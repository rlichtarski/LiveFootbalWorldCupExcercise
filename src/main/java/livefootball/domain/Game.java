package livefootball.domain;

import java.util.Objects;
import java.util.UUID;

public record Game(
        UUID id,
        Team homeTeam,
        Team awayTeam,
        Score homeScore,
        Score awayScore
) {

    public Game(Team homeTeam, Team awayTeam) {
        this(UUID.randomUUID(), homeTeam, awayTeam, new Score(0), new Score(0));
    }

    public Game updateGameScore(Score homeScore, Score awayScore) {
        return new Game(id, homeTeam, awayTeam, homeScore, awayScore);
    }

    public Score getOverallScore() {
        return homeScore.addScore(awayScore);
    }

}
