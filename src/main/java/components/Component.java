package components;

import objects.GameObject;

public abstract class Component {

    protected GameObject parent;

    public abstract String getType();
    public abstract void tick();
    public abstract void render();

    public void setParent(GameObject parent) {
        this.parent = parent;
    }

    public GameObject getParent() {
        return parent;
    }

}
