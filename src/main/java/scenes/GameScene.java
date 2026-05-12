package scenes;

import components.Button;
import components.RectangleRenderer;
import components.SpriteRenderer;
import game.Game;
import objects.GameObject;
import score.ScoreManager;

import static com.raylib.Raylib.*;
import static com.raylib.Colors.*;

public class GameScene extends Scene {

    private static final short PANEL_WIDTH = 400, PANEL_HEIGHT = 600;
    private static final byte SQUARE_BUTTON_SIZE = 40;
    private static final short SQUARE_BUTTON_HORIZONTAL_OFFSET = 230;
    private static final byte SQUARE_BUTTON_VERTICAL_OFFSET = 15;
    private static final short PLAY_BUTTON_WIDTH = 200, PLAY_BUTTON_HEIGHT = 40;
    private static final short THUMBNAIL_SIZE = 320;
    private static final byte THUMBNAILS_PER_ROW = 4;

    private GameObject back;
    private Button backButton;

    private GameObject pageForward, pageBack;
    private Button pageForwardButton, pageBackButton;

    private GameObject[] play;
    private Button[] playButtons;

    private GameObject[] thumbnails;

    private byte page;

    public GameScene(Game game) {
        super(game);
    }

    @Override
    public void init() {
        back = new GameObject();
        back.getTransform().setLocalPosition(10, 10);

        backButton = new Button(game, 80, 40, "Back");
        back.addComponent(backButton);

        objects.add(back);

        GameObject panel = new GameObject();
        panel.getTransform().setLocalPosition(getCenterX(PANEL_WIDTH), getCenterY(PANEL_HEIGHT) + 15);
        panel.addComponent(new RectangleRenderer(PANEL_WIDTH, PANEL_HEIGHT, true));

        objects.add(panel);

        pageForward = new GameObject();
        pageForward.getTransform().setLocalPosition(game.getDisplay().getCurrentWidth() - SQUARE_BUTTON_SIZE - SQUARE_BUTTON_HORIZONTAL_OFFSET,
                getCenterY(SQUARE_BUTTON_SIZE) + SQUARE_BUTTON_VERTICAL_OFFSET);

        pageForwardButton = new Button(game, SQUARE_BUTTON_SIZE, SQUARE_BUTTON_SIZE, ">");
        pageForward.addComponent(pageForwardButton);

        objects.add(pageForward);

        pageBack = new GameObject();
        pageBack.getTransform().setLocalPosition(SQUARE_BUTTON_HORIZONTAL_OFFSET, getCenterY(SQUARE_BUTTON_SIZE) + SQUARE_BUTTON_VERTICAL_OFFSET);

        pageBackButton = new Button(game, SQUARE_BUTTON_SIZE, SQUARE_BUTTON_SIZE, "<");
        pageBack.addComponent(pageBackButton);

        objects.add(pageBack);

        play = new GameObject[ScoreManager.GAME_LIST.length];
        playButtons = new Button[play.length];

        for (int i = 0; i < play.length; i++) {
            play[i] = new GameObject();
            play[i].getTransform().setLocalPosition(getCenterX(PLAY_BUTTON_WIDTH), game.getDisplay().getInternalHeight() - 150);

            playButtons[i] = new Button(game, PLAY_BUTTON_WIDTH, PLAY_BUTTON_HEIGHT, "Play " + ScoreManager.GAME_LIST[i], 20);
            play[i].addComponent(playButtons[i]);

            objects.add(play[i]);

            play[i].setActive(false);
        }

        thumbnails = new GameObject[THUMBNAILS_PER_ROW * THUMBNAILS_PER_ROW];

        int i = 0;
        for (int x = 0; x < THUMBNAILS_PER_ROW; x++) {
            for (int y = 0; y < THUMBNAILS_PER_ROW; y++) {
                thumbnails[i] = new GameObject();
                thumbnails[i].getTransform().setLocalPosition(getCenterX(THUMBNAIL_SIZE), 140);
                thumbnails[i].addComponent(new SpriteRenderer(game, "gameThumbnails",
                        y * THUMBNAIL_SIZE, x * THUMBNAIL_SIZE, THUMBNAIL_SIZE, THUMBNAIL_SIZE));
                thumbnails[i].addComponent(new RectangleRenderer(THUMBNAIL_SIZE, THUMBNAIL_SIZE, true));

                objects.add(thumbnails[i]);

                thumbnails[i].setActive(false);
                i++;
            }
        }

        play[page].setActive(true);
        thumbnails[page].setActive(true);

        page = 0;
    }

    @Override
    public void tick() {
        tickObjects();

        if (backButton.isPressed()) {
            game.getSceneManager().setScene(new TitleScene(game));
        }

        if (pageForwardButton.isPressed() || IsKeyPressed(KEY_RIGHT) || IsKeyPressed(KEY_D)) {
            play[page].setActive(false);
            thumbnails[page].setActive(false);
            page = (byte) Math.clamp(++page, 0, play.length - 1);
            play[page].setActive(true);
            thumbnails[page].setActive(true);
        } else if (pageBackButton.isPressed() || IsKeyPressed(KEY_LEFT) || IsKeyPressed(KEY_A)) {
            play[page].setActive(false);
            thumbnails[page].setActive(false);
            page = (byte) Math.clamp(--page, 0, play.length - 1);
            play[page].setActive(true);
            thumbnails[page].setActive(true);
        }

        if (playButtons[0].isPressed()) {
            game.getSceneManager().setScene(new BreakoutScene(game));
        } else if (playButtons[1].isPressed()) {
            game.getSceneManager().setScene(new PongScene(game));
        }
    }

    @Override
    public void render() {
        renderObjects();
    }

    @Override
    public void ui() {
        DrawText("Choose a game", (int) getCenterX(MeasureText("Choose a game", 30)), 30, 30, WHITE);
        DrawText(String.format("%d/%d", (page + 1), (ScoreManager.GAME_LIST.length)),
                (int) getCenterX(MeasureText(String.format("%d/%d", (page + 1), (ScoreManager.GAME_LIST.length)), 20)), 67, 20, WHITE);
    }

    @Override
    public void close() {

    }

}
