package livefootball.domain;

import java.util.List;

public class LiveFootballFacade {
    private final GameValidator gameValidator = new GameValidator();
    private final GameSummarizer gameSummarizer = new GameSummarizer();
    private final GameLiveScoreboard gameLiveScoreboard = new GameLiveScoreboard();
    private final GameLauncher gameLauncher = new GameLauncher(gameValidator, gameLiveScoreboard);

    Game startGame(final Team homeTeam, final Team awayTeam) {
        return gameLauncher.startGame(homeTeam, awayTeam);
    }

    public String getLiveScoreboardInfoAsString() {
        return gameLiveScoreboard.getLiveScoreboardInfoAsString();
    }

    public String getSummaryGamesInfoAsString() {
        return gameSummarizer.getSummaryGamesInfoAsString();
    }

    public Game updateGameScore(Game game, final int homeScore, final int awayScore) {
        return gameLiveScoreboard.updateGameScore(game, homeScore, awayScore);
    }

    public List<Game> getGamesLiveScoreboard() {
        return gameLiveScoreboard.getLiveScoreboard();
    }

    public List<Game> getGamesSummary() {
        return gameSummarizer.getSummary();
    }

    void finishGame(final Game game) {
        gameLiveScoreboard.remove(game);
        gameSummarizer.add(game);
    }
}
