package livefootball.service;

import livefootball.domain.Game;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class LiveFootballServiceTest {

    @Test
    public void user_displays_the_games_live_scoreboard_and_there_are_no_live_matches() {
        // given
        final LiveFootballService liveFootballService = new LiveFootballService();

        // when
        final List<Game> gamesLiveScoreboard = liveFootballService.getGamesLiveScoreboard();

        // then
        assertEquals(gamesLiveScoreboard.size(), 0);
    }

    @Test
    public void user_displays_the_games_summary_and_there_are_no_past_matches() {
        // given
        final LiveFootballService liveFootballService = new LiveFootballService();

        // when
        final List<Game> gamesSummary = liveFootballService.getGamesSummary();

        // then
        assertEquals(gamesSummary.size(), 0);
    }

    @Test
    public void user_adds_a_game_and_it_adds_this_game_to_games_live_scoreboard_with_0_to_0_score() {
        // given

        // when

        // then
    }

}