package livefootball.service;

import livefootball.domain.Game;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class LiveFootballServiceTest {

    @Test
    public void user_displays_the_games_live_scoreboard_and_there_are_no_live_games() {
        // given
        final LiveFootballService liveFootballService = new LiveFootballService();

        // when
        final List<Game> gamesLiveScoreboard = liveFootballService.getGamesLiveScoreboard();

        // then
        assertEquals(gamesLiveScoreboard.size(), 0);
    }

    @Test
    public void user_displays_the_games_summary_and_there_are_no_past_games() {
        // given
        final LiveFootballService liveFootballService = new LiveFootballService();

        // when
        final List<Game> gamesSummary = liveFootballService.getGamesSummary();

        // then
        assertEquals(gamesSummary.size(), 0);
    }

    @Test
    public void user_starts_a_game_and_it_adds_this_game_to_games_live_scoreboard_with_0_to_0_score() {
        // given
        final LiveFootballService liveFootballService = new LiveFootballService();
        final String homeTeam = "Mexico";
        final String awayTeam = "Canada";

        // when
        final Game game = liveFootballService.startGame(homeTeam, awayTeam);

        // then
        assertEquals(game.getHomeTeam(), homeTeam);
        assertEquals(game.getAwayTeam(), awayTeam);
        assertEquals(game.getHomeScore(), 0);
        assertEquals(game.getAwayScore(), 0);
        assertEquals(game.getOverallScore(), 0);
        assertEquals(liveFootballService.getGamesLiveScoreboard().size(), 1);
        assertTrue(liveFootballService.getGamesLiveScoreboard().contains(game));
    }

    @Test
    public void user_starts_two_games_and_two_games_are_present_in_games_live_scoreboard() {
        // given
        final LiveFootballService liveFootballService = new LiveFootballService();
        final String firstGameHomeTeam = "Mexico";
        final String firstGameAwayTeam = "Canada";
        final String secondGameHomeTeam = "Spain";
        final String secondGameAwayTeam = "Brazil";

        // when
        final Game firstGame = liveFootballService.startGame(firstGameHomeTeam, firstGameAwayTeam);
        final Game secondGame = liveFootballService.startGame(secondGameHomeTeam, secondGameAwayTeam);

        // then
        assertEquals(liveFootballService.getGamesLiveScoreboard().size(), 2);
        assertTrue(liveFootballService.getGamesLiveScoreboard().contains(firstGame));
        assertTrue(liveFootballService.getGamesLiveScoreboard().contains(secondGame));
    }

}