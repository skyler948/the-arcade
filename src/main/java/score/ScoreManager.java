package score;

import game.Game;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.PatternSyntaxException;

public class ScoreManager {

    private static final String[] GAME_LIST = {"asteroids", "breakout", "pong"};

    private static final String ROOT = ".application/";
    private static final String SCORES_PATH = ROOT + "scores/";

    private File scoreDirectory;

    private File[] scoreFiles;

    private Game game;

    private ArrayList<GameScore> gameScores;

    public ScoreManager(Game game) {
        this.game = game;

        checkDirectory();
        checkFiles();

        gameScores = new ArrayList<>();

        for (String gameName : GAME_LIST) {
            gameScores.add(new GameScore(gameName));
        }

        readScores();
    }

    private void checkDirectory() {
        scoreDirectory = new File(SCORES_PATH);

        try {
            if (scoreDirectory.mkdirs()) {
                System.out.println("Score directory created.");
            }
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
    }

    private void checkFiles() {
        scoreFiles = new File[GAME_LIST.length];

        for (int i = 0; i < scoreFiles.length; i++) {
            scoreFiles[i] = new File(SCORES_PATH + GAME_LIST[i] + ".txt");

            try {
                if (scoreFiles[i].createNewFile()) {
                    System.out.println("Score file for " + GAME_LIST[i] + " created.");
                } else {
                    System.out.println("Score file for " + GAME_LIST[i] + " found.");
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void readScores() {
        for (int i = 0; i < scoreFiles.length; i++) {
            try {
                Scanner scanner = new Scanner(scoreFiles[i]);

                while (scanner.hasNextLine()) {
                    String[] data = scanner.nextLine().split("=");

                    gameScores.get(i).addScore(data[0].trim(), Long.parseLong(data[1].trim()));
                }

                scanner.close();
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
        }

        System.out.println("Scores read successfully.");
    }

    private int getIndexByName(String gameName) {
        for (GameScore gameScore : gameScores) {
            if (gameName.equals(gameScore.getGameName())) {
                return gameScores.indexOf(gameScore);
            }
        }

        return -1;
    }

    private GameScore getGameByName(String gameName) {
        for (GameScore gameScore : gameScores) {
            if (gameName.equals(gameScore.getGameName())) {
                return gameScore;
            }
        }

        return null;
    }

    public void addScore(Score score) {
        int index = getIndexByName(score.getGameName());
        if (index == -1) return;

        gameScores.get(index).addScore(score.getScoreHolder(), score.getScore());

        try {
            FileWriter writer = new FileWriter(scoreFiles[index], true);

            writer.write(score.getScoreHolder());
            writer.write("=");
            writer.write(String.valueOf(score.getScore()));
            writer.write("\n");

            writer.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        System.out.println("Score for " + score.getScoreHolder() + " - " + score.getScore() + " successfully appended.");
    }

    public GameScore getGameScore(String name) {
        return getGameByName(name);
    }

    public GameScore getGameScore(int index) {
        return gameScores.get(index);
    }

}
