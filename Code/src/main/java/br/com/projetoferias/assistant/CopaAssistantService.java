package br.com.projetoferias.assistant;

import br.com.projetoferias.ai.AIProvider;
import br.com.projetoferias.mcp.Copa2026MCP;
import org.springframework.stereotype.Service;

@Service
public class CopaAssistantService {

    private final Copa2026MCP copa2026MCP;
    private final AIProvider aiProvider;

    public CopaAssistantService(Copa2026MCP copa2026MCP, AIProvider aiProvider) {
        this.copa2026MCP = copa2026MCP;
        this.aiProvider = aiProvider;
    }

    public String ask(String question) {
        String prompt = buildPrompt(question);
        try {
            return aiProvider.generate(prompt);
        } catch (RuntimeException exception) {
            return "O Copa Assistant esta indisponivel no momento, mas o marketplace continua funcionando normalmente.";
        }
    }

    private String buildPrompt(String question) {
        return """
                Voce e o Copa Assistant do marketplace Brasildrop.

                Contexto Copa:
                %s

                Contexto Brasil:
                %s

                Contexto Marketplace:
                %s

                Pergunta:
                %s

                Responda de forma amigavel, curta e objetiva.
                """.formatted(
                copa2026MCP.getWorldCupContext(),
                copa2026MCP.getBrazilTeamContext(),
                copa2026MCP.getMarketplaceContext(),
                question
        );
    }
}
