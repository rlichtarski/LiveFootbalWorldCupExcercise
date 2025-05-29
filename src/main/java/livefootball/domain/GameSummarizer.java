package livefootball.domain;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

class GameSummarizer {

    private final List<Game> summary = new ArrayList<>();

    List<Game> getSummary() {
        return summary.stream()
                .sorted(Comparator
                        .comparingInt(Game::getOverallScore)
                        .reversed())
                .toList();
    }

    void add(Game game) {
        summary.add(0, game);
    }

    String getSummaryGamesInfoAsString() {
        return getSummary().stream()
                .map(g -> "%s-%s: %d-%d".formatted(
                        g.homeTeam().value(), g.awayTeam().value(), g.homeScore(), g.awayScore()))
                .toList()
                .toString();
    }

}
