package scenes;

import game.Game;
import objects.GameObject;

import java.util.ArrayList;

public abstract class Scene {

    private static long currentId = 0L;

    private final long id;

    protected Game game;
    protected ArrayList<GameObject> objects;

    public Scene(Game game) {
        this.game = game;
        this.id = currentId++;
        this.objects = new ArrayList<>();
    }

    public abstract void init();

    public abstract void tick();

    public abstract void render();

    public abstract void ui();

    public abstract void close();

    public void tickObjects() {
        for (GameObject obj : objects) {
            obj.tick();
        }
    }

    public void renderObjects() {
        for (GameObject obj : objects) {
            obj.render();
        }
    }

    public float getCenterX(float width) {
        return (game.getDisplay().getInternalWidth() / 2.f) - (width / 2.f);
    }

    public float getCenterY(float height) {
        return (game.getDisplay().getInternalHeight() / 2.f) - (height / 2.f);
    }

    public long getId() {
        return id;
    }

}
