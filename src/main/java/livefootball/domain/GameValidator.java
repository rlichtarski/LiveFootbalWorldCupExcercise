package livefootball.domain;

import java.util.List;

class GameValidator {

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

}
