package scenes;

import components.Hitbox;
import components.RectangleRenderer;
import game.Game;
import objects.GameObject;
import score.Score;
import time.Timer;

import java.util.Random;

import static com.raylib.Raylib.*;
import static com.raylib.Colors.*;

public class PongScene extends Scene {

    private static final byte PADDLE_WIDTH = 20, PADDLE_HEIGHT = 100;
    private static final byte PADDLE_STARTING_X = 70;
    private static final float PADDLE_MOVE_SPEED = 500.f;
    private static final byte BALL_SIZE = 20;
    private static final float BALL_SPEED = 450.f;
    private static final byte LINE_WIDTH = 10;
    private static final byte DEFAULT_WIN_CONDITION = 10;
    private static final float DEFAULT_REACTION_TIME = 0.25f;
    private static final float MINIMUM_REACTION_TIME = 0.0001f, MAXIMUM_REACTION_TIME = 1.f;
    private static final byte OPPONENT_MESS_UP_BOUNDS = 20;

    private Random random;

    private boolean playing = true;

    private byte opponentWinCondition;
    private float opponentReactionTime;

    private Timer respawnTimer;
    private Timer sampleTimer;

    private GameObject player;
    private Hitbox playerHitbox;

    private long playerScore;

    private GameObject opponent;
    private Hitbox opponentHitbox;

    private long opponentScore;

    private GameObject ball;
    private Hitbox ballHitbox;

    private GameObject line;

    private float ballSpeedX, ballSpeedY;
    private float ballPreviousY;

    private boolean opponentMoving = false;

    public PongScene(Game game) {
        super(game);

        random = new Random();
        respawnTimer = new Timer(2.f);
    }

    @Override
    public void init() {
        setOpponentWinCondition(DEFAULT_WIN_CONDITION);
        setOpponentReactionTime(DEFAULT_REACTION_TIME);

        sampleTimer = new Timer(opponentReactionTime);

        player = new GameObject();
        player.addComponent(new RectangleRenderer(PADDLE_WIDTH, PADDLE_HEIGHT));

        playerHitbox = new Hitbox(PADDLE_WIDTH, PADDLE_HEIGHT);
        player.addComponent(playerHitbox);

        objects.add(player);

        opponent = new GameObject();
        opponent.addComponent(new RectangleRenderer(PADDLE_WIDTH, PADDLE_HEIGHT));

        opponentHitbox = new Hitbox(2, PADDLE_HEIGHT);
        opponent.addComponent(opponentHitbox);

        objects.add(opponent);

        ball = new GameObject();
        ball.addComponent(new RectangleRenderer(BALL_SIZE, BALL_SIZE));

        ballHitbox = new Hitbox(BALL_SIZE, BALL_SIZE);
        ball.addComponent(ballHitbox);

        objects.add(ball);

        line = new GameObject();
        line.getTransform().setLocalPosition(getCenterX(LINE_WIDTH), 0.f);
        line.addComponent(new RectangleRenderer(LINE_WIDTH, game.getDisplay().getInternalHeight()));

        objects.add(line);

        resetGame();

        respawnTimer.start();
    }

    @Override
    public void tick() {
        tickObjects();

        if (opponentScore >= opponentWinCondition && playing) {
            playing = false;
            game.getScoreManager().addScore(new Score("TMP", playerScore, "pong"));
        }
        if (!playing && IsKeyDown(KEY_SPACE)) {
            playing = true;
            resetGame();
        }

        if (respawnTimer.isDone()) {
            setBallSpeed();
        }

        if (respawnTimer.isPlaying()) return;

        if (!playing) return;

        if ((IsKeyDown(KEY_S) || IsKeyDown(KEY_DOWN))
                && player.getTransform().getLocalPosition().y() < game.getDisplay().getInternalHeight() - PADDLE_HEIGHT) {
            player.getTransform().changeLocalPosition(0.f, PADDLE_MOVE_SPEED * GetFrameTime());
        }
        if ((IsKeyDown(KEY_W) || IsKeyDown(KEY_UP))
                && player.getTransform().getLocalPosition().y() > 0) {
            player.getTransform().changeLocalPosition(0.f, -PADDLE_MOVE_SPEED * GetFrameTime());
        }

        if (CheckCollisionRecs(playerHitbox.getHitbox(), ballHitbox.getHitbox())) {
            double localY = (ball.getTransform().getLocalPosition().y() - player.getTransform().getLocalPosition().y()) - (PADDLE_HEIGHT / 2.f);
            double angle = Math.toRadians(localY);

            ballSpeedX = (float) Math.cos(angle) * BALL_SPEED;
            ballSpeedY = (float) Math.sin(angle) * BALL_SPEED;
        }
        if (CheckCollisionRecs(opponentHitbox.getHitbox(), ballHitbox.getHitbox())) {
            double localY = (ball.getTransform().getLocalPosition().y() - opponent.getTransform().getLocalPosition().y()) - (PADDLE_HEIGHT / 2.f);
            double angle = Math.toRadians(localY);

            ballSpeedX = -(float) Math.cos(angle) * BALL_SPEED;
            ballSpeedY = (float) Math.sin(angle) * BALL_SPEED;
        }

        if (ball.getTransform().getLocalPosition().y() + BALL_SIZE > game.getDisplay().getInternalHeight()) {
            ballSpeedY = -Math.abs(ballSpeedY);
        }
        if (ball.getTransform().getLocalPosition().y() < 0) {
            ballSpeedY = Math.abs(ballSpeedY);
        }

        if (ball.getTransform().getLocalPosition().x() < 0) {
            resetField();
            opponentScore++;
            respawnTimer.start();
        }
        if (ball.getTransform().getLocalPosition().x() + BALL_SIZE > game.getDisplay().getInternalWidth()) {
            resetField();
            playerScore++;
            respawnTimer.start();
        }

        ball.getTransform().changeLocalPosition(ballSpeedX * GetFrameTime(), ballSpeedY * GetFrameTime());
        moveOpponent();
    }

    @Override
    public void render() {
        renderObjects();
    }

    @Override
    public void ui() {
        DrawText(String.valueOf(playerScore), (int) line.getTransform().getLocalPosition().x()
                - MeasureText(String.valueOf(playerScore), 90) - 40, 40, 90, WHITE);
        DrawText(String.valueOf(opponentScore), (int) line.getTransform().getLocalPosition().x()
                + 50, 40, 90, WHITE);

        if (!playing) {
            DrawText("You lose! Press SPACE to play again.", 50, 500, 30, WHITE);
        }
    }

    @Override
    public void close() {

    }

    private void resetField() {
        player.getTransform().setLocalPosition(PADDLE_STARTING_X, getCenterY(PADDLE_HEIGHT));
        opponent.getTransform().setLocalPosition(game.getDisplay().getInternalWidth() - PADDLE_STARTING_X - PADDLE_WIDTH,
                getCenterY(PADDLE_HEIGHT));
        ball.getTransform().setLocalPosition(getCenterX(BALL_SIZE), getCenterY(BALL_SIZE));

        ballSpeedX = 0.f;
        ballSpeedY = 0.f;
    }

    private void setBallSpeed() {
        ballSpeedX = -BALL_SPEED;
    }

    private void resetGame() {
        resetField();

        playerScore = 0;
        opponentScore = 0;
    }

    private void moveOpponent() {
        if (sampleTimer.isDone()) {
            opponentMoving = true;
        }

        byte ballDir = (byte) (ballSpeedY == 0 ? 0 : ballSpeedY < 0 ? 1 : -1);

        if (ballDir == 0) return;

        if (ballDir == -1 && opponent.getTransform().getLocalPosition().y() < ballPreviousY) {
            opponent.getTransform().changeLocalPosition(0.f, PADDLE_MOVE_SPEED * GetFrameTime());
        } else if (ballDir == 1 && opponent.getTransform().getLocalPosition().y() >= ballPreviousY) {
            opponent.getTransform().changeLocalPosition(0.f, -PADDLE_MOVE_SPEED * GetFrameTime());
        } else {
            opponentMoving = false;
        }

        if (sampleTimer.isPlaying()) return;

        if (!opponentMoving) {
            sampleTimer.start();
            ballPreviousY = ball.getTransform().getLocalPosition().y() - (PADDLE_HEIGHT / 2.f)
                    + random.nextInt(-OPPONENT_MESS_UP_BOUNDS, OPPONENT_MESS_UP_BOUNDS);
        }
    }

    public void setOpponentWinCondition(byte amount) {
        this.opponentWinCondition = (byte) Math.clamp(amount, 0, Byte.MAX_VALUE);
    }

    public void setOpponentReactionTime(float time) {
        this.opponentReactionTime = Math.clamp(time, MINIMUM_REACTION_TIME, MAXIMUM_REACTION_TIME);
    }

}
