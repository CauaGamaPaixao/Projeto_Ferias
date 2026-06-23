package br.com.projetoferias.football;

import java.util.List;

public interface FootballDataProvider {

    List<String> getNextBrazilMatches();

    String getBrazilTeamInfo();

    String getBrazilGroupStandings();

    List<String> getWorldCupNews();
}
