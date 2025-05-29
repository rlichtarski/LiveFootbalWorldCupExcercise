package livefootball.domain;

import java.util.List;

public class LiveFootballFacade {
    private final GameSummarizer gameSummarizer = new GameSummarizer();
    private final GameLiveScoreboard gameLiveScoreboard = new GameLiveScoreboard();
    private final GameLauncher gameLauncher = new GameLauncher(gameLiveScoreboard);

    Game startGame(final Team homeTeam, final Team awayTeam) {
        return gameLauncher.startGame(homeTeam, awayTeam);
    }

    public Game updateGameScore(Game game, final Score homeScore, final Score awayScore) {
        return gameLiveScoreboard.updateGameScore(game, homeScore, awayScore);
    }

    public List<Game> getGamesLiveScoreboard() {
        return gameLiveScoreboard.getLiveScoreboard();
    }

    public List<Game> getGamesSummary() {
        return gameSummarizer.getSummary();
    }

    void finishGame(final Game game) {
        gameSummarizer.addGameToSummary(game);
        gameLiveScoreboard.removeGameFromLiveScoreboard(game);
    }
}
