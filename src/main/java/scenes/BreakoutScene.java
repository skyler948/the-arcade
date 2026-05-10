package scenes;

import components.Hitbox;
import components.RectangleRenderer;
import game.Game;
import objects.GameObject;
import time.Timer;

import java.util.ArrayList;
import java.util.Random;

import static com.raylib.Colors.WHITE;
import static com.raylib.Raylib.*;

public class BreakoutScene extends Scene {

    private static final float SPEED_LOWER_BOUND = 250.f;
    private static final float SPEED_UPPER_BOUND = 350.f;
    private static final float SPEED_MINIMUM = 100.f;
    private static final float PADDLE_SPEED = 500.f;
    private static final byte BALL_SIZE = 20;
    private static final byte BRICK_WIDTH = 40, BRICK_HEIGHT = 20;
    private static final byte PADDLE_WIDTH = 90, PADDLE_HEIGHT = 20;
    private static final short SCORE_PER_BRICK = 100;
    private static byte BRICK_COUNT_X = 14, BRICK_COUNT_Y = 8;

    private Timer startTimer;
    private Timer respawnTimer;
    private Random random;

    private boolean playing = false;

    private GameObject paddle;
    private Hitbox paddleHitbox;

    private ArrayList<GameObject> bricks;
    private ArrayList<Hitbox> brickHitboxes;

    private GameObject brickToRemove;
    private Hitbox hitboxToRemove;

    private GameObject ball;
    private Hitbox ballHitbox;

    private float ballSpeedX, ballSpeedY;

    private long score;
    private int completions;
    private byte lives = 3;

    public BreakoutScene(Game game) {
        super(game);
    }

    @Override
    public void init() {
        startTimer = new Timer(2.f);
        respawnTimer = new Timer(2.f);
        random = new Random();

        paddle = new GameObject();
        paddle.getTransform().setLocalPosition(getCenterX(PADDLE_WIDTH), game.getDisplay().getInternalHeight() - 150);
        paddle.addComponent(new RectangleRenderer(PADDLE_WIDTH, PADDLE_HEIGHT));

        paddleHitbox = new Hitbox(PADDLE_WIDTH, 2.f);
        paddle.addComponent(paddleHitbox);

        bricks = new ArrayList<>();
        brickHitboxes = new ArrayList<>();

        objects.add(paddle);

        createBricks();

        ball = new GameObject();
        ball.getTransform().setLocalPosition(random.nextFloat(420, game.getDisplay().getInternalWidth() - 440), getCenterY(BALL_SIZE));
        ball.addComponent(new RectangleRenderer(BALL_SIZE, BALL_SIZE));

        ballHitbox = new Hitbox(BALL_SIZE, BALL_SIZE);
        ball.addComponent(ballHitbox);

        objects.add(ball);

        startTimer.start();
    }

    @Override
    public void tick() {
        tickObjects();

        if (startTimer.isDone()) {
            startGame();
        }

        if (paddle.getTransform().getLocalPosition().x() > -PADDLE_WIDTH
                && (IsKeyDown(KEY_A) || IsKeyDown(KEY_LEFT))) {
            paddle.getTransform().changeLocalPosition(-PADDLE_SPEED * GetFrameTime(), 0.f);
        }
        if (paddle.getTransform().getLocalPosition().x() < game.getDisplay().getInternalWidth()
                && (IsKeyDown(KEY_D) || IsKeyDown(KEY_RIGHT))) {
            paddle.getTransform().changeLocalPosition(PADDLE_SPEED * GetFrameTime(), 0.f);
        }

        if (playing) {
            if (respawnTimer.isDone()) {
                resetBall();
            }

            if (respawnTimer.isPlaying()) return;

            if (CheckCollisionRecs(paddleHitbox.getHitbox(), ballHitbox.getHitbox())) {
                float localX = (ball.getTransform().getLocalPosition().x() - paddle.getTransform().getLocalPosition().x()) - (PADDLE_WIDTH / 2.f);
                ballSpeedY = -Math.abs(ballSpeedY);
                ballSpeedX = random.nextFloat(SPEED_LOWER_BOUND, SPEED_UPPER_BOUND) * (float) Math.sin(Math.toRadians(localX));
            }

            if (ballSpeedX < SPEED_MINIMUM && ballSpeedX >= 0) {
                ballSpeedX = SPEED_MINIMUM;
            }
            if (ballSpeedX > -SPEED_MINIMUM && ballSpeedX < 0) {
                ballSpeedX = -SPEED_MINIMUM;
            }

            if (ballSpeedY < SPEED_MINIMUM && ballSpeedY >= 0) {
                ballSpeedY = SPEED_MINIMUM;
            }
            if (ballSpeedY > -SPEED_MINIMUM && ballSpeedY < 0) {
                ballSpeedY = -SPEED_MINIMUM;
            }

            if (ball.getTransform().getLocalPosition().x() < 0) {
                ballSpeedX = Math.abs(ballSpeedX);
            }
            if (ball.getTransform().getLocalPosition().x() + BALL_SIZE > game.getDisplay().getInternalWidth()) {
                ballSpeedX = -Math.abs(ballSpeedX);
            }

            if (ball.getTransform().getLocalPosition().y() > game.getDisplay().getInternalHeight()) {
                lives--;
                respawnTimer.start();
                ball.getTransform().setLocalPosition(random.nextFloat(420, game.getDisplay().getInternalWidth() - 440), getCenterY(BALL_SIZE));
                resetBall();
            }
            if (ball.getTransform().getLocalPosition().y() < 0) {
                ballSpeedY = Math.abs(ballSpeedY);
            }

            for (int i = 0; i < bricks.size(); i++) {
                if (CheckCollisionRecs(ballHitbox.getHitbox(), brickHitboxes.get(i).getHitbox())) {
                    brickToRemove = bricks.get(i);
                    hitboxToRemove = brickHitboxes.get(i);
                    score += SCORE_PER_BRICK;
                    ballSpeedX += random.nextFloat(-20.f, 20.f);
                    ballSpeedY = -ballSpeedY;
                }
            }

            if (brickToRemove != null) {
                objects.remove(brickToRemove);
                bricks.remove(brickToRemove);
                brickToRemove = null;
                brickHitboxes.remove(hitboxToRemove);
                hitboxToRemove = null;
            }

            ball.getTransform().changeLocalPosition(ballSpeedX * GetFrameTime(), ballSpeedY * GetFrameTime());

            if (lives == 0 || (playing && hasWon())) {
                playing = false;
                completions++;
            }
        } else {
            if (hasWon() && IsKeyPressed(KEY_SPACE)) {
                playing = true;
                createBricks();
                ball.getTransform().setLocalPosition(random.nextFloat(420, game.getDisplay().getInternalWidth() - 440), getCenterY(BALL_SIZE));
                resetBall();
            }
        }
    }

    @Override
    public void render() {
        renderObjects();
    }

    @Override
    public void ui() {
        DrawText("Score: " + score, 100, 10, 20, WHITE);
        DrawText(lives + " Lives", game.getDisplay().getInternalWidth() - 170, 10, 20, WHITE);

        if (hasWon()) {
            DrawText("You Win! Press SPACE to play again.", 100, 700, 40, WHITE);
        }
        if (lives == 0) {
            DrawText("Game Over :(", 100, 700, 40, WHITE);
        }
    }

    @Override
    public void close() {

    }


    private void startGame() {
        resetBall();

        score = 0;
        completions = 0;
        lives = 3;

        playing = true;
    }

    private void resetBall() {
        ball.setActive(true);

        ballSpeedX = 0.f;
        ballSpeedY = random.nextFloat(SPEED_LOWER_BOUND, SPEED_UPPER_BOUND);
    }

    private void createBricks() {
        objects.removeAll(bricks);
        bricks.clear();
        brickHitboxes.clear();

        int i = 0;
        for (int x = 0; x < BRICK_COUNT_X; x++) {
            for (int y = 0; y < BRICK_COUNT_Y; y++) {
                bricks.add(new GameObject());
                bricks.get(i).getTransform().setLocalPosition((x * 60) + 100, (y * 40) + 50);
                bricks.get(i).addComponent(new RectangleRenderer(BRICK_WIDTH, BRICK_HEIGHT));

                brickHitboxes.add(new Hitbox(BRICK_WIDTH, BRICK_HEIGHT));
                bricks.get(i).addComponent(brickHitboxes.get(i));

                objects.add(bricks.get(i));

                i++;
            }
        }
    }

    private boolean hasWon() {
        return bricks.isEmpty();
    }

}
