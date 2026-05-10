package score;

public class Score {

    private String scoreHolder;
    private long score;
    private String gameName;

    public Score(String scoreHolder, long score, String gameName) {
        this.scoreHolder = scoreHolder;
        this.score = score;
        this.gameName = gameName;
    }

    public String getScoreHolder() {
        return scoreHolder;
    }

    public long getScore() {
        return score;
    }

    public String getGameName() {
        return gameName;
    }

}
