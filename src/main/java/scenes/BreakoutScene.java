package scenes;

import components.Hitbox;
import components.RectangleRenderer;
import game.Game;
import objects.GameObject;

import java.util.ArrayList;

import static com.raylib.Raylib.*;

public class BreakoutScene extends Scene {

    private GameObject paddle;
    private Hitbox paddleHitbox;

    private ArrayList<GameObject> bricks;
    private ArrayList<Hitbox> brickHitboxes;

    public BreakoutScene(Game game) {
        super(game);
    }

    @Override
    public void init() {
        paddle = new GameObject();
        paddle.getTransform().setLocalPosition(getCenterX(100), game.getDisplay().getInternalHeight() - 150);
        paddle.addComponent(new RectangleRenderer(100, 20));

        paddleHitbox = new Hitbox(100, 20);
        paddle.addComponent(paddleHitbox);

        bricks = new ArrayList<>();
        brickHitboxes = new ArrayList<>();

        int i = 0;
        for (int x = 0; x < 14; x++) {
            for (int y = 0; y < 8; y++) {
                bricks.add(new GameObject());
                bricks.get(i).getTransform().setLocalPosition((x * 60) + 100, (y * 40) + 50);
                bricks.get(i).addComponent(new RectangleRenderer(40, 20));

                brickHitboxes.add(new Hitbox(40, 20));
                bricks.get(i).addComponent(brickHitboxes.get(i));

                objects.add(bricks.get(i));

                i++;
            }
        }

        objects.add(paddle);
    }

    @Override
    public void tick() {
        tickObjects();

        if (IsKeyDown(KEY_A) || IsKeyDown(KEY_LEFT)) {
            paddle.getTransform().changeLocalPosition(-500.f * GetFrameTime(), 0.f);
        }
        if (IsKeyDown(KEY_D) || IsKeyDown(KEY_RIGHT)) {
            paddle.getTransform().changeLocalPosition(500.f * GetFrameTime(), 0.f);
        }
    }

    @Override
    public void render() {
        renderObjects();
    }

    @Override
    public void ui() {

    }

    @Override
    public void close() {

    }

}
