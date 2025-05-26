package livefootball.service;

import livefootball.domain.AwayTeam;
import livefootball.domain.Game;
import livefootball.domain.HomeTeam;
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
        final HomeTeam homeTeam = new HomeTeam("Mexico");
        final AwayTeam awayTeam = new AwayTeam("Canada");

        // when
        final Game game = liveFootballService.startGame(homeTeam, awayTeam);

        // then
        assertAll(
                () -> assertThat(game.homeTeam()).isEqualTo(homeTeam),
                () -> assertThat(game.awayTeam()).isEqualTo(awayTeam),
                () -> assertThat(game.homeScore()).isZero(),
                () -> assertThat(game.awayScore()).isZero(),
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
        final HomeTeam homeTeam = new HomeTeam("Mexico");
        final AwayTeam awayTeam = new AwayTeam("Canada");
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
        final HomeTeam firstGameHomeTeam = new HomeTeam("Mexico");
        final AwayTeam firstGameAwayTeam = new AwayTeam("Canada");
        final HomeTeam secondGameHomeTeam = new HomeTeam("Spain");
        final AwayTeam secondGameAwayTeam = new AwayTeam("Brazil");

        // when
        final Game firstGame = liveFootballService.startGame(firstGameHomeTeam, firstGameAwayTeam);
        final Game secondGame = liveFootballService.startGame(secondGameHomeTeam, secondGameAwayTeam);

        // then
        assertAll(
                () -> assertThat(liveFootballService.getGamesLiveScoreboard())
                        .hasSize(2)
                        .containsExactlyInAnyOrder(firstGame, secondGame),

                () -> assertThat(firstGame)
                        .extracting(Game::homeTeam, Game::awayTeam)
                        .containsExactly(firstGameHomeTeam, firstGameAwayTeam),

                () -> assertThat(secondGame)
                        .extracting(Game::homeTeam, Game::awayTeam)
                        .containsExactly(secondGameHomeTeam, secondGameAwayTeam)
        );
    }

    @Test
    public void should_return_game_with_updated_scores_when_user_updates_live_game_score() {
        // given
        final LiveFootballService liveFootballService = new LiveFootballService();
        final HomeTeam homeTeam = new HomeTeam("Mexico");
        final AwayTeam awayTeam = new AwayTeam("Canada");
        final Game game = liveFootballService.startGame(homeTeam, awayTeam);

        // when
        final Game updatedGame = liveFootballService.updateGameScore(game, 3, 1);

        // then
        assertAll(
                () -> assertThat(updatedGame.homeScore()).isEqualTo(3),
                () -> assertThat(updatedGame.awayScore()).isEqualTo(1),
                () -> assertThat(liveFootballService.getGamesLiveScoreboard())
                        .containsExactly(updatedGame),
                () -> assertThat(liveFootballService.getGamesLiveScoreboard())
                        .doesNotContain(game)
        );

    }


    @Test
    public void should_return_live_games_info_with_updated_scores_when_user_updates_two_games() {
        // given
        final LiveFootballService liveFootballService = new LiveFootballService();
        final HomeTeam firstGameHomeTeam = new HomeTeam("Mexico");
        final AwayTeam firstGameAwayTeam = new AwayTeam("Canada");
        final HomeTeam secondGameHomeTeam = new HomeTeam("Spain");
        final AwayTeam secondGameAwayTeam = new AwayTeam("Brazil");
        final Game firstGame = liveFootballService.startGame(firstGameHomeTeam, firstGameAwayTeam);
        final Game secondGame = liveFootballService.startGame(secondGameHomeTeam, secondGameAwayTeam);

        // when
        liveFootballService.updateGameScore(firstGame, 3, 1);
        liveFootballService.updateGameScore(secondGame, 1, 1);

        // then
        assertThat(liveFootballService.getLiveScoreboardInfoAsString()).isEqualTo("[Mexico-Canada: 3-1, Spain-Brazil: 1-1]");
    }

    @Test
    public void should_remove_game_from_live_scoreboard_and_add_to_summary_when_user_finishes_game() {
        // given
        final LiveFootballService liveFootballService = new LiveFootballService();
        final HomeTeam homeTeam = new HomeTeam("Mexico");
        final AwayTeam awayTeam = new AwayTeam("Canada");
        final Game game = liveFootballService.startGame(homeTeam, awayTeam);

        // when
        liveFootballService.finishGame(game);

        // then
        assertThat(liveFootballService.getGamesLiveScoreboard().contains(game)).isFalse();
        assertThat(liveFootballService.getGamesSummary().contains(game)).isTrue();
    }

}