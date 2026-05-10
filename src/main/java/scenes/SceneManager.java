package scenes;

public class SceneManager {

    private Scene currentScene;

    public Scene getCurrentScene() {
        return currentScene;
    }

    public void setScene(Scene scene) {
        if (this.currentScene != null) {
            this.currentScene.close();
        }
        this.currentScene = scene;
        this.currentScene.init();
    }

}
