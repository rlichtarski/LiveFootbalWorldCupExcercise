package livefootball.service;

import livefootball.domain.Game;

import java.util.ArrayList;
import java.util.List;

public class LiveFootballService {
    private final List<Game> gamesSummary = new ArrayList<>();
    private final List<Game> gamesLiveScoreboard = new ArrayList<>();

    List<Game> getGamesLiveScoreboard() {
        return gamesLiveScoreboard;
    }

    List<Game> getGamesSummary() {
        return gamesSummary;
    }

    String getLiveGamesInfo() {
        return gamesLiveScoreboard.stream().map(
                game ->
                        game.homeTeam() + "-"
                        + game.awayTeam() + ": "
                        + game.homeScore() + "-"
                        + game.awayScore()
        ).toList().toString();
    }

    String getSummaryGamesInfo() {
        return gamesSummary.stream().map(
                game ->
                        game.homeTeam() + "-"
                        + game.awayTeam() + ": "
                        + game.homeScore() + "-"
                        + game.awayScore()
        ).toList().toString();
    }

    Game startGame(final String homeTeam, final String awayTeam) {
        validateGameBeforeStart(homeTeam, awayTeam);
        final Game game = new Game(homeTeam, awayTeam);
        gamesLiveScoreboard.add(game);
        return game;
    }

    private void validateGameBeforeStart(final String homeTeam, final String awayTeam) {
        if (gamesLiveScoreboard.stream()
                .anyMatch(game -> checkIfMatchIsAlreadyOngoing(homeTeam, awayTeam, game))
        ) {
            throw new IllegalArgumentException("This game is already ongoing");
        }
    }

    private static boolean checkIfMatchIsAlreadyOngoing(final String homeTeam, final String awayTeam, final Game game) {
        return game.homeTeam().equals(homeTeam) || game.awayTeam().equals(awayTeam);
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
