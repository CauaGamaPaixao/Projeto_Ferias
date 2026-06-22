package br.com.projetoferias.controller;

import br.com.projetoferias.assistant.CopaAssistantService;
import br.com.projetoferias.controller.dto.CopaAssistantRequest;
import br.com.projetoferias.controller.dto.CopaAssistantResponse;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/copa-assistant")
public class CopaAssistantController {

    private final CopaAssistantService copaAssistantService;

    public CopaAssistantController(CopaAssistantService copaAssistantService) {
        this.copaAssistantService = copaAssistantService;
    }

    @PostMapping("/chat")
    public ResponseEntity<CopaAssistantResponse> chat(@Valid @RequestBody CopaAssistantRequest request) {
        return ResponseEntity.ok(new CopaAssistantResponse(copaAssistantService.ask(request.message())));
    }
}
