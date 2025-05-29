package livefootball.domain;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

class GameSummarizer {

    private static final Comparator<Game> TOTAL_DESC =
            Comparator.comparingInt(Game::getOverallScore).reversed();

    private final List<Game> summary = new ArrayList<>();

    void add(Game game) {
        summary.add(0, game);
        summary.sort(
                (g1, g2) -> Integer.compare(
                        g2.getOverallScore(),
                        g1.getOverallScore()));
    }

    List<Game> getSummary() {
        return List.copyOf(summary);
    }

    String getSummaryGamesInfoAsString() {
        return getSummary().stream()
                .map(g -> "%s-%s: %d-%d".formatted(
                        g.homeTeam().value(), g.awayTeam().value(), g.homeScore().value(), g.awayScore().value()))
                .toList()
                .toString();
    }

}
