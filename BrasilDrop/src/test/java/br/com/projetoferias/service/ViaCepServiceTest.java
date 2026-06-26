package br.com.projetoferias.service;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ViaCepServiceTest {

    @Test
    void rejectsCepWithInvalidLengthWithoutCallingApi() {
        ViaCepService viaCepService = new ViaCepService();

        assertThat(viaCepService.findAddress("123")).isEmpty();
    }
}
