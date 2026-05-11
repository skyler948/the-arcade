package components;

import game.Game;

import static com.raylib.Raylib.*;
import static com.raylib.Colors.*;

public class Button extends Component {

    private static final int DEFAULT_FONT_SIZE = 20;
    private static final int DEFAULT_THICKNESS = 2;
    private static final Color HIGHLIGHT_COLOR = BLUE;

    private Game game;

    private Vector2 size;
    private String text;
    private int thickness;
    private int fontSize;
    private Color outColor, inColor;

    private boolean pressed;
    private boolean hovered;

    public Button(Game game, Vector2 size, String text, int thickness, int fontSize, Color outColor, Color inColor) {
        this.game = game;
        this.size = size;
        this.text = text;
        this.thickness = thickness;
        this.fontSize = fontSize;
        this.outColor = outColor;
        this.inColor = inColor;
    }

    public Button(Game game, float width, float height, String text, int thickness, int fontSize, Color outColor, Color inColor) {
        this.game = game;
        this.size = new Vector2().x(width).y(height);
        this.text = text;
        this.thickness = thickness;
        this.fontSize = fontSize;
        this.outColor = outColor;
        this.inColor = inColor;
    }

    public Button(Game game, Vector2 size, String text) {
        this(game, size, text, DEFAULT_THICKNESS, DEFAULT_FONT_SIZE, WHITE, BLACK);
    }

    public Button(Game game, float width, float height, String text) {
        this(game, width, height, text, DEFAULT_THICKNESS, DEFAULT_FONT_SIZE, WHITE, BLACK);
    }

    public Button(Game game, Vector2 size, String text, int fontSize) {
        this(game, size, text, DEFAULT_THICKNESS, fontSize, WHITE, BLACK);
    }

    public Button(Game game, float width, float height, String text, int fontSize) {
        this(game, width, height, text, DEFAULT_THICKNESS, fontSize, WHITE, BLACK);
    }

    @Override
    public String getType() {
        return "Button";
    }

    @Override
    public void tick() {
        if (CheckCollisionPointRec(game.getDisplay().getMousePosition(), getBounds())) {
            hovered = true;
            pressed = IsMouseButtonPressed(MOUSE_BUTTON_LEFT);
        } else {
            hovered = false;
            pressed = false;
        }
    }

    @Override
    public void render() {
        DrawRectangleRec(getBounds(), (hovered ? HIGHLIGHT_COLOR : inColor));
        DrawRectangleLinesEx(getBounds(), thickness, outColor);
        DrawText(text, (int) (parent.getTransform().getLocalPosition().x() + ((size.x() / 2.f) - ((MeasureText(text, fontSize)) / 2.f))),
                (int) (parent.getTransform().getLocalPosition().y() + ((size.y() / 2.f) - (fontSize / 2.f))),
                fontSize, outColor);
    }

    public Rectangle getBounds() {
        return new Rectangle()
                .x(parent.getTransform().getLocalPosition().x())
                .y(parent.getTransform().getLocalPosition().y())
                .width(size.x())
                .height(size.y());
    }

    public Vector2 getSize() {
        return size;
    }

    public void setSize(Vector2 size) {
        this.size = size;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getThickness() {
        return thickness;
    }

    public void setThickness(int thickness) {
        this.thickness = thickness;
    }

    public int getFontSize() {
        return fontSize;
    }

    public void setFontSize(int fontSize) {
        this.fontSize = fontSize;
    }

    public Color getOutColor() {
        return outColor;
    }

    public void setOutColor(Color outColor) {
        this.outColor = outColor;
    }

    public Color getInColor() {
        return inColor;
    }

    public void setInColor(Color inColor) {
        this.inColor = inColor;
    }

    public boolean isPressed() {
        return pressed;
    }

    public boolean isHovered() {
        return hovered;
    }

}
