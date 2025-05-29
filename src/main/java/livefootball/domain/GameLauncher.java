package livefootball.domain;

class GameLauncher {

    private final GameLiveScoreboard gameLiveScoreboard;

    GameLauncher(final GameLiveScoreboard gameLiveScoreboard) {
        this.gameLiveScoreboard = gameLiveScoreboard;
    }

    Game startGame(final Team homeTeam, final Team awayTeam) {
        gameLiveScoreboard.validateBy(homeTeam, awayTeam);
        final Game game = new Game(homeTeam, awayTeam);
        gameLiveScoreboard.add(game);
        return game;
    }

}
