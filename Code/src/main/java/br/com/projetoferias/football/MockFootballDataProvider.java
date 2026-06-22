package br.com.projetoferias.football;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MockFootballDataProvider implements FootballDataProvider {

    @Override
    public List<String> getNextBrazilMatches() {
        return List.of(
                "Brasil x Argentina em 2026-07-10",
                "Brasil x Alemanha em 2026-07-15"
        );
    }

    @Override
    public String getBrazilTeamInfo() {
        return "Tecnico atual: Carlo Ancelotti. Uniforme visitante: camisa azul. Uniforme principal: camisa amarela.";
    }

    @Override
    public List<String> getWorldCupNews() {
        return List.of(
                "A Copa 2026 sera sediada por Estados Unidos, Canada e Mexico.",
                "O Brasil segue como uma das selecoes mais procuradas em produtos oficiais e de treino."
        );
    }
}
