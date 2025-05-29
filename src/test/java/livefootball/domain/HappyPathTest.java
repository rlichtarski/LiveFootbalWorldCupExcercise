package livefootball.domain;


import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

class HappyPathTest {

    private final LiveFootballFacade liveFootballFacade = new LiveFootballFacade();

    @Test
    public void happyPathTest() {

        //Happy path:

        //1. The user displayed a live scoreboard and there are no live matches.
        // when
        final List<Game> gamesLiveScoreboard = liveFootballFacade.getGamesLiveScoreboard();
        // then
        assertThat(gamesLiveScoreboard).isEmpty();

        //2. The user displayed a summary and there are no past matches.
        // when
        final List<Game> gamesSummary = liveFootballFacade.getGamesSummary();
        // then
        assertThat(gamesSummary).isEmpty();

        //3. The user starts a game with home team Mexico and away team Canada. The score is 0-0
        // when
        final Game gameMexicoCanada = liveFootballFacade.startGame(new Team("Mexico"), new Team("Canada"));
        // then
        assertAll(
                () -> assertThat(gameMexicoCanada.homeTeam()).isEqualTo(new Team("Mexico")),
                () -> assertThat(gameMexicoCanada.awayTeam()).isEqualTo(new Team("Canada")),
                () -> assertThat(gameMexicoCanada.homeScore().value()).isZero(),
                () -> assertThat(gameMexicoCanada.awayScore().value()).isZero(),
                () -> assertThat(gameMexicoCanada.getOverallScore().value()).isZero()
        );
        assertThat(liveFootballFacade.getGamesLiveScoreboard())
                .hasSize(1)
                .contains(gameMexicoCanada);

        //4. The user displayed a live scoreboard and there is one match Mexico-Canada with the score: 0-0.
        {
            final Game game = liveFootballFacade.getGamesLiveScoreboard().get(0);
            assertAll(
                    () -> assertThat(game.homeTeam()).isEqualTo(new Team("Mexico")),
                    () -> assertThat(game.awayTeam()).isEqualTo(new Team("Canada")),
                    () -> assertThat(game.homeScore().value()).isZero(),
                    () -> assertThat(game.awayScore().value()).isZero(),
                    () -> assertThat(game.getOverallScore().value()).isZero()
            );
        }

        //5. The user starts a game with home team Spain and away team Brazil. The score is 0-0.
        final Game gameSpainBrazil = liveFootballFacade.startGame(new Team("Spain"), new Team("Brazil"));
        assertAll(
                () -> assertThat(gameSpainBrazil.homeTeam()).isEqualTo(new Team("Spain")),
                () -> assertThat(gameSpainBrazil.awayTeam()).isEqualTo(new Team("Brazil")),
                () -> assertThat(gameSpainBrazil.homeScore().value()).isZero(),
                () -> assertThat(gameSpainBrazil.awayScore().value()).isZero(),
                () -> assertThat(gameSpainBrazil.getOverallScore().value()).isZero()
        );
        assertThat(liveFootballFacade.getGamesLiveScoreboard())
                .hasSize(2)
                .contains(gameSpainBrazil);

        //6. The user updated the score for the match Mexico-Canada: 3-1
        final Game updatedMexicoCanadaGame = liveFootballFacade.updateGameScore(gameMexicoCanada, new Score(3), new Score(1));
        assertAll(
                () -> assertThat(updatedMexicoCanadaGame.homeScore().value()).isEqualTo(3),
                () -> assertThat(updatedMexicoCanadaGame.awayScore().value()).isEqualTo(1)
        );
        assertAll(
                () -> assertThat(liveFootballFacade.getGamesLiveScoreboard())
                        .contains(updatedMexicoCanadaGame),
                () -> assertThat(liveFootballFacade.getGamesLiveScoreboard())
                        .doesNotContain(gameMexicoCanada)
        );

        //7. The user displayed a live scoreboard and there are two matches: Mexico-Canada (3-1) and Spain-Brazil (0-0).
        {
            final Game game1 = liveFootballFacade.getGamesLiveScoreboard().get(0);
            final Game game2 = liveFootballFacade.getGamesLiveScoreboard().get(1);
            assertAll(
                    () -> assertThat(game1.homeTeam()).isEqualTo(new Team("Mexico")),
                    () -> assertThat(game1.awayTeam()).isEqualTo(new Team("Canada")),
                    () -> assertThat(game1.homeScore().value()).isEqualTo(3),
                    () -> assertThat(game1.awayScore().value()).isEqualTo(1),
                    () -> assertThat(game1.getOverallScore().value()).isEqualTo(4)
            );
            assertAll(
                    () -> assertThat(game2.homeTeam()).isEqualTo(new Team("Spain")),
                    () -> assertThat(game2.awayTeam()).isEqualTo(new Team("Brazil")),
                    () -> assertThat(game2.homeScore().value()).isZero(),
                    () -> assertThat(game2.awayScore().value()).isZero(),
                    () -> assertThat(game2.getOverallScore().value()).isZero()
            );
        }

        //8. The user updated the score for the match Spain-Brazil: 1-1
        final Game updatedSpainBrazilGame = liveFootballFacade.updateGameScore(gameSpainBrazil, new Score(1), new Score(1));

        //9. The user finished the match Mexico-Canada.
        liveFootballFacade.finishGame(updatedMexicoCanadaGame);

        //10. The user displayed a live scoreboard and there is one match: Spain-Brazil (1-1).
        {
            {
                final Game game1 = liveFootballFacade.getGamesLiveScoreboard().get(0);
                assertAll(
                        () -> assertThat(game1.homeTeam()).isEqualTo(new Team("Spain")),
                        () -> assertThat(game1.awayTeam()).isEqualTo(new Team("Brazil")),
                        () -> assertThat(game1.homeScore().value()).isEqualTo(1),
                        () -> assertThat(game1.awayScore().value()).isEqualTo(1),
                        () -> assertThat(game1.getOverallScore().value()).isEqualTo(2)
                );
            }
        }

        //11. The user starts a game with home team Argentina and away team Australia. The score is 0-0.
        final Game gameArgentinaAustralia = liveFootballFacade.startGame(new Team("Argentina"), new Team("Australia"));
        assertAll(
                () -> assertThat(gameArgentinaAustralia.homeTeam()).isEqualTo(new Team("Argentina")),
                () -> assertThat(gameArgentinaAustralia.awayTeam()).isEqualTo(new Team("Australia")),
                () -> assertThat(gameArgentinaAustralia.homeScore().value()).isZero(),
                () -> assertThat(gameArgentinaAustralia.awayScore().value()).isZero(),
                () -> assertThat(gameArgentinaAustralia.getOverallScore().value()).isZero()
        );

        //12. The user updated the score for the match Argentina-Australia: 2-2
        final Game updatedArgentinaAustraliaGame = liveFootballFacade.updateGameScore(gameArgentinaAustralia, new Score(2), new Score(2));
        assertAll(
                () -> assertThat(updatedArgentinaAustraliaGame.homeScore().value()).isEqualTo(2),
                () -> assertThat(updatedArgentinaAustraliaGame.awayScore().value()).isEqualTo(2)
        );
        assertAll(
                () -> assertThat(liveFootballFacade.getGamesLiveScoreboard())
                        .contains(updatedArgentinaAustraliaGame),
                () -> assertThat(liveFootballFacade.getGamesLiveScoreboard())
                        .doesNotContain(gameArgentinaAustralia)
        );

        //13. The user finished the match Argentina-Australia.
        liveFootballFacade.finishGame(updatedArgentinaAustraliaGame);

        //14. The user starts a game with home team Poland and away team Italy.
        final Game gamePolandItaly = liveFootballFacade.startGame(new Team("Poland"), new Team("Italy"));

        //15. The user updated the score for the match Argentina-Australia: 2-2
        final Game updatedPolandItalyGame = liveFootballFacade.updateGameScore(gamePolandItaly, new Score(5), new Score(4));
        assertAll(
                () -> assertThat(updatedPolandItalyGame.homeScore().value()).isEqualTo(5),
                () -> assertThat(updatedPolandItalyGame.awayScore().value()).isEqualTo(4)
        );
        assertAll(
                () -> assertThat(liveFootballFacade.getGamesLiveScoreboard())
                        .contains(updatedPolandItalyGame),
                () -> assertThat(liveFootballFacade.getGamesLiveScoreboard())
                        .doesNotContain(gamePolandItaly)
        );

        //16. The user finished the match Poland-Italy.
        liveFootballFacade.finishGame(updatedPolandItalyGame);

        /* 17. The user displayed the summary and there are three past matches in the following order:
        / - Poland-Italy: (5-4),
        / - Argentina-Australia: (2-2),
        / - Mexico-Canada (3-1).
        */
        {
            final Game game1 = liveFootballFacade.getGamesSummary().get(0);
            final Game game2 = liveFootballFacade.getGamesSummary().get(1);
            final Game game3 = liveFootballFacade.getGamesSummary().get(2);
            assertAll(
                    () -> assertThat(game1.homeTeam()).isEqualTo(new Team("Poland")),
                    () -> assertThat(game1.awayTeam()).isEqualTo(new Team("Italy")),
                    () -> assertThat(game1.homeScore().value()).isEqualTo(5),
                    () -> assertThat(game1.awayScore().value()).isEqualTo(4),
                    () -> assertThat(game1.getOverallScore().value()).isEqualTo(9)
            );
            assertAll(
                    () -> assertThat(game2.homeTeam()).isEqualTo(new Team("Argentina")),
                    () -> assertThat(game2.awayTeam()).isEqualTo(new Team("Australia")),
                    () -> assertThat(game2.homeScore().value()).isEqualTo(2),
                    () -> assertThat(game2.awayScore().value()).isEqualTo(2),
                    () -> assertThat(game2.getOverallScore().value()).isEqualTo(4)
            );
            assertAll(
                    () -> assertThat(game3.homeTeam()).isEqualTo(new Team("Mexico")),
                    () -> assertThat(game3.awayTeam()).isEqualTo(new Team("Canada")),
                    () -> assertThat(game3.homeScore().value()).isEqualTo(3),
                    () -> assertThat(game3.awayScore().value()).isEqualTo(1),
                    () -> assertThat(game3.getOverallScore().value()).isEqualTo(4)
            );
        }
    }

}