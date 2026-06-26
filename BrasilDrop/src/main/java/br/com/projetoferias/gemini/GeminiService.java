package br.com.projetoferias.gemini;

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
    private final String apiKey;
    private final String model;
    private final RestOperations restOperations;

    @Autowired
    public GeminiService(ProductService productService,
                         @Value("${gemini.api.key:}") String apiKey,
                         @Value("${gemini.model:gemini-2.5-flash}") String model) {
        this(productService, apiKey, model, new RestTemplate());
    }

    GeminiService(ProductService productService,
                  String apiKey,
                  String model,
                  RestOperations restOperations) {
        this.productService = productService;
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

                Utilize a ferramenta de busca do Google para obter informações atualizadas sobre:
                - Fase atual da Copa do Mundo 2026 (mata-mata, resultados recentes, próximos jogos do Brasil)
                - Escalação, técnico e situação atual da Seleção Brasileira
                - Qualquer notícia recente relevante sobre a Copa 2026

                Catálogo disponível no BrasilDrop:
                %s

                Regras:
                - Nunca invente produtos.
                - Nunca recomende produtos inexistentes.
                - Sempre utilize apenas o catálogo informado acima para recomendações de produtos.
                - APENAS mencione ou recomende produtos se o usuário perguntar explicitamente sobre produtos, compras ou recomendações. Nunca inclua produtos espontaneamente em respostas sobre Copa ou futebol.
                - Use a busca do Google para responder perguntas sobre jogos, resultados e notícias da Copa.
                - Seja objetivo.
                - Responda em português brasileiro.
                - Utilize tom amigável.

                Pergunta do usuário:
                %s
                """.formatted(
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
        return new GeminiRequest(
                List.of(new GeminiContent(List.of(new GeminiPart(prompt)))),
                new GeminiGenerationConfig(0.4),
                List.of(new GeminiTool(new GeminiGoogleSearch()))
        );
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

    public record GeminiRequest(List<GeminiContent> contents, GeminiGenerationConfig generationConfig, List<GeminiTool> tools) {
    }

    public record GeminiContent(List<GeminiPart> parts) {
    }

    public record GeminiPart(String text) {
    }

    public record GeminiGenerationConfig(double temperature) {
    }

    public record GeminiTool(GeminiGoogleSearch google_search) {
    }

    public record GeminiGoogleSearch() {
    }

    public record GeminiResponse(List<GeminiCandidate> candidates) {
    }

    public record GeminiCandidate(GeminiContent content) {
    }
}
