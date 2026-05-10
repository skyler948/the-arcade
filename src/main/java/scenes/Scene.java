package scenes;

import game.Game;

public abstract class Scene {

    private static long currentId = 0L;

    private final long id;

    protected Game game;

    public Scene(Game game) {
        this.game = game;
        this.id = currentId++;
    }

    public abstract void init();

    public abstract void tick();

    public abstract void render();

    public abstract void ui();

    public abstract void close();

    public long getId() {
        return id;
    }

}
