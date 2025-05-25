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

    Game startGame(final String homeTeam, final String awayTeam) {
        final Game game = new Game(homeTeam, awayTeam);
        gamesLiveScoreboard.add(game);
        return game;
    }

    void updateGameScore(final Game game, final int homeScore, final int awayScore) {
        game.setScore(homeScore, awayScore);
    }
}
