package livefootball.domain;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertAll;

class LiveFootballFacadeTest {

    @Test
    public void should_return_0_live_games_when_user_opens_live_scoreboard_for_the_first_time() {
        // given
        final LiveFootballFacade liveFootballFacade = new LiveFootballFacade();

        // when
        final List<Game> gamesLiveScoreboard = liveFootballFacade.getGamesLiveScoreboard();

        // then
        assertThat(0).isEqualTo(gamesLiveScoreboard.size());
    }

    @Test
    public void should_return_0_summary_games_when_user_opens_live_scoreboard_for_the_first_time() {
        // given
        final LiveFootballFacade liveFootballFacade = new LiveFootballFacade();

        // when
        final List<Game> gamesSummary = liveFootballFacade.getGamesSummary();

        // then
        assertThat(0).isEqualTo(gamesSummary.size());
    }

    @Test
    public void should_return_live_scoreboard_with_game_and_zero_scores_when_user_starts_game() {
        // given
        final LiveFootballFacade liveFootballFacade = new LiveFootballFacade();
        final Team homeTeam = new Team("Mexico");
        final Team awayTeam = new Team("Canada");

        // when
        final Game game = liveFootballFacade.startGame(homeTeam, awayTeam);

        // then
        assertAll(
                () -> assertThat(game.homeTeam()).isEqualTo(homeTeam),
                () -> assertThat(game.awayTeam()).isEqualTo(awayTeam),
                () -> assertThat(game.homeScore().value()).isZero(),
                () -> assertThat(game.awayScore().value()).isZero(),
                () -> assertThat(game.getOverallScore().value()).isZero()
        );
        assertThat(liveFootballFacade.getGamesLiveScoreboard())
                .hasSize(1)
                .contains(game);
    }

    @Test
    public void should_throw_exception_when_starting_a_game_which_is_already_ongoing() {
        // given
        final LiveFootballFacade liveFootballFacade = new LiveFootballFacade();
        final Team homeTeam = new Team("Mexico");
        final Team awayTeam = new Team("Canada");
        liveFootballFacade.startGame(homeTeam, awayTeam);

        // when

        final Throwable throwable = catchThrowable(() -> liveFootballFacade.startGame(homeTeam, awayTeam));

        // then
        assertThat(throwable).isInstanceOf(IllegalArgumentException.class);
        assertThat(throwable.getMessage()).isEqualTo("This game is already ongoing");
    }

    @Test
    public void should_return_live_scoreboard_with_two_games_when_user_starts_two_games() {
        // given
        final LiveFootballFacade liveFootballFacade = new LiveFootballFacade();
        final Team firstGameHomeTeam = new Team("Mexico");
        final Team firstGameAwayTeam = new Team("Canada");
        final Team secondGameHomeTeam = new Team("Spain");
        final Team secondGameAwayTeam = new Team("Brazil");

        // when
        final Game firstGame = liveFootballFacade.startGame(firstGameHomeTeam, firstGameAwayTeam);
        final Game secondGame = liveFootballFacade.startGame(secondGameHomeTeam, secondGameAwayTeam);

        // then
        assertThat(liveFootballFacade.getGamesLiveScoreboard())
                        .hasSize(2)
                        .containsExactlyInAnyOrder(firstGame, secondGame);

        assertThat(firstGame)
                .extracting(Game::homeTeam, Game::awayTeam)
                .containsExactly(firstGameHomeTeam, firstGameAwayTeam);

        assertThat(secondGame)
                .extracting(Game::homeTeam, Game::awayTeam)
                .containsExactly(secondGameHomeTeam, secondGameAwayTeam);
    }

    @Test
    public void should_return_game_with_updated_scores_when_user_updates_live_game_score() {
        // given
        final LiveFootballFacade liveFootballFacade = new LiveFootballFacade();
        final Team homeTeam = new Team("Mexico");
        final Team awayTeam = new Team("Canada");
        final Game game = liveFootballFacade.startGame(homeTeam, awayTeam);

        // when
        final Game updatedGame = liveFootballFacade.updateGameScore(game, new Score(3), new Score(1));

        // then
        {
            assertThat(updatedGame.homeScore().value()).isEqualTo(3);
            assertThat(updatedGame.awayScore().value()).isEqualTo(1);
        }

        {
            assertThat(liveFootballFacade.getGamesLiveScoreboard())
                    .containsExactly(updatedGame);
            assertThat(liveFootballFacade.getGamesLiveScoreboard())
                    .doesNotContain(game);
        }

    }


    @Test
    public void should_return_live_games_info_with_updated_scores_when_user_updates_two_games() {
        // given
        final LiveFootballFacade liveFootballFacade = new LiveFootballFacade();
        final Team firstGameHomeTeam = new Team("Mexico");
        final Team firstGameAwayTeam = new Team("Canada");
        final Team secondGameHomeTeam = new Team("Spain");
        final Team secondGameAwayTeam = new Team("Brazil");
        final Game firstGame = liveFootballFacade.startGame(firstGameHomeTeam, firstGameAwayTeam);
        final Game secondGame = liveFootballFacade.startGame(secondGameHomeTeam, secondGameAwayTeam);

        // when
        final Game gameMexicoCanada = liveFootballFacade.updateGameScore(firstGame, new Score(3), new Score(1));
        final Game gameSpainBrazil = liveFootballFacade.updateGameScore(secondGame, new Score(1), new Score(1));

        // then
        assertAll(
                () -> assertThat(gameMexicoCanada.homeTeam()).isEqualTo(new Team("Mexico")),
                () -> assertThat(gameMexicoCanada.awayTeam()).isEqualTo(new Team("Canada")),
                () -> assertThat(gameMexicoCanada.homeScore().value()).isEqualTo(3),
                () -> assertThat(gameMexicoCanada.awayScore().value()).isEqualTo(1),
                () -> assertThat(gameMexicoCanada.getOverallScore().value()).isEqualTo(4)
        );
        assertAll(
                () -> assertThat(gameSpainBrazil.homeTeam()).isEqualTo(new Team("Spain")),
                () -> assertThat(gameSpainBrazil.awayTeam()).isEqualTo(new Team("Brazil")),
                () -> assertThat(gameSpainBrazil.homeScore().value()).isEqualTo(1),
                () -> assertThat(gameSpainBrazil.awayScore().value()).isEqualTo(1),
                () -> assertThat(gameSpainBrazil.getOverallScore().value()).isEqualTo(2)
        );
    }

    @Test
    public void should_remove_game_from_live_scoreboard_and_add_to_summary_when_user_finishes_game() {
        // given
        final LiveFootballFacade liveFootballFacade = new LiveFootballFacade();
        final Team homeTeam = new Team("Mexico");
        final Team awayTeam = new Team("Canada");
        final Game game = liveFootballFacade.startGame(homeTeam, awayTeam);

        // when
        liveFootballFacade.finishGame(game);

        // then
        assertThat(liveFootballFacade.getGamesLiveScoreboard().contains(game)).isFalse();
        assertThat(liveFootballFacade.getGamesSummary().contains(game)).isTrue();
        final Game pastGame = liveFootballFacade.getGamesSummary().get(0);
        assertAll(
                () -> assertThat(pastGame.homeTeam()).isEqualTo(new Team("Mexico")),
                () -> assertThat(pastGame.awayTeam()).isEqualTo(new Team("Canada")),
                () -> assertThat(pastGame.homeScore().value()).isEqualTo(0),
                () -> assertThat(pastGame.awayScore().value()).isEqualTo(0),
                () -> assertThat(pastGame.getOverallScore().value()).isEqualTo(0)
        );
    }

}