package br.com.projetoferias.controller;

import br.com.projetoferias.assistant.CopaAssistantService;
import br.com.projetoferias.controller.dto.CopaAssistantRequest;
import br.com.projetoferias.controller.dto.CopaAssistantResponse;
import br.com.projetoferias.gemini.GeminiConfigurationException;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
public class CopaAssistantController {

    private static final Logger LOGGER = LoggerFactory.getLogger(CopaAssistantController.class);

    private final CopaAssistantService copaAssistantService;

    public CopaAssistantController(CopaAssistantService copaAssistantService) {
        this.copaAssistantService = copaAssistantService;
    }

    @PostMapping({"/api/copa-assistant/chat", "/api/chat"})
    public ResponseEntity<CopaAssistantResponse> chat(@Valid @RequestBody CopaAssistantRequest request) {
        try {
            return ResponseEntity.ok(new CopaAssistantResponse(copaAssistantService.ask(request.message())));
        } catch (GeminiConfigurationException exception) {
            LOGGER.error("Configuração do Gemini ausente ou inválida", exception);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new CopaAssistantResponse(exception.getMessage()));
        } catch (RuntimeException exception) {
            LOGGER.error("Erro inesperado ao processar mensagem do Copa Assistant", exception);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new CopaAssistantResponse("No momento não consegui consultar a IA. Tente novamente em alguns instantes."));
        }
    }
}
