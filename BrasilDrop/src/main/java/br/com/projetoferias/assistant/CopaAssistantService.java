package br.com.projetoferias.assistant;

import br.com.projetoferias.gemini.GeminiService;
import org.springframework.stereotype.Service;

@Service
public class CopaAssistantService {

    private final GeminiService geminiService;

    public CopaAssistantService(GeminiService geminiService) {
        this.geminiService = geminiService;
    }

    public String ask(String question) {
        return geminiService.ask(question);
    }
}
