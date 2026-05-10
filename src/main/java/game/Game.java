package game;

import assets.AssetManager;
import display.Display;
import scenes.*;
import settings.Settings;

public class Game {

    private Settings settings;
    private Display display;

    private SceneManager sceneManager;

    private AssetManager assetManager;

    public Game() {
        settings = new Settings(this);
        display = new Display(settings);

        display.createDisplay();

        assetManager = new AssetManager();

        loadAssets();

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

    public SceneManager getSceneManager() {
        return sceneManager;
    }

    public AssetManager getAssetManager() {
        return assetManager;
    }

}
