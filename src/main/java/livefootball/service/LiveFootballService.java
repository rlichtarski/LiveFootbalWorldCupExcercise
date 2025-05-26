package livefootball.service;

import livefootball.domain.AwayTeam;
import livefootball.domain.Game;
import livefootball.domain.HomeTeam;

import java.util.List;

public class LiveFootballService {
    private final GameValidator gameValidator = new GameValidator();
    private final GameSummarizer gameSummarizer = new GameSummarizer();
    private final GameLiveScoreboard gameLiveScoreboard = new GameLiveScoreboard();

    Game startGame(final HomeTeam homeTeam, final AwayTeam awayTeam) {
        gameValidator.validateGameBeforeStart(homeTeam, awayTeam, gameLiveScoreboard.getLiveScoreboard());
        final Game game = new Game(homeTeam, awayTeam);
        gameLiveScoreboard.add(game);
        return game;
    }

    public String getLiveScoreboardInfoAsString() {
        return gameLiveScoreboard.getLiveScoreboardInfoAsString();
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
