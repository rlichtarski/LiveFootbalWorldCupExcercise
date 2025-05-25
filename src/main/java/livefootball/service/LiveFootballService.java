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
                        game.getHomeTeam() + "-"
                        + game.getAwayTeam() + ": "
                        + game.getHomeScore() + "-"
                        + game.getAwayScore()
        ).toList().toString();
    }

    String getSummaryGamesInfo() {
        return gamesSummary.stream().map(
                game ->
                        game.getHomeTeam() + "-"
                        + game.getAwayTeam() + ": "
                        + game.getHomeScore() + "-"
                        + game.getAwayScore()
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
                .anyMatch(game -> game.getHomeTeam().equals(homeTeam) || game.getAwayTeam().equals(awayTeam))
        ) {
            throw new IllegalArgumentException("This game is already ongoing");
        }
    }

    void updateGameScore(final Game game, final int homeScore, final int awayScore) {
        game.setScore(homeScore, awayScore);
    }

    void finishGame(final Game game) {
        gamesLiveScoreboard.remove(game);
        gamesSummary.add(game);
    }
}
