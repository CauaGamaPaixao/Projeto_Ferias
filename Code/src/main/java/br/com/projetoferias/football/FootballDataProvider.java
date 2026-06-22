package br.com.projetoferias.football;

import java.util.List;

public interface FootballDataProvider {

    List<String> getNextBrazilMatches();

    String getBrazilTeamInfo();

    List<String> getWorldCupNews();
}
