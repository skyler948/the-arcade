package game;

import assets.AssetManager;
import display.Display;
import scenes.*;
import score.ScoreManager;
import settings.Settings;

public class Game {

    private Settings settings;
    private Display display;

    private ScoreManager scoreManager;

    private SceneManager sceneManager;

    private AssetManager assetManager;

    public Game() {
        settings = new Settings(this);
        display = new Display(settings);

        display.createDisplay();

        assetManager = new AssetManager();

        loadAssets();

        scoreManager = new ScoreManager(this);

        sceneManager = new SceneManager();
        sceneManager.setScene(new BreakoutScene(this));

        settings.setMasterVolume(settings.getMasterVolume());
        settings.setMusicVolume(settings.getMusicVolume());
        settings.setSoundVolume(settings.getSoundVolume());

        display.updateDisplay(sceneManager);

        sceneManager.getCurrentScene().close();
        assetManager.unloadAssets();
        display.closeDisplay();
    }

    private void loadAssets() {
        assetManager.loadTexture("logo");
        assetManager.loadMusic("sneaky_snitch");
        assetManager.loadSound("coin");
        assetManager.loadFont("arial");
    }

    public Settings getSettings() {
        return settings;
    }

    public Display getDisplay() {
        return display;
    }

    public ScoreManager getScoreManager() {
        return scoreManager;
    }

    public SceneManager getSceneManager() {
        return sceneManager;
    }

    public AssetManager getAssetManager() {
        return assetManager;
    }

}
