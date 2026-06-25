package br.com.projetoferias.gemini;

import br.com.projetoferias.mcp.Copa2026MCP;
import br.com.projetoferias.model.Product;
import br.com.projetoferias.service.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestOperations;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class GeminiService {

    private static final Logger LOGGER = LoggerFactory.getLogger(GeminiService.class);
    private static final String GEMINI_ENDPOINT = "https://generativelanguage.googleapis.com/v1beta/models/%s:generateContent";
    private static final String UNAVAILABLE_MESSAGE = "No momento não consegui consultar a IA. Tente novamente em alguns instantes.";

    private final ProductService productService;
    private final Copa2026MCP copa2026MCP;
    private final String apiKey;
    private final String model;
    private final RestOperations restOperations;

    @Autowired
    public GeminiService(ProductService productService,
                         Copa2026MCP copa2026MCP,
                         @Value("${gemini.api.key:}") String apiKey,
                         @Value("${gemini.model:gemini-2.5-flash}") String model) {
        this(productService, copa2026MCP, apiKey, model, new RestTemplate());
    }

    GeminiService(ProductService productService,
                  Copa2026MCP copa2026MCP,
                  String apiKey,
                  String model,
                  RestOperations restOperations) {
        this.productService = productService;
        this.copa2026MCP = copa2026MCP;
        this.apiKey = apiKey;
        this.model = model;
        this.restOperations = restOperations;
    }

    public String ask(String question) {
        validateApiKey();

        try {
            GeminiResponse response = restOperations.postForObject(
                    GEMINI_ENDPOINT.formatted(model),
                    new HttpEntity<>(buildRequest(buildPrompt(question)), buildHeaders()),
                    GeminiResponse.class
            );
            return extractText(response);
        } catch (RestClientException exception) {
            LOGGER.error("Erro ao consultar a API do Google Gemini", exception);
            return UNAVAILABLE_MESSAGE;
        }
    }

    String buildPrompt(String question) {
        return """
                Você é o Copa Assistant do marketplace BrasilDrop.

                O BrasilDrop vende apenas artigos esportivos.

                Contexto da Copa do Mundo:
                %s

                Contexto da seleção brasileira:
                %s

                Catálogo disponível:
                %s

                Regras:
                - Nunca invente produtos.
                - Nunca recomende produtos inexistentes.
                - Sempre utilize apenas o catálogo informado.
                - Seja objetivo.
                - Responda em português brasileiro.
                - Utilize tom amigável.
                - Quando perguntarem sobre recomendações, utilize os produtos disponíveis.

                Pergunta do usuário:
                %s
                """.formatted(
                copa2026MCP.getWorldCupContext(),
                copa2026MCP.getBrazilTeamContext(),
                buildMarketplaceContext(),
                question
        );
    }

    String buildMarketplaceContext() {
        List<Product> products = productService.findAll(null, null);
        if (products.isEmpty()) {
            return "Nenhum produto disponível no momento.";
        }

        return products.stream()
                .map(product -> "- %s | Marca: %s | Categoria: %s | Descrição: %s | Preço: R$ %s".formatted(
                        product.name(),
                        product.brand(),
                        product.category().getLabel(),
                        product.description(),
                        product.price()
                ))
                .reduce((current, next) -> current + "\n" + next)
                .orElse("Nenhum produto disponível no momento.");
    }

    private void validateApiKey() {
        if (apiKey == null || apiKey.isBlank()) {
            LOGGER.error("GEMINI_API_KEY não configurada. Defina a variável de ambiente antes de iniciar a aplicação.");
            throw new GeminiConfigurationException("A chave da API Gemini não foi configurada. Defina GEMINI_API_KEY e tente novamente.");
        }
    }

    private HttpHeaders buildHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("x-goog-api-key", apiKey);
        return headers;
    }

    private GeminiRequest buildRequest(String prompt) {
        return new GeminiRequest(List.of(new GeminiContent(List.of(new GeminiPart(prompt)))), new GeminiGenerationConfig(0.4));
    }

    private String extractText(GeminiResponse response) {
        if (response == null || response.candidates() == null || response.candidates().isEmpty()) {
            LOGGER.error("Resposta vazia recebida da API do Google Gemini");
            return UNAVAILABLE_MESSAGE;
        }

        GeminiCandidate candidate = response.candidates().get(0);
        if (candidate.content() == null || candidate.content().parts() == null || candidate.content().parts().isEmpty()) {
            LOGGER.error("Resposta sem conteúdo textual recebida da API do Google Gemini");
            return UNAVAILABLE_MESSAGE;
        }

        String text = candidate.content().parts().stream()
                .map(GeminiPart::text)
                .filter(part -> part != null && !part.isBlank())
                .reduce("", (current, next) -> current.isBlank() ? next : current + "\n" + next)
                .trim();

        if (text.isBlank()) {
            LOGGER.error("Resposta textual em branco recebida da API do Google Gemini");
            return UNAVAILABLE_MESSAGE;
        }
        return text;
    }

    public record GeminiRequest(List<GeminiContent> contents, GeminiGenerationConfig generationConfig) {
    }

    public record GeminiContent(List<GeminiPart> parts) {
    }

    public record GeminiPart(String text) {
    }

    public record GeminiGenerationConfig(double temperature) {
    }

    public record GeminiResponse(List<GeminiCandidate> candidates) {
    }

    public record GeminiCandidate(GeminiContent content) {
    }
}
