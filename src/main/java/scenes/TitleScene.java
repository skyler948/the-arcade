package scenes;

import components.Button;
import game.Game;
import objects.GameObject;

import static com.raylib.Raylib.*;
import static com.raylib.Colors.*;

public class TitleScene extends Scene {

    private static final int BUTTON_WIDTH = 250;
    private static final int BUTTON_HEIGHT = 50;

    private GameObject games;
    private Button gamesButton;

    private GameObject scores;
    private Button scoresButton;

    private GameObject settings;
    private Button settingsButton;

    private GameObject about;
    private Button aboutButton;

    private GameObject exit;
    private Button exitButton;

    public TitleScene(Game game) {
        super(game);
    }

    @Override
    public void init() {
        games = new GameObject();
        games.getTransform().setLocalPosition(getCenterX(BUTTON_WIDTH), 200);

        gamesButton = new Button(game, BUTTON_WIDTH, BUTTON_HEIGHT, "Games");
        games.addComponent(gamesButton);

        objects.add(games);

        scores = new GameObject();
        scores.getTransform().setLocalPosition(getCenterX(BUTTON_WIDTH), 300);

        scoresButton = new Button(game, BUTTON_WIDTH, BUTTON_HEIGHT, "Scores");
        scores.addComponent(scoresButton);

        objects.add(scores);

        settings = new GameObject();
        settings.getTransform().setLocalPosition(getCenterX(BUTTON_WIDTH), 400);

        settingsButton = new Button(game, BUTTON_WIDTH, BUTTON_HEIGHT, "Settings");
        settings.addComponent(settingsButton);

        objects.add(settings);

        about = new GameObject();
        about.getTransform().setLocalPosition(getCenterX(BUTTON_WIDTH), 500);

        aboutButton = new Button(game, BUTTON_WIDTH, BUTTON_HEIGHT, "About");
        about.addComponent(aboutButton);

        objects.add(about);

        exit = new GameObject();
        exit.getTransform().setLocalPosition(getCenterX(BUTTON_WIDTH), 600);

        exitButton = new Button(game, BUTTON_WIDTH, BUTTON_HEIGHT, "Exit");
        exit.addComponent(exitButton);

        objects.add(exit);
    }

    @Override
    public void tick() {
        tickObjects();

        if (gamesButton.isPressed()) {
            System.out.println("This will open a game selector screen.");
        }
        if (scoresButton.isPressed()) {
            System.out.println("This will display the scores.");
        }
        if (settingsButton.isPressed()) {
            System.out.println("This will open settings.");
        }
        if (aboutButton.isPressed()) {
            System.out.println("This will open an about page.");
        }
        if (exitButton.isPressed()) {
            game.getDisplay().forceClose();
        }
    }

    @Override
    public void render() {
        renderObjects();
    }

    @Override
    public void ui() {
        DrawText("The Arcade", (int) getCenterX(MeasureText("The Arcade", 75)), 60, 75, WHITE);
    }

    @Override
    public void close() {

    }

}
