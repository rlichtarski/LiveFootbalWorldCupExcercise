package livefootball.service;

import livefootball.domain.Game;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertAll;

class LiveFootballServiceTest {

    @Test
    public void should_return_0_live_games_when_user_opens_live_scoreboard_for_the_first_time() {
        // given
        final LiveFootballService liveFootballService = new LiveFootballService();

        // when
        final List<Game> gamesLiveScoreboard = liveFootballService.getGamesLiveScoreboard();

        // then
        assertThat(0).isEqualTo(gamesLiveScoreboard.size());
    }

    @Test
    public void should_return_0_summary_games_when_user_opens_live_scoreboard_for_the_first_time() {
        // given
        final LiveFootballService liveFootballService = new LiveFootballService();

        // when
        final List<Game> gamesSummary = liveFootballService.getGamesSummary();

        // then
        assertThat(0).isEqualTo(gamesSummary.size());
    }

    @Test
    public void should_return_live_scoreboard_with_game_and_zero_scores_when_user_starts_game() {
        // given
        final LiveFootballService liveFootballService = new LiveFootballService();
        final String homeTeam = "Mexico";
        final String awayTeam = "Canada";

        // when
        final Game game = liveFootballService.startGame(homeTeam, awayTeam);

        // then
        assertAll(
                () -> assertThat(game.getHomeTeam()).isEqualTo(homeTeam),
                () -> assertThat(game.getAwayTeam()).isEqualTo(awayTeam),
                () -> assertThat(game.getHomeScore()).isZero(),
                () -> assertThat(game.getAwayScore()).isZero(),
                () -> assertThat(game.getOverallScore()).isZero(),
                () -> assertThat(liveFootballService.getGamesLiveScoreboard())
                        .hasSize(1)
                        .contains(game)
        );
    }

    @Test
    public void should_throw_exception_when_starting_a_game_which_is_already_ongoing() {
        // given
        final LiveFootballService liveFootballService = new LiveFootballService();
        final String homeTeam = "Mexico";
        final String awayTeam = "Canada";
        final Game game = liveFootballService.startGame(homeTeam, awayTeam);

        // when

        final Throwable throwable = catchThrowable(() -> liveFootballService.startGame(homeTeam, awayTeam));

        // then
        assertThat(throwable).isInstanceOf(IllegalArgumentException.class);
        assertThat(throwable.getMessage()).isEqualTo("This game is already ongoing");
    }

    @Test
    public void should_return_live_scoreboard_with_two_games_when_user_starts_two_games() {
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
        assertAll(
                () -> assertThat(liveFootballService.getGamesLiveScoreboard())
                        .hasSize(2)
                        .containsExactlyInAnyOrder(firstGame, secondGame),

                () -> assertThat(firstGame)
                        .extracting(Game::getHomeTeam, Game::getAwayTeam)
                        .containsExactly(firstGameHomeTeam, firstGameAwayTeam),

                () -> assertThat(secondGame)
                        .extracting(Game::getHomeTeam, Game::getAwayTeam)
                        .containsExactly(secondGameHomeTeam, secondGameAwayTeam)
        );
    }

    @Test
    public void should_return_game_with_updated_scores_when_user_updates_live_game_score() {
        // given
        final LiveFootballService liveFootballService = new LiveFootballService();
        final String homeTeam = "Mexico";
        final String awayTeam = "Canada";
        final Game firstGame = liveFootballService.startGame(homeTeam, awayTeam);

        // when
        liveFootballService.updateGameScore(firstGame, 3, 1);

        // then
        assertThat(3).isEqualTo(firstGame.getHomeScore());
        assertThat(1).isEqualTo(firstGame.getAwayScore());
    }


    @Test
    public void should_return_live_games_info_with_updated_scores_when_user_updates_two_games() {
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
        assertThat("[Mexico-Canada: 3-1, Spain-Brazil: 1-1]").isEqualTo(liveFootballService.getLiveGamesInfo());
    }

    @Test
    public void should_remove_game_from_live_scoreboard_and_add_to_summary_when_user_finishes_game() {
        // given
        final LiveFootballService liveFootballService = new LiveFootballService();
        final String homeTeam = "Mexico";
        final String awayTeam = "Canada";
        final Game game = liveFootballService.startGame(homeTeam, awayTeam);

        // when
        liveFootballService.finishGame(game);

        // then
        assertThat(liveFootballService.getGamesLiveScoreboard().contains(game)).isFalse();
        assertThat(liveFootballService.getGamesSummary().contains(game)).isTrue();
    }

}