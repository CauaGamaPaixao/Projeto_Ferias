package br.com.projetoferias.gemini;

import br.com.projetoferias.mcp.Copa2026MCP;
import br.com.projetoferias.model.Product;
import br.com.projetoferias.model.ProductCategory;
import br.com.projetoferias.repository.ProductRepository;
import br.com.projetoferias.service.ProductService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpEntity;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestOperations;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GeminiServiceTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private Copa2026MCP copa2026MCP;

    @Mock
    private RestOperations restOperations;

    @Test
    void buildsPromptWithMarketplaceContextAndReturnsGeminiText() {
        ProductService productService = new ProductService(productRepository);
        GeminiService geminiService = new GeminiService(productService, copa2026MCP, "api-key", "gemini-test", restOperations);
        when(productRepository.findAll()).thenReturn(products());
        when(copa2026MCP.getWorldCupContext()).thenReturn("Brasil lidera o Grupo C.");
        when(copa2026MCP.getBrazilTeamContext()).thenReturn("Uniforme visitante: camisa azul.");
        when(restOperations.postForObject(
                eq("https://generativelanguage.googleapis.com/v1beta/models/gemini-test:generateContent"),
                org.mockito.ArgumentMatchers.any(HttpEntity.class),
                eq(GeminiService.GeminiResponse.class)
        )).thenReturn(new GeminiService.GeminiResponse(List.of(
                new GeminiService.GeminiCandidate(new GeminiService.GeminiContent(List.of(
                        new GeminiService.GeminiPart("Recomendo a Camisa Brasil Casa 2026 para torcer.")
                )))
        )));

        String response = geminiService.ask("Qual camisa vocês recomendam?");

        assertThat(response).isEqualTo("Recomendo a Camisa Brasil Casa 2026 para torcer.");
        ArgumentCaptor<HttpEntity<GeminiService.GeminiRequest>> requestCaptor = ArgumentCaptor.forClass(HttpEntity.class);
        org.mockito.Mockito.verify(restOperations).postForObject(
                eq("https://generativelanguage.googleapis.com/v1beta/models/gemini-test:generateContent"),
                requestCaptor.capture(),
                eq(GeminiService.GeminiResponse.class)
        );
        assertThat(requestCaptor.getValue().getHeaders().getFirst("x-goog-api-key")).isEqualTo("api-key");
        assertThat(requestCaptor.getValue().getBody().contents().get(0).parts().get(0).text())
                .contains("Você é o Copa Assistant do marketplace BrasilDrop")
                .contains("Camisa Brasil Casa 2026")
                .contains("Nunca invente produtos")
                .contains("Qual camisa vocês recomendam?");
    }

    @Test
    void throwsFriendlyConfigurationErrorWhenApiKeyIsMissing() {
        ProductService productService = new ProductService(productRepository);
        GeminiService geminiService = new GeminiService(productService, copa2026MCP, " ", "gemini-test", restOperations);

        assertThatThrownBy(() -> geminiService.ask("Oi"))
                .isInstanceOf(GeminiConfigurationException.class)
                .hasMessage("A chave da API Gemini não foi configurada. Defina GEMINI_API_KEY e tente novamente.");
    }

    @Test
    void returnsFriendlyMessageWhenGeminiApiIsUnavailable() {
        ProductService productService = new ProductService(productRepository);
        GeminiService geminiService = new GeminiService(productService, copa2026MCP, "api-key", "gemini-test", restOperations);
        when(productRepository.findAll()).thenReturn(products());
        when(copa2026MCP.getWorldCupContext()).thenReturn("Contexto Copa");
        when(copa2026MCP.getBrazilTeamContext()).thenReturn("Contexto Brasil");
        when(restOperations.postForObject(
                eq("https://generativelanguage.googleapis.com/v1beta/models/gemini-test:generateContent"),
                org.mockito.ArgumentMatchers.any(HttpEntity.class),
                eq(GeminiService.GeminiResponse.class)
        )).thenThrow(new RestClientException("offline"));

        String response = geminiService.ask("Oi");

        assertThat(response).isEqualTo("No momento não consegui consultar a IA. Tente novamente em alguns instantes.");
    }

    private List<Product> products() {
        return List.of(new Product(
                1L,
                "Camisa Brasil Casa 2026",
                "Nike",
                ProductCategory.CAMISAS,
                "Camisa amarela da selecao brasileira para torcer em clima de copa.",
                new BigDecimal("349.90"),
                "/images/brasilcasa.jpg"
        ));
    }
}
