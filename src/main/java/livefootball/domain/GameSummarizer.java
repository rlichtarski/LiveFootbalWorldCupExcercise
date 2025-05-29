package livefootball.domain;

import java.util.ArrayList;
import java.util.List;

class GameSummarizer {

    private final List<Game> summary = new ArrayList<>();

    void addGameToSummary(Game game) {
        summary.add(0, game);
        summary.sort(
                (g1, g2) -> Integer.compare(
                        g2.getOverallScore().value(),
                        g1.getOverallScore().value()));
    }

    List<Game> getSummary() {
        return List.copyOf(summary);
    }

}
