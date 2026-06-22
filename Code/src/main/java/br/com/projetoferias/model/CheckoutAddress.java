package br.com.projetoferias.model;

public record CheckoutAddress(
        String cep,
        String logradouro,
        String bairro,
        String localidade,
        String uf,
        String complemento
) {
}
