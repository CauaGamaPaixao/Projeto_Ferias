package br.com.projetoferias.controller;

import br.com.projetoferias.model.*;
import br.com.projetoferias.repository.OrderRepository;
import br.com.projetoferias.service.ProductService;
import br.com.projetoferias.service.ViaCepService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;
import java.math.BigDecimal;
import java.util.Optional;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(properties = "spring.datasource.url=jdbc:h2:mem:order-flow;DB_CLOSE_DELAY=-1")
@AutoConfigureMockMvc
class OrderFlowTest {
    @Autowired MockMvc mvc;
    @Autowired ObjectMapper json;
    @Autowired JdbcTemplate jdbc;
    @MockBean ViaCepService viaCep;
    @MockBean ProductService products;

    @BeforeEach
    void setup() {
        jdbc.update("DELETE FROM purchase_order_items");
        jdbc.update("DELETE FROM purchase_orders");
        when(viaCep.findAddress("01001-000")).thenReturn(Optional.of(
                new CheckoutAddress("01001-000", "Praça da Sé", "Sé", "São Paulo", "SP", "", "")));
        when(products.findById(1L)).thenReturn(Optional.of(product("Camisa Brasil", "349.90")));
    }

    @Test
    void checkoutCanBeReadInNewSessionWithCompletePrivateContract() throws Exception {
        MockHttpSession session = login();
        add(session);
        add(session);
        JsonNode order = checkout(session);
        assertThat(order.get("total").decimalValue()).isEqualByComparingTo("699.80");
        assertThat(order.get("items").get(0).get("subtotal").decimalValue()).isEqualByComparingTo("699.80");
        assertThat(order.get("items").get(0).get("unitPrice").decimalValue()).isEqualByComparingTo("349.90");
        assertThat(order.get("items").get(0).get("quantity").intValue()).isEqualTo(2);
        assertThat(order.get("installments").intValue()).isEqualTo(3);
        assertThat(order.get("address").get("complemento").asText()).isEqualTo("Apto 42");
        assertThat(order.get("createdAt").asText()).endsWith("Z");
        assertThat(order.has("ownerEmail")).isFalse();
        assertThat(order.has("password")).isFalse();
        mvc.perform(get("/api/cart").session(session)).andExpect(jsonPath("$.items").isEmpty());
        String code = order.get("code").asText();
        mvc.perform(get("/api/orders/" + code).session(login()))
                .andExpect(status().isOk()).andExpect(header().string("Cache-Control", "no-store"))
                .andExpect(jsonPath("$.code").value(code)).andExpect(jsonPath("$.buyerName").value("Usuario Demo"));
        mvc.perform(get("/api/orders").session(login())).andExpect(jsonPath("$[0].code").value(code));
        mvc.perform(post("/api/checkout").session(session).contentType("application/json").content(request("PIX", 1)))
                .andExpect(status().isBadRequest());
        assertThat(jdbc.queryForObject("SELECT COUNT(*) FROM purchase_orders", Integer.class)).isEqualTo(1);
    }

    @Test
    void rejectsAnonymousForeignAndMissingOrders() throws Exception {
        MockHttpSession owner = login();
        add(owner);
        String code = checkout(owner).get("code").asText();
        mvc.perform(get("/api/orders/" + code)).andExpect(status().isUnauthorized());
        mvc.perform(get("/api/orders")).andExpect(status().isUnauthorized());
        MockHttpSession foreign = new MockHttpSession();
        foreign.setAttribute("user", new UserAccount("Outra pessoa", "other@example.com", ""));
        mvc.perform(get("/api/orders/" + code).session(foreign)).andExpect(status().isForbidden());
        mvc.perform(get("/api/orders").session(foreign)).andExpect(jsonPath("$").isEmpty());
        mvc.perform(get("/api/orders/BD-MISSING").session(owner)).andExpect(status().isNotFound());
    }

    @Test
    void receiptKeepsSnapshotAfterCatalogChanges() throws Exception {
        MockHttpSession owner = login();
        add(owner);
        String code = checkout(owner).get("code").asText();
        when(products.findById(1L)).thenReturn(Optional.of(product("Outro nome", "999.99")));
        mvc.perform(get("/api/orders/" + code).session(owner))
                .andExpect(jsonPath("$.items[0].name").value("Camisa Brasil"))
                .andExpect(jsonPath("$.items[0].unitPrice").value(349.90));
        when(products.findById(1L)).thenReturn(Optional.empty());
        assertThat(new OrderRepository(jdbc).findByCode(code).orElseThrow().items().get(0).name())
                .isEqualTo("Camisa Brasil");
    }

    @ParameterizedTest
    @CsvSource({"PIX,2", "BOLETO,7", "CARTAO_CREDITO,11", "INVALID,1", "PIX,0"})
    void invalidPaymentKeepsCartAndDoesNotCreateOrder(String method, int count) throws Exception {
        MockHttpSession session = login();
        add(session);
        mvc.perform(post("/api/checkout").session(session).contentType("application/json").content(request(method, count)))
                .andExpect(status().isBadRequest());
        mvc.perform(get("/api/cart").session(session)).andExpect(jsonPath("$.items[0].quantity").value(1));
        assertThat(jdbc.queryForObject("SELECT COUNT(*) FROM purchase_orders", Integer.class)).isZero();
    }

    @Test
    void badAddressAndUnauthenticatedCheckoutAreRejected() throws Exception {
        mvc.perform(post("/api/checkout").contentType("application/json").content(request("PIX", 1)))
                .andExpect(status().isUnauthorized());
        MockHttpSession session = login();
        add(session);
        mvc.perform(post("/api/checkout").session(session).contentType("application/json")
                .content(request("PIX", 1).replace("01001-000", "invalid")))
                .andExpect(status().isBadRequest());
        when(viaCep.findAddress("01001-000")).thenReturn(Optional.empty());
        mvc.perform(post("/api/checkout").session(session).contentType("application/json").content(request("PIX", 1)))
                .andExpect(status().isBadRequest());
        mvc.perform(get("/api/cart").session(session)).andExpect(jsonPath("$.items[0].quantity").value(1));
    }

    @Test
    void failedItemInsertRollsBackHeaderAndKeepsCart() throws Exception {
        MockHttpSession session = login();
        add(session);
        // Force a database failure after the header and first item were inserted.
        when(products.findById(2L)).thenReturn(Optional.of(new Product(2L, "x".repeat(251), "Nike",
                ProductCategory.CAMISAS, "", new BigDecimal("10.00"), "")));
        mvc.perform(post("/api/cart/add").session(session).contentType("application/json").content("{\"productId\":2}"))
                .andExpect(status().isOk());
        assertThatThrownBy(() -> mvc.perform(post("/api/checkout").session(session)
                .contentType("application/json").content(request("PIX", 1))))
                .hasRootCauseInstanceOf(java.sql.SQLException.class);
        assertThat(jdbc.queryForObject("SELECT COUNT(*) FROM purchase_orders", Integer.class)).isZero();
        assertThat(jdbc.queryForObject("SELECT COUNT(*) FROM purchase_order_items", Integer.class)).isZero();
        mvc.perform(get("/api/cart").session(session)).andExpect(jsonPath("$.items[0].quantity").value(1));
    }

    @Test
    void concurrentCheckoutCreatesOnlyOneOrder() throws Exception {
        MockHttpSession session = login();
        add(session);
        java.util.concurrent.ExecutorService executor = java.util.concurrent.Executors.newFixedThreadPool(2);
        try {
            java.util.concurrent.Callable<Integer> pay = () -> mvc.perform(post("/api/checkout").session(session)
                    .contentType("application/json").content(request("PIX", 1)))
                    .andReturn().getResponse().getStatus();
            var results = executor.invokeAll(java.util.List.of(pay, pay));
            assertThat(java.util.List.of(results.get(0).get(), results.get(1).get()))
                    .containsExactlyInAnyOrder(200, 400);
            assertThat(jdbc.queryForObject("SELECT COUNT(*) FROM purchase_orders", Integer.class)).isEqualTo(1);
        } finally {
            executor.shutdownNow();
        }
    }

    private MockHttpSession login() throws Exception {
        return (MockHttpSession) mvc.perform(post("/api/auth/login").contentType("application/json")
                .content("{\"email\":\"demo@brasilmarket.com\",\"password\":\"123456\"}"))
                .andExpect(status().isOk()).andReturn().getRequest().getSession();
    }

    private void add(MockHttpSession session) throws Exception {
        mvc.perform(post("/api/cart/add").session(session).contentType("application/json").content("{\"productId\":1}"))
                .andExpect(status().isOk());
    }

    private JsonNode checkout(MockHttpSession session) throws Exception {
        return json.readTree(mvc.perform(post("/api/checkout").session(session).contentType("application/json")
                .content(request("CARTAO_CREDITO", 3))).andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString());
    }

    private String request(String method, int installments) {
        return """
                {"cep":"01001-000","rua":"Praça da Sé","numero":"123","complemento":"Apto 42",
                 "paymentMethod":"%s","installments":%d}
                """.formatted(method, installments);
    }

    private Product product(String name, String price) {
        return new Product(1L, name, "Nike", ProductCategory.CAMISAS, "Camisa", new BigDecimal(price), "/image.jpg");
    }
}
