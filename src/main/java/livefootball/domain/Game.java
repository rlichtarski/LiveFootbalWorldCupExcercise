package livefootball.domain;

public record Game(
        Team homeTeam,
        Team awayTeam,
        Score homeScore,
        Score awayScore
) {

    public Game(Team homeTeam, Team awayTeam) {
        this(homeTeam, awayTeam, new Score(0), new Score(0));
    }

    public Game copyWithGameScore(Score homeScore, Score awayScore) {
        return new Game(homeTeam, awayTeam, homeScore, awayScore);
    }

    public int getOverallScore() {
        return homeScore.value() + awayScore.value();
    }

}
