package display;

import scenes.SceneManager;
import settings.DisplayMode;
import settings.Settings;

import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;

import static com.raylib.Raylib.*;
import static com.raylib.Colors.*;

public class Display {

    private final int INTERNAL_WIDTH = 1024;
    private final int INTERNAL_HEIGHT = 768;

    private int width, height;

    private GraphicsDevice gd;

    private Settings settings;

    private float virtualRatio;
    private float internalRatio, displayRatio;

    private Camera2D worldCamera, screenCamera;

    private RenderTexture target;

    private Rectangle sourceRec, destRec;

    private Vector2 origin;

    private boolean forceClose = false;

    public Display(Settings settings) {
        this.settings = settings;

        gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();

        worldCamera = new Camera2D()
                .zoom(1.0f);
        screenCamera = new Camera2D()
                .zoom(1.0f);
    }

    public void createDisplay() {
        setDisplayDimensions();

        InitWindow(width, height, "The Arcade");

        InitAudioDevice();

        settings.setVsync(settings.isVsync());
        settings.setMonitor(settings.getMonitor());

        target = LoadRenderTexture(INTERNAL_WIDTH, INTERNAL_HEIGHT);

        setRenderTextureDimensions();
    }

    public void setDisplayDimensions() {
        ClearWindowState(FLAG_FULLSCREEN_MODE | FLAG_WINDOW_TOPMOST | FLAG_WINDOW_UNDECORATED);

        if (settings.getDisplayMode().equals(DisplayMode.WINDOWED)) {
            width = settings.getWidth();
            height = settings.getHeight();
        } else {
            width = gd.getDisplayMode().getWidth();
            height = gd.getDisplayMode().getHeight();
        }

        virtualRatio = width / (float) INTERNAL_WIDTH;
        internalRatio = (float) INTERNAL_WIDTH / INTERNAL_HEIGHT;
        displayRatio = (float) width / height;

        switch (settings.getDisplayMode()) {
            case FULLSCREEN:
                SetConfigFlags(FLAG_FULLSCREEN_MODE);
                break;
            case BORDERLESS:
                SetConfigFlags(FLAG_WINDOW_TOPMOST | FLAG_WINDOW_UNDECORATED);
                break;
        }
    }

    public void setRenderTextureDimensions() {
        origin = new Vector2();

        sourceRec = new Rectangle()
                .x(0.0f).y(0.0f)
                .width((float) target.texture().width())
                .height(-(float) target.texture().height());

        if (!settings.isLetterboxing() || displayRatio == internalRatio) {
            destRec = new Rectangle()
                    .x(-virtualRatio).y(-virtualRatio)
                    .width(width + (virtualRatio * 2))
                    .height(height + (virtualRatio * 2));
        } else {
            if (displayRatio < internalRatio) {
                long letterboxHeight = ((long) width * INTERNAL_HEIGHT) / INTERNAL_WIDTH;

                destRec = new Rectangle()
                        .x(-virtualRatio).y(-virtualRatio)
                        .width(width + (virtualRatio * 2))
                        .height(letterboxHeight + (virtualRatio * 2));

                origin.y((letterboxHeight / 2.0f) - (height / 2.0f));
            } else if (displayRatio > internalRatio) {
                long letterboxWidth = ((long) INTERNAL_WIDTH * height) / INTERNAL_HEIGHT;

                destRec = new Rectangle()
                        .x(-virtualRatio).y(-virtualRatio)
                        .width(letterboxWidth + (virtualRatio * 2))
                        .height(height + (virtualRatio * 2));

                origin.x((letterboxWidth / 2.0f) - (width / 2.0f));
            }
        }
    }

    public void updateDisplay(SceneManager sceneManager) {
        while (!WindowShouldClose()) {
            if (forceClose) break;

            sceneManager.getCurrentScene().tick();

            if (IsKeyPressed(KEY_F11)) {
                if (settings.getDisplayMode().equals(DisplayMode.WINDOWED)) {
                    settings.setDisplayMode(DisplayMode.FULLSCREEN);
                } else {
                    settings.setDisplayMode(DisplayMode.WINDOWED);
                }

                setDisplayDimensions();
                setRenderTextureDimensions();
                SetWindowSize(width, height);
                setDisplayDimensions();
            }

            BeginTextureMode(target);
                ClearBackground(BLACK);

                BeginMode2D(worldCamera);
                    sceneManager.getCurrentScene().render();
                    sceneManager.getCurrentScene().ui();

                    //DrawFPS(5, 5);
                EndMode2D();
            EndTextureMode();

            BeginDrawing();
                ClearBackground(BLACK);

                BeginMode2D(screenCamera);
                    DrawTexturePro(target.texture(), sourceRec, destRec, origin, 0.0f, WHITE);
                EndMode2D();
            EndDrawing();
        }
    }

    public void closeDisplay() {
        CloseAudioDevice();

        UnloadRenderTexture(target);

        CloseWindow();
    }

    public Camera2D getCamera() {
        return worldCamera;
    }

    public int getInternalWidth() {
        return INTERNAL_WIDTH;
    }

    public int getInternalHeight() {
        return INTERNAL_HEIGHT;
    }

    public int getCurrentWidth() {
        return width;
    }

    public int getCurrentHeight() {
        return height;
    }

    public Vector2 getMousePosition() {
        Vector2 position = GetMousePosition();
        position.x(position.x() / virtualRatio);
        position.y(position.y() / virtualRatio);
        return position;
    }

    public void forceClose() {
        forceClose = true;
    }

}
