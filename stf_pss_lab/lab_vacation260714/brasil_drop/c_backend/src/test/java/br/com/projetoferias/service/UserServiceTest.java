package br.com.projetoferias.service;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class UserServiceTest {

    @Test
    void registersAndAuthenticatesUser() {
        UserService userService = new UserService();

        boolean created = userService.register("Ana", "ana@email.com", "123456");

        assertThat(created).isTrue();
        assertThat(userService.authenticate("ana@email.com", "123456")).isPresent();
    }

    @Test
    void rejectsDuplicateEmail() {
        UserService userService = new UserService();

        userService.register("Ana", "ana@email.com", "123456");

        assertThat(userService.register("Outra Ana", "ANA@email.com", "abcdef")).isFalse();
    }
}
