package livefootball.domain;

public record Score(
        int value
) {

    public Score addScore(Score awayScore) {
        return new Score(this.value + awayScore.value);
    }

}
