package livefootball.service;

import livefootball.domain.AwayTeam;
import livefootball.domain.Game;
import livefootball.domain.HomeTeam;

import java.util.ArrayList;
import java.util.List;

public class LiveFootballService {
    private final GameValidator gameValidator = new GameValidator();

    private final List<Game> gamesSummary = new ArrayList<>();
    private final List<Game> gamesLiveScoreboard = new ArrayList<>();

    List<Game> getGamesLiveScoreboard() {
        return gamesLiveScoreboard;
    }

    List<Game> getGamesSummary() {
        return gamesSummary;
    }

    String getLiveGamesInfo() {
        return gamesLiveScoreboard.stream()
                .map(g -> "%s-%s: %d-%d".formatted(
                        g.homeTeam().value(), g.awayTeam().value(), g.homeScore(), g.awayScore()))
                .toList()
                .toString();
    }

    String getSummaryGamesInfo() {
        return gamesSummary.stream()
                .map(g -> "%s-%s: %d-%d".formatted(
                        g.homeTeam().value(), g.awayTeam().value(), g.homeScore(), g.awayScore()))
                .toList()
                .toString();
    }

    Game startGame(final HomeTeam homeTeam, final AwayTeam awayTeam) {
        gameValidator.validateGameBeforeStart(homeTeam, awayTeam, gamesLiveScoreboard);
        final Game game = new Game(homeTeam, awayTeam);
        gamesLiveScoreboard.add(game);
        return game;
    }

    Game updateGameScore(Game game, final int homeScore, final int awayScore) {
        final int gameIndex = gamesLiveScoreboard.indexOf(game);
        final Game updatedScoreGame = game.copyWithGameScore(homeScore, awayScore);
        gamesLiveScoreboard.set(gameIndex, updatedScoreGame);
        return updatedScoreGame;
    }

    void finishGame(final Game game) {
        gamesLiveScoreboard.remove(game);
        gamesSummary.add(game);
    }
}
