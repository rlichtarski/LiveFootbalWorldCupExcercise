package livefootball.service;

import livefootball.domain.AwayTeam;
import livefootball.domain.Game;
import livefootball.domain.HomeTeam;

import java.util.List;

class GameValidator {

    void validateGameBeforeStart(final HomeTeam homeTeam, final AwayTeam awayTeam, List<Game> gamesLiveScoreboard) {
        if (gamesLiveScoreboard.stream()
                .anyMatch(game -> checkIfMatchIsAlreadyOngoing(homeTeam, awayTeam, game))
        ) {
            throw new IllegalArgumentException("This game is already ongoing");
        }
    }

    private static boolean checkIfMatchIsAlreadyOngoing(final HomeTeam homeTeam, final AwayTeam awayTeam, final Game game) {
        return game.homeTeam().equals(homeTeam) || game.awayTeam().equals(awayTeam);
    }

}
