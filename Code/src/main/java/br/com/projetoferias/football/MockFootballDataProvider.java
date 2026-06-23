package br.com.projetoferias.football;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MockFootballDataProvider implements FootballDataProvider {

    @Override
    public List<String> getNextBrazilMatches() {
        return List.of(
                "Scotland x Brasil em 2026-06-24 as 18:00 ET"
        );
    }

    @Override
    public String getBrazilTeamInfo() {
        return "Tecnico atual: Carlo Ancelotti. Uniforme visitante: camisa azul. Uniforme principal: camisa amarela.";
    }

    @Override
    public String getBrazilGroupStandings() {
        return "Grupo C: 1 Brasil - 2 jogos, 1 vitoria, 1 empate, 0 derrotas, 4 gols pro, 1 contra, saldo +3, 4 pontos; "
                + "2 Morocco - 4 pontos; 3 Scotland - 3 pontos; 4 Haiti - 0 pontos.";
    }

    @Override
    public List<String> getWorldCupNews() {
        return List.of(
                "A Copa 2026 sera sediada por Estados Unidos, Canada e Mexico.",
                "O Brasil segue como uma das selecoes mais procuradas em produtos oficiais e de treino."
        );
    }
}
