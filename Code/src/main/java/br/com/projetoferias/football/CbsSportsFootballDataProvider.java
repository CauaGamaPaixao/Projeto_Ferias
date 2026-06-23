package br.com.projetoferias.football;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientException;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Primary
@Service
public class CbsSportsFootballDataProvider implements FootballDataProvider {

    private static final String SOURCE_URL = "https://www.cbssports.com/soccer/news/world-cup-group-standings-table-results/";
    private static final Pattern GROUP_C_PATTERN = Pattern.compile("(?is)Group C(.*?)(Group D|Add CBS Sports|$)");
    private static final Pattern BRAZIL_NEXT_MATCH_PATTERN = Pattern.compile(
            "(Monday|Tuesday|Wednesday|Thursday|Friday|Saturday|Sunday),\\s+([A-Za-z]+\\s+\\d{1,2}).{0,120}?((?:Brazil\\s+vs\\.\\s+[^,]+)|(?:[^,]+\\s+vs\\.\\s+Brazil)),\\s+(\\d{1,2}(?::\\d{2})?\\s+p\\.m\\.)",
            Pattern.CASE_INSENSITIVE
    );

    private final RestClient restClient;
    private final FootballDataProvider fallbackProvider;

    public CbsSportsFootballDataProvider() {
        this(RestClient.create(), new MockFootballDataProvider());
    }

    CbsSportsFootballDataProvider(RestClient restClient, FootballDataProvider fallbackProvider) {
        this.restClient = restClient;
        this.fallbackProvider = fallbackProvider;
    }

    @Override
    public List<String> getNextBrazilMatches() {
        return fetchPage()
                .map(this::extractNextBrazilMatch)
                .filter(match -> !match.isBlank())
                .map(List::of)
                .orElseGet(fallbackProvider::getNextBrazilMatches);
    }

    @Override
    public String getBrazilTeamInfo() {
        return fallbackProvider.getBrazilTeamInfo();
    }

    @Override
    public String getBrazilGroupStandings() {
        return fetchPage()
                .map(this::extractBrazilGroupStandings)
                .filter(standings -> !standings.isBlank())
                .orElseGet(fallbackProvider::getBrazilGroupStandings);
    }

    @Override
    public List<String> getWorldCupNews() {
        return List.of(
                "Fonte de jogos e tabela: CBS Sports World Cup group standings.",
                "A Copa 2026 esta em fase de grupos com Brasil no Grupo C."
        );
    }

    private java.util.Optional<String> fetchPage() {
        try {
            return java.util.Optional.ofNullable(restClient.get()
                    .uri(SOURCE_URL)
                    .retrieve()
                    .body(String.class));
        } catch (RestClientException exception) {
            return java.util.Optional.empty();
        }
    }

    private String extractNextBrazilMatch(String html) {
        String groupC = extractGroupCPlainText(html);
        Matcher matcher = BRAZIL_NEXT_MATCH_PATTERN.matcher(groupC);
        if (!matcher.find()) {
            return "";
        }
        return "%s, %s: %s, %s ET".formatted(
                matcher.group(1),
                matcher.group(2),
                normalizeSpaces(matcher.group(3)),
                matcher.group(4)
        );
    }

    private String extractBrazilGroupStandings(String html) {
        String groupC = extractGroupCPlainText(html);
        if (groupC.isBlank()) {
            return "";
        }

        String[] teams = {"Brazil", "Morocco", "Scotland", "Haiti"};
        StringBuilder standings = new StringBuilder("Grupo C: ");
        for (String team : teams) {
            Pattern teamPattern = Pattern.compile("(\\d)\\s+" + team + "\\s+(\\d)\\s+(\\d)\\s+(\\d)\\s+(\\d)\\s+(\\d)\\s+(\\d)([+-]?\\d)\\s+(\\d)", Pattern.CASE_INSENSITIVE);
            Matcher matcher = teamPattern.matcher(groupC);
            if (matcher.find()) {
                standings.append("%s %s - %sJ, %sV, %sE, %sD, GP %s, GC %s, SG %s, %s pts; ".formatted(
                        matcher.group(1),
                        team,
                        matcher.group(2),
                        matcher.group(3),
                        matcher.group(4),
                        matcher.group(5),
                        matcher.group(6),
                        matcher.group(7),
                        matcher.group(8),
                        matcher.group(9)
                ));
            }
        }

        return standings.toString().replaceAll("; $", ".");
    }

    private String extractGroupCPlainText(String html) {
        String markdownLike = html
                .replaceAll("(?i)<br\\s*/?>", "\n")
                .replaceAll("(?i)</p>|</li>|</tr>|</h[1-6]>", "\n");
        String plainText = normalizeSpaces(markdownLike.replaceAll("<[^>]+>", " "));
        Matcher matcher = GROUP_C_PATTERN.matcher(plainText);
        if (!matcher.find()) {
            return "";
        }
        return normalizeSpaces(matcher.group(1));
    }

    private String normalizeSpaces(String value) {
        return value.replace("&nbsp;", " ")
                .replaceAll("\\s+", " ")
                .trim();
    }
}
