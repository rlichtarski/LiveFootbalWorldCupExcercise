package livefootball.domain;

class Game {
    private final String homeTeam;
    private final String awayTeam;
    private int homeScore;
    private int awayScore;

    Game(final String homeTeam, final String awayTeam, final int homeScore, final int awayScore) {
        this.homeTeam = homeTeam;
        this.awayTeam = awayTeam;
        this.homeScore = 0;
        this.awayScore = 0;
    }

    String getHomeTeam() {
        return homeTeam;
    }

    String getAwayTeam() {
        return awayTeam;
    }

    int getHomeScore() {
        return homeScore;
    }

    int getAwayScore() {
        return awayScore;
    }

    public void setScore(int homeScore, int awayScore) {
        this.homeScore = homeScore;
        this.awayScore = awayScore;
    }

    public int getOverallScore() {
        return homeScore + awayScore;
    }

}
