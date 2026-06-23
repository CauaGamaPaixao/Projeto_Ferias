package br.com.projetoferias.ai;

import org.springframework.stereotype.Service;

import java.util.Locale;

@Service
public class MockAIProvider implements AIProvider {

    @Override
    public String generate(String prompt) {
        String question = extractQuestion(prompt).toLowerCase(Locale.ROOT);

        if (question.contains("proximo jogo") || question.contains("próximo jogo")) {
            return "O proximo jogo do Brasil na Copa e: " + extractContextValue(prompt, "Proximo jogo do Brasil:", ". Tabela do grupo do Brasil:");
        }
        if (question.contains("tabela") || question.contains("grupo") || question.contains("posição")
                || question.contains("posicao") || question.contains("classificação") || question.contains("classificacao")) {
            return "Na tabela atual, " + extractContextValue(prompt, "Tabela do grupo do Brasil:", "\n\nContexto Brasil:");
        }
        if (question.contains("visitante") || question.contains("fora")) {
            return "A selecao usa camisa azul como uniforme visitante. No catalogo temos a Camisa Brasil Fora Azul.";
        }
        if (question.contains("tecnico") || question.contains("técnico")) {
            return "Nos dados simulados, o tecnico atual da selecao e Carlo Ancelotti.";
        }
        if (question.contains("torcedor") || question.contains("jogador")) {
            return "A camisa de jogador costuma ser mais ajustada e tecnologica; a de torcedor e mais confortavel para uso casual.";
        }
        if (question.contains("society") || question.contains("chuteira")) {
            return "Para jogar society, olhe primeiro as chuteiras do catalogo: Chuteira Puma Future Coral e Chuteira Nike Mercurial Rosa.";
        }
        if (question.contains("promocao") || question.contains("promoção")) {
            return "Produtos em promocao no mock: Camisa Brasil Goleiro Verde, Bone Brasil Azul CBF e Blusa Treino Brasil Manga Longa.";
        }
        if (question.contains("camisa") || question.contains("produto")) {
            return "Temos Camisa Brasil Casa 2026, Camisa Brasil Goleiro Verde, Camisa Brasil Fora Azul e Blusa Treino Brasil Manga Longa.";
        }
        if (question.contains("noticia") || question.contains("notícias")) {
            return "Noticia simulada: a Copa 2026 sera sediada por Estados Unidos, Canada e Mexico.";
        }

        return "Posso ajudar com jogos do Brasil, uniformes, noticias da Copa e produtos do catalogo Brasildrop.";
    }

    private String extractQuestion(String prompt) {
        int index = prompt.lastIndexOf("Pergunta:");
        if (index < 0) {
            return prompt;
        }
        return prompt.substring(index + "Pergunta:".length()).trim();
    }

    private String extractContextValue(String prompt, String startMarker, String endMarker) {
        int start = prompt.indexOf(startMarker);
        if (start < 0) {
            return "nao encontrei essa informacao no contexto disponivel.";
        }

        int contentStart = start + startMarker.length();
        int end = prompt.indexOf(endMarker, contentStart);
        if (end < 0) {
            end = prompt.length();
        }

        String value = prompt.substring(contentStart, end).trim();
        if (value.isBlank()) {
            return "nao encontrei essa informacao no contexto disponivel.";
        }
        return value;
    }
}
