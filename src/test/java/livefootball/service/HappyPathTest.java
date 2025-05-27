package livefootball.service;


import livefootball.domain.AwayTeam;
import livefootball.domain.Game;
import livefootball.domain.HomeTeam;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

class HappyPathTest {

    private LiveFootballService liveFootballService;
    final HomeTeam mexicoHomeTeam = new HomeTeam("Mexico");
    final AwayTeam canadaHomeTeam = new AwayTeam("Canada");
    final HomeTeam spainHomeTeam = new HomeTeam("Spain");
    final AwayTeam brazilAwayTeam = new AwayTeam("Brazil");
    final HomeTeam argentinaHomeTeam = new HomeTeam("Argentina");
    final AwayTeam australiaAwayTeam = new AwayTeam("Australia");

    @BeforeEach
    void setUp() {
        liveFootballService = new LiveFootballService();
    }

    @Test
    public void happyPathTest() {

        //Happy path:

        //1. The user displayed a live scoreboard and there are no live matches.
        // when
        final List<Game> gamesLiveScoreboard = liveFootballService.getGamesLiveScoreboard();
        // then
        assertThat(0).isEqualTo(gamesLiveScoreboard.size());

        //2. The user displayed a summary and there are no past matches.
        // when
        final List<Game> gamesSummary = liveFootballService.getGamesSummary();
        // then
        assertThat(0).isEqualTo(gamesSummary.size());

        //3. The user starts a game with home team Mexico and away team Canada. The score is 0-0
        // when
        final Game gameMexicoCanada = liveFootballService.startGame(mexicoHomeTeam, canadaHomeTeam);
        // then
        assertAll(
                () -> assertThat(gameMexicoCanada.homeTeam()).isEqualTo(mexicoHomeTeam),
                () -> assertThat(gameMexicoCanada.awayTeam()).isEqualTo(canadaHomeTeam),
                () -> assertThat(gameMexicoCanada.homeScore()).isZero(),
                () -> assertThat(gameMexicoCanada.awayScore()).isZero(),
                () -> assertThat(gameMexicoCanada.getOverallScore()).isZero(),
                () -> assertThat(liveFootballService.getGamesLiveScoreboard())
                        .hasSize(1)
                        .contains(gameMexicoCanada)
        );

        //4. The user displayed a live scoreboard and there is one match Mexico-Canada with the score: 0-0.
        assertThat(liveFootballService.getLiveScoreboardInfoAsString()).isEqualTo("[Mexico-Canada: 0-0]");

        //5. The user starts a game with home team Spain and away team Brazil. The score is 0-0.
        final Game gameSpainBrazil = liveFootballService.startGame(spainHomeTeam, brazilAwayTeam);
        assertAll(
                () -> assertThat(gameSpainBrazil.homeTeam()).isEqualTo(spainHomeTeam),
                () -> assertThat(gameSpainBrazil.awayTeam()).isEqualTo(brazilAwayTeam),
                () -> assertThat(gameSpainBrazil.homeScore()).isZero(),
                () -> assertThat(gameSpainBrazil.awayScore()).isZero(),
                () -> assertThat(gameSpainBrazil.getOverallScore()).isZero(),
                () -> assertThat(liveFootballService.getGamesLiveScoreboard())
                        .hasSize(2)
                        .contains(gameSpainBrazil)
        );

        //6. The user updated the score for the match Mexico-Canada: 3-1
        final Game updatedMexicoCanadaGame = liveFootballService.updateGameScore(gameMexicoCanada, 3, 1);
        assertAll(
                () -> assertThat(updatedMexicoCanadaGame.homeScore()).isEqualTo(3),
                () -> assertThat(updatedMexicoCanadaGame.awayScore()).isEqualTo(1),
                () -> assertThat(liveFootballService.getGamesLiveScoreboard())
                        .contains(updatedMexicoCanadaGame),
                () -> assertThat(liveFootballService.getGamesLiveScoreboard())
                        .doesNotContain(gameMexicoCanada)
        );

        //7. The user displayed a live scoreboard and there are two matches: Mexico-Canada (3-1) and Spain-Brazil (0-0).
        assertThat(liveFootballService.getLiveScoreboardInfoAsString()).isEqualTo("[Mexico-Canada: 3-1, Spain-Brazil: 0-0]");

        //8. The user updated the score for the match Spain-Brazil: 1-1
        final Game updatedSpainBrazilGame = liveFootballService.updateGameScore(gameSpainBrazil, 1, 1);

        //9. The user finished the match Mexico-Canada.
        liveFootballService.finishGame(updatedMexicoCanadaGame);

        //10. The user displayed a live scoreboard and there is one match: Spain-Brazil (1-1).
        assertThat(liveFootballService.getLiveScoreboardInfoAsString()).isEqualTo("[Spain-Brazil: 1-1]");

        //11. The user starts a game with home team Argentina and away team Australia. The score is 0-0.
        final Game gameArgentinaAustralia = liveFootballService.startGame(argentinaHomeTeam, australiaAwayTeam);
        assertAll(
                () -> assertThat(gameArgentinaAustralia.homeTeam()).isEqualTo(argentinaHomeTeam),
                () -> assertThat(gameArgentinaAustralia.awayTeam()).isEqualTo(australiaAwayTeam),
                () -> assertThat(gameArgentinaAustralia.homeScore()).isZero(),
                () -> assertThat(gameArgentinaAustralia.awayScore()).isZero(),
                () -> assertThat(gameArgentinaAustralia.getOverallScore()).isZero()
        );

        //12. The user updated the score for the match Argentina-Australia: 2-2
        final Game updatedArgentinaAustraliaGame = liveFootballService.updateGameScore(gameArgentinaAustralia, 2, 2);
        assertAll(
                () -> assertThat(updatedArgentinaAustraliaGame.homeScore()).isEqualTo(2),
                () -> assertThat(updatedArgentinaAustraliaGame.awayScore()).isEqualTo(2),
                () -> assertThat(liveFootballService.getGamesLiveScoreboard())
                        .contains(updatedArgentinaAustraliaGame),
                () -> assertThat(liveFootballService.getGamesLiveScoreboard())
                        .doesNotContain(gameArgentinaAustralia)
        );

        //13. The user finished the match Argentina-Australia.
        liveFootballService.finishGame(updatedArgentinaAustraliaGame);

        //14. The user displayed the summary and there are two past matches in the following order: Argentina-Australia: (2-2), Mexico-Canada (3-1).
        assertThat(liveFootballService.getSummaryGamesInfoAsString()).isEqualTo("[Argentina-Australia: 2-2, Mexico-Canada: 3-1]");
    }

}