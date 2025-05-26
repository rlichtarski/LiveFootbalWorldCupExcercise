package livefootball.service;

import livefootball.domain.Game;

import java.util.ArrayList;
import java.util.List;

class GameSummarizer {

    private final List<Game> gamesSummary = new ArrayList<>();

    List<Game> getGamesSummary() {
        return gamesSummary;
    }

    void add(Game game) {
        gamesSummary.add(game);
    }

    String getSummaryGamesInfo() {
        return gamesSummary.stream()
                .map(g -> "%s-%s: %d-%d".formatted(
                        g.homeTeam().value(), g.awayTeam().value(), g.homeScore(), g.awayScore()))
                .toList()
                .toString();
    }

}
