package livefootball.domain;

public record Game(
        HomeTeam homeTeam,
        AwayTeam awayTeam,
        int homeScore,
        int awayScore
) {

    public Game(HomeTeam homeTeam, AwayTeam awayTeam) {
        this(homeTeam, awayTeam, 0, 0);
    }

    public Game copyWithGameScore(int homeScore, int awayScore) {
        return new Game(homeTeam, awayTeam, homeScore, awayScore);
    }

    public int getOverallScore() {
        return homeScore + awayScore;
    }

}
