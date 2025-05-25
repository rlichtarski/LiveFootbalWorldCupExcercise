package livefootball.service;

import livefootball.domain.Game;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class LiveFootballServiceTest {

    @Test
    public void user_displays_the_games_live_scoreboard_and_there_are_no_live_games() {
        // given
        final LiveFootballService liveFootballService = new LiveFootballService();

        // when
        final List<Game> gamesLiveScoreboard = liveFootballService.getGamesLiveScoreboard();

        // then
        assertEquals(0, gamesLiveScoreboard.size());
    }

    @Test
    public void user_displays_the_games_summary_and_there_are_no_past_games() {
        // given
        final LiveFootballService liveFootballService = new LiveFootballService();

        // when
        final List<Game> gamesSummary = liveFootballService.getGamesSummary();

        // then
        assertEquals(0, gamesSummary.size());
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
        assertEquals(homeTeam, game.getHomeTeam());
        assertEquals(awayTeam, game.getAwayTeam());
        assertEquals(0, game.getHomeScore());
        assertEquals(0, game.getAwayScore());
        assertEquals(0, game.getOverallScore());
        assertEquals(1, liveFootballService.getGamesLiveScoreboard().size());
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
        assertEquals(2, liveFootballService.getGamesLiveScoreboard().size());
        assertTrue(liveFootballService.getGamesLiveScoreboard().contains(firstGame));
        assertTrue(liveFootballService.getGamesLiveScoreboard().contains(secondGame));
    }

    @Test
    public void user_updates_the_score_for_the_live_game_and_the_score_is_updated() {
        // given
        final LiveFootballService liveFootballService = new LiveFootballService();
        final String homeTeam = "Mexico";
        final String awayTeam = "Canada";
        final Game firstGame = liveFootballService.startGame(homeTeam, awayTeam);

        // when
        liveFootballService.updateGameScore(firstGame, 3, 1);

        // then
        assertEquals(3, firstGame.getHomeScore());
        assertEquals(1, firstGame.getAwayScore());
    }


    @Test
    public void user_updates_two_games_and_receives_live_games_info() {
        // given
        final LiveFootballService liveFootballService = new LiveFootballService();
        final String firstGameHomeTeam = "Mexico";
        final String firstGameAwayTeam = "Canada";
        final String secondGameHomeTeam = "Spain";
        final String secondGameAwayTeam = "Brazil";
        final Game firstGame = liveFootballService.startGame(firstGameHomeTeam, firstGameAwayTeam);
        final Game secondGame = liveFootballService.startGame(secondGameHomeTeam, secondGameAwayTeam);

        // when
        liveFootballService.updateGameScore(firstGame, 3, 1);
        liveFootballService.updateGameScore(secondGame, 1, 1);

        // then
        assertEquals("[Mexico-Canada: 3-1, Spain-Brazil: 1-1]", liveFootballService.getLiveGamesInfo());
    }

    @Test
    public void user_finishes_the_game_and_it_gets_removed_from_live_scoreboard_and_gets_added_to_games_summary() {
        // given
        final LiveFootballService liveFootballService = new LiveFootballService();
        final String homeTeam = "Mexico";
        final String awayTeam = "Canada";
        final Game game = liveFootballService.startGame(homeTeam, awayTeam);

        // when
        liveFootballService.finishGame(game);

        // then
        assertFalse(liveFootballService.getGamesLiveScoreboard().contains(game));
        assertTrue(liveFootballService.getGamesSummary().contains(game));
    }

}