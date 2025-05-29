package livefootball.domain;

public record Game(
        Team homeTeam,
        Team awayTeam,
        int homeScore,
        int awayScore
) {

    public Game(Team homeTeam, Team awayTeam) {
        this(homeTeam, awayTeam, 0, 0);
    }

    public Game copyWithGameScore(int homeScore, int awayScore) {
        return new Game(homeTeam, awayTeam, homeScore, awayScore);
    }

    public int getOverallScore() {
        return homeScore + awayScore;
    }

}
