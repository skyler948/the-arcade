package components;

import static com.raylib.Colors.RED;
import static com.raylib.Raylib.*;

public class Hitbox extends Component {

    private Vector2 size;

    private boolean debug = false;

    public Hitbox(Vector2 size) {
        this.size = size;
    }

    public Hitbox(float width, float height) {
        size = new Vector2().x(width).y(height);
    }

    @Override
    public String getType() {
        return "Hitbox";
    }

    @Override
    public void tick() { }

    @Override
    public void render() {
        if (debug && parent.isActive()) {
            DrawRectangleLinesEx(getHitbox(), 2, RED);
        }
    }

    public Rectangle getHitbox() {
        return new Rectangle()
                .x(parent.getTransform().getLocalPosition().x()).y(parent.getTransform().getLocalPosition().y())
                .width(size.x() * parent.getTransform().getLocalScale()).height(size.y() * parent.getTransform().getLocalScale());
    }

}
