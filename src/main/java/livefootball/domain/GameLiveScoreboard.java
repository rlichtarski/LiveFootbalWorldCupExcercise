package livefootball.domain;

import java.util.ArrayList;
import java.util.List;

class GameLiveScoreboard {

    private final List<Game> liveScoreboard = new ArrayList<>();

    List<Game> getLiveScoreboard() {
        return liveScoreboard;
    }

    void add(Game game) {
        liveScoreboard.add(game);
    }

    void remove(Game game) {
        liveScoreboard.remove(game);
    }

    Game updateGameScore(Game game, final Score homeScore, final Score awayScore) {
        final int gameIndex = liveScoreboard.indexOf(game);
        final Game updatedScoreGame = game.updateGameScore(homeScore, awayScore);
        liveScoreboard.set(gameIndex, updatedScoreGame);
        return updatedScoreGame;
    }

    void validateGameBeforeStart(final Team homeTeam, final Team awayTeam, List<Game> gamesLiveScoreboard) {
        if (gamesLiveScoreboard.stream()
                .anyMatch(game -> checkIfMatchIsAlreadyOngoing(homeTeam, awayTeam, game))
        ) {
            throw new IllegalArgumentException("This game is already ongoing");
        }
    }

    private static boolean checkIfMatchIsAlreadyOngoing(final Team homeTeam, final Team awayTeam, final Game game) {
        return game.homeTeam().equals(homeTeam) || game.awayTeam().equals(awayTeam);
    }

    void validateBy(final Team homeTeam, final Team awayTeam) {
        validateGameBeforeStart(homeTeam, awayTeam, liveScoreboard);
    }

//    String getLiveScoreboardInfoAsString() {
//        return liveScoreboard.stream()
//                .map(g -> "%s-%s: %d-%d".formatted(
//                        g.homeTeam().value(), g.awayTeam().value(), g.homeScore().value(), g.awayScore().value()))
//                .toList()
//                .toString();
//    }
}
