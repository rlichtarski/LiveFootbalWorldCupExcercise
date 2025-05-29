package livefootball.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

class GameLiveScoreboard {

    private final Map<UUID, Game> liveScoreboard = new ConcurrentHashMap<>();

    List<Game> getLiveScoreboard() {
        return new ArrayList<>(liveScoreboard.values());
    }

    void addGameToLiveScoreboard(Game game) {
        liveScoreboard.put(game.id(), game);
    }

    void removeGameFromLiveScoreboard(Game game) {
        liveScoreboard.remove(game.id());
    }

    Game updateGameScore(Game game, final Score homeScore, final Score awayScore) {
        if (!liveScoreboard.containsKey(game.id())) {
            throw new IllegalArgumentException("Game does not exists");
        }
        final Game foundGameToBeUpdated = liveScoreboard.get(game.id());
        final Game updatedScoreGame = foundGameToBeUpdated.updateGameScore(homeScore, awayScore);
        liveScoreboard.put(game.id(), updatedScoreGame);
        return updatedScoreGame;
    }

    void validateGameBeforeStart(final Team homeTeam, final Team awayTeam, Map<UUID, Game> gamesLiveScoreboard) {
        if (gamesLiveScoreboard.values().stream()
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

}
