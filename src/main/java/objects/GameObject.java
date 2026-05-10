package objects;

import components.Component;
import components.Transform;

import java.util.ArrayList;

public class GameObject {

    private ArrayList<Component> components;

    private Transform transform;
    private boolean active = true;

    public GameObject() {
        transform = new Transform();
        components = new ArrayList<>();
    }

    public void tick() {
        if (!active) return;
        for (Component component : components) {
            component.tick();
        }
    }

    public void render() {
        if (!active) return;
        for (Component component : components) {
            component.render();
        }
    }

    public void addComponent(Component component) {
        components.add(component);
        component.setParent(this);
    }

    public void removeComponent(Component component) {
        components.remove(component);
        component.setParent(null);
    }

    public Component getComponent(String type) {
        for (Component component : components) {
            if (component.getType().equals(type)) {
                return component;
            }
        }
        return null;
    }

    public Transform getTransform() {
        return transform;
    }

    public void setTransform(Transform transform) {
        this.transform = transform;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

}
