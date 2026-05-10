package components;

import com.raylib.Colors;

import static com.raylib.Raylib.*;
import static com.raylib.Colors.*;

public class RectangleRenderer extends Component {

    private Color color;
    private Vector2 size;

    public RectangleRenderer(Color color, float width, float height) {
        this.color = color;
        this.size = new Vector2().x(width).y(height);
    }

    public RectangleRenderer(float width, float height) {
        this(WHITE, width, height);
    }

    @Override
    public String getType() {
        return "RectangleRenderer";
    }

    @Override
    public void tick() { }

    @Override
    public void render() {
        if (parent.isActive()) {
            DrawRectangleV(parent.getTransform().getLocalPosition(),
                    new Vector2().x(size.x() * parent.getTransform().getLocalScale()).y(size.y() * parent.getTransform().getLocalScale()), color);
        }
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public Vector2 getSize() {
        return size;
    }

    public void setSize(Vector2 size) {
        this.size = size;
    }

}
