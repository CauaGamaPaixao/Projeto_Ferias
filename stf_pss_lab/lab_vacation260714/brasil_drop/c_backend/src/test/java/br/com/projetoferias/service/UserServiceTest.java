package br.com.projetoferias.service;

import br.com.projetoferias.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.core.JdbcTemplate;
import static org.assertj.core.api.Assertions.assertThat;

@JdbcTest
@Import({UserService.class, UserRepository.class})
class UserServiceTest {
    @Autowired UserService users;
    @Autowired UserRepository repository;
    @Autowired JdbcTemplate jdbc;

    @Test
    void registersAndAuthenticatesUser() {
        assertThat(users.register("Ana", " Ana@email.com ", "123456")).isTrue();
        assertThat(users.authenticate("ANA@email.com", "123456")).isPresent();
        assertThat(users.authenticate("ana@email.com", "incorrect")).isEmpty();
        assertThat(jdbc.queryForObject("SELECT password_hash FROM user_accounts WHERE email = ?",
                String.class, "ana@email.com")).doesNotContain("123456");
    }

    @Test
    void rejectsDuplicateEmail() {
        users.register("Ana", "ana@email.com", "123456");
        assertThat(users.register("Outra Ana", "ANA@email.com", "abcdef")).isFalse();
    }

    @Test
    void newServiceInstanceKeepsAccountAndCannotReclaimItsEmail() {
        users.register("Ana", "ana@email.com", "123456");
        UserService reopened = new UserService(repository);
        assertThat(reopened.authenticate("ana@email.com", "123456")).isPresent();
        assertThat(reopened.register("Impostor", "ana@email.com", "abcdef")).isFalse();
    }
}
