package score;

import java.util.ArrayList;

public class GameScore {

    private String gameName;
    private ArrayList<Score> scores;

    public GameScore(String gameName, ArrayList<Score> scores) {
        this.gameName = gameName;
        this.scores = scores;
    }

    public GameScore(String gameName) {
        this(gameName, new ArrayList<>());
    }

    public String getGameName() {
        return gameName;
    }

    public ArrayList<Score> getScores() {
        return scores;
    }

    protected void addScore(String scoreHolder, long score) {
        scores.add(new Score(scoreHolder, score, gameName));
    }

}
