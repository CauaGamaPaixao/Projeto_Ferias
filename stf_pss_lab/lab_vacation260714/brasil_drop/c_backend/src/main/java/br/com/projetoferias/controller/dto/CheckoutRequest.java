package br.com.projetoferias.controller.dto;

import jakarta.validation.constraints.*;

public record CheckoutRequest(
        @NotBlank @Pattern(regexp = "\\d{5}-?\\d{3}") String cep,
        @NotBlank @Size(max = 250) String rua,
        @NotBlank @Size(max = 30) String numero,
        @Size(max = 250) String complemento,
        @NotBlank String paymentMethod,
        @Min(1) @Max(10) int installments) {
}
