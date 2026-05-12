package scenes;

import components.Button;
import components.RectangleRenderer;
import game.Game;
import objects.GameObject;
import score.ScoreManager;

import java.util.ArrayList;

import static com.raylib.Colors.*;
import static com.raylib.Raylib.*;

public class ScoreScene extends Scene {

    private static final float SCROLL_SPEED = 600.f;
    private static final float SCROLL_MULTIPLIER = 2.f;
    private static final byte SCORE_GAP = 80, TITLE_GAP = 70;
    private static final byte INDIVIDUAL_SCORE_GAP = 32;
    private static final short SCORE_OFFSET = 130;
    private static final byte TITLE_FONT_SIZE = 50;
    private static final byte SCORE_FONT_SIZE = 30;

    private GameObject back;
    private Button backButton;

    private GameObject background;

    private ArrayList<String[]> scores;

    private float yOffs;
    private float yMin;

    public ScoreScene(Game game) {
        super(game);
    }

    @Override
    public void init() {
        back = new GameObject();
        back.getTransform().setLocalPosition(10, 10);

        backButton = new Button(game, 80, 40, "Back");
        back.addComponent(backButton);

        objects.add(back);

        scores = new ArrayList<>();
        for (int i = 0; i < ScoreManager.GAME_LIST.length; i++) {
            scores.add(game.getScoreManager().getGameScore(i).getScoresFormatted());
        }

        background = new GameObject();
        background.getTransform().setLocalPosition(getCenterX(game.getDisplay().getInternalWidth()), 0);
        background.addComponent(new RectangleRenderer(BLACK, game.getDisplay().getInternalWidth(), 60));
    }

    @Override
    public void tick() {
        tickObjects();

        if (backButton.isPressed()) {
            game.getSceneManager().setScene(new TitleScene(game));
        }

        float speed = SCROLL_SPEED;
        if (IsKeyDown(KEY_LEFT_SHIFT) || IsKeyDown(KEY_RIGHT_SHIFT)) {
            speed *= SCROLL_MULTIPLIER;
        }

        if (IsKeyDown(KEY_DOWN) || IsKeyDown(KEY_S)) {
            yOffs -= speed * GetFrameTime();
        }
        if (IsKeyDown(KEY_UP) || IsKeyDown(KEY_W)) {
            yOffs += speed * GetFrameTime();
        }

        yOffs = Math.clamp(yOffs, -(yMin - (game.getDisplay().getInternalHeight() / 2.f)), 0.f);
    }

    @Override
    public void render() {
        renderObjects();
    }

    @Override
    public void ui() {
        int titleHeight = 0;
        int scoreHeight = 0;
        yMin = 0.f;

        for (int i = 0; i < scores.size(); i++) {
            DrawText(ScoreManager.GAME_LIST[i], (int) getCenterX(MeasureText(ScoreManager.GAME_LIST[i], TITLE_FONT_SIZE)),
                    (TITLE_GAP + titleHeight) + (int) yOffs, TITLE_FONT_SIZE, WHITE);

            for (int j = 0; j < scores.get(i).length; j++) {
                DrawText(scores.get(i)[j], (int) getCenterX(MeasureText(scores.get(i)[j], SCORE_FONT_SIZE)),
                        (SCORE_OFFSET + (j * INDIVIDUAL_SCORE_GAP) + scoreHeight) + (int) yOffs, SCORE_FONT_SIZE, WHITE);

                titleHeight = SCORE_GAP + (j * INDIVIDUAL_SCORE_GAP);
            }

            if (scores.get(i).length == 0) {
                titleHeight = SCORE_GAP;
            }

            scoreHeight = titleHeight;
            yMin += scoreHeight;
        }

        background.render();

        DrawText("Scores", (int) getCenterX(MeasureText("Scores", 40)), 12, 40, WHITE);
    }

    @Override
    public void close() {

    }

}
