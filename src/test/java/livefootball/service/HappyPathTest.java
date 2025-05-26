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
        final Game game = liveFootballService.startGame(mexicoHomeTeam, canadaHomeTeam);
        // then
        assertAll(
                () -> assertThat(game.homeTeam()).isEqualTo(mexicoHomeTeam),
                () -> assertThat(game.awayTeam()).isEqualTo(canadaHomeTeam),
                () -> assertThat(game.homeScore()).isZero(),
                () -> assertThat(game.awayScore()).isZero(),
                () -> assertThat(game.getOverallScore()).isZero(),
                () -> assertThat(liveFootballService.getGamesLiveScoreboard())
                        .hasSize(1)
                        .contains(game)
        );

        //4. The user displayed a live scoreboard and there is one match Mexico-Canada with the score: 0-0.
        assertThat(liveFootballService.getLiveScoreboardInfoAsString()).isEqualTo("[Mexico-Canada: 0-0]");

        //5. The user starts a game with home team Spain and away team Brazil. The score is 0-0.
        final Game game2 = liveFootballService.startGame(spainHomeTeam, brazilAwayTeam);
        assertAll(
                () -> assertThat(game2.homeTeam()).isEqualTo(spainHomeTeam),
                () -> assertThat(game2.awayTeam()).isEqualTo(brazilAwayTeam),
                () -> assertThat(game2.homeScore()).isZero(),
                () -> assertThat(game2.awayScore()).isZero(),
                () -> assertThat(game2.getOverallScore()).isZero(),
                () -> assertThat(liveFootballService.getGamesLiveScoreboard())
                        .hasSize(2)
                        .contains(game2)
        );

        //6. The user updated the score for the match Mexico-Canada: 3-1
        //7. The user displayed a live scoreboard and there are two matches: Mexico-Canada (3-1) and Spain-Brazil (0-0).
        //8. The user updated the score for the match Spain-Brazil: 1-1
        //9. The user finished the match Mexico-Canada.
        //10. The user displayed a live scoreboard and there is one match: Spain-Brazil (1-1).
        //11. The user starts a game with home team Argentina and away team Australia. The score is 0-0.
        //12. The user updated the score for the match Argentina-Australia: 2-2
        //13. The user finished the match Argentina-Australia.
        //14. The user displayed the summary and there are two past matches in the following order:  Argentina-Australia: (2-2) Mexico-Canada (3-1).

    }

}