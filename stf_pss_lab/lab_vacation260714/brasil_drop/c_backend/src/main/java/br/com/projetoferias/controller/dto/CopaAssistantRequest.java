package br.com.projetoferias.controller.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CopaAssistantRequest(
        @NotBlank(message = "A mensagem e obrigatoria.")
        @Size(max = 500, message = "A mensagem deve ter no maximo 500 caracteres.")
        String message
) {
}
