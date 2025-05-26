package livefootball.service;

import livefootball.domain.Game;

import java.util.ArrayList;
import java.util.List;

class GameSummarizer {

    private final List<Game> summary = new ArrayList<>();

    List<Game> getSummary() {
        return summary;
    }

    void add(Game game) {
        summary.add(game);
    }

    String getSummaryGamesInfoAsString() {
        return summary.stream()
                .map(g -> "%s-%s: %d-%d".formatted(
                        g.homeTeam().value(), g.awayTeam().value(), g.homeScore(), g.awayScore()))
                .toList()
                .toString();
    }

}
