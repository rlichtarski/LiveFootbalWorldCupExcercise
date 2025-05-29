package livefootball.domain;

class GameLauncher {

    private final GameValidator gameValidator;
    private final GameLiveScoreboard gameLiveScoreboard;

    GameLauncher(final GameValidator gameValidator, final GameLiveScoreboard gameLiveScoreboard) {
        this.gameValidator = gameValidator;
        this.gameLiveScoreboard = gameLiveScoreboard;
    }

    Game startGame(final Team homeTeam, final Team awayTeam) {
        gameValidator.validateGameBeforeStart(homeTeam, awayTeam, gameLiveScoreboard.getLiveScoreboard());
        final Game game = new Game(homeTeam, awayTeam);
        gameLiveScoreboard.add(game);
        return game;
    }

}
