package br.com.projetoferias.repository;

import br.com.projetoferias.model.*;
import br.com.projetoferias.service.OrderService;
import br.com.projetoferias.service.UserService;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.flywaydb.core.Flyway;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.springframework.jdbc.core.JdbcTemplate;
import java.math.BigDecimal;
import java.nio.file.Path;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;

class FilePersistenceTest {
    @TempDir Path directory;

    @Test
    void accountsAndReceiptSurviveClosingAndReopeningFileDatabase() {
        String url = "jdbc:h2:file:" + directory.resolve("orders").toAbsolutePath().toString().replace('\\', '/');
        String code;
        OrderSummary original;
        try (HikariDataSource database = open(url)) {
            Flyway.configure().dataSource(database).load().migrate();
            JdbcTemplate jdbc = new JdbcTemplate(database);
            UserService users = new UserService(new UserRepository(jdbc));
            users.register("Ana", "ana@example.com", "test-password");
            UserAccount owner = users.authenticate("ana@example.com", "test-password").orElseThrow();
            original = new OrderService(new OrderRepository(jdbc)).createOrder(owner,
                    List.of(new CartItem(new Product(1L, "Camisa histórica", "Nike", ProductCategory.CAMISAS,
                            "", new BigDecimal("349.90"), ""), 2)),
                    new CheckoutAddress("01001-000", "Praça da Sé", "Sé", "São Paulo", "SP", "123", ""),
                    "PIX", 1);
            code = original.code();
        }
        try (HikariDataSource database = open(url)) {
            Flyway.configure().dataSource(database).load().migrate();
            JdbcTemplate jdbc = new JdbcTemplate(database);
            UserService users = new UserService(new UserRepository(jdbc));
            UserAccount owner = users.authenticate("ana@example.com", "test-password").orElseThrow();
            assertThat(users.register("Impostor", "ANA@example.com", "different")).isFalse();
            OrderSummary recovered = new OrderService(new OrderRepository(jdbc)).findForUser(code, owner);
            assertThat(recovered).isEqualTo(original);
        }
    }

    private HikariDataSource open(String url) {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(url);
        config.setUsername("sa");
        config.setPassword("");
        return new HikariDataSource(config);
    }
}
