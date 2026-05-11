package components;

import com.raylib.Colors;

import static com.raylib.Raylib.*;
import static com.raylib.Colors.*;

public class RectangleRenderer extends Component {

    private static final int LINE_THICKNESS = 2;

    private Color color;
    private Vector2 size;

    private boolean outline;

    public RectangleRenderer(Color color, float width, float height, boolean outline) {
        this.color = color;
        this.size = new Vector2().x(width).y(height);
        this.outline = outline;
    }

    public RectangleRenderer(float width, float height, boolean outline) {
        this(WHITE, width, height, outline);
    }

    public RectangleRenderer(Color color, float width, float height) {
        this(color, width, height, false);
    }

    public RectangleRenderer(float width, float height) {
        this(WHITE, width, height, false);
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
            if (outline) {
                DrawRectangleLinesEx(getBounds(), LINE_THICKNESS, color);
            } else {
                DrawRectangleRec(getBounds(), color);
            }
        }
    }

    private Rectangle getBounds() {
        return new Rectangle()
                .x(parent.getTransform().getLocalPosition().x())
                .y(parent.getTransform().getLocalPosition().y())
                .width(size.x() * parent.getTransform().getLocalScale())
                .height(size.y() * parent.getTransform().getLocalScale());
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
