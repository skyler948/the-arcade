package components;

import game.Game;

import static com.raylib.Raylib.*;
import static com.raylib.Colors.*;

public class SpriteRenderer extends Component {

    private Game game;

    private String textureName;
    private Color tint;

    private Rectangle crop;

    private Texture texture;

    public SpriteRenderer(Game game, String textureName, Color tint) {
        this.game = game;
        this.textureName = textureName;
        this.tint = tint;
        this.texture = game.getAssetManager().getTexture(textureName);
    }

    public SpriteRenderer(Game game, String textureName, Color tint, int x, int y, int width, int height) {
        this.game = game;
        this.textureName = textureName;
        this.tint = tint;
        this.texture = game.getAssetManager().getTexture(textureName);
        this.crop = new Rectangle()
                .x(x)
                .y(y)
                .width(width)
                .height(height);
    }

    public SpriteRenderer(Game game, String textureName) {
        this(game, textureName, WHITE);
    }

    public SpriteRenderer(Game game, String textureName, int x, int y, int width, int height) {
        this(game, textureName, WHITE, x, y, width, height);
    }

    @Override
    public String getType() {
        return "SpriteRenderer";
    }

    @Override
    public void tick() { }

    @Override
    public void render() {
        if (!parent.isActive()) return;

        if (crop == null) {
            DrawTextureEx(texture, parent.getTransform().getLocalPosition(), parent.getTransform().getLocalRotation(), parent.getTransform().getLocalScale(), tint);
        } else {
            DrawTextureRec(texture, crop, parent.getTransform().getLocalPosition(), tint);
        }
    }

    public String getTextureName() {
        return textureName;
    }

    public void setTextureName(String textureName) {
        this.textureName = textureName;
        this.texture = game.getAssetManager().getTexture(textureName);
    }

    public Color getTint() {
        return tint;
    }

    public void setTint(Color tint) {
        this.tint = tint;
    }

    public Texture getTexture() {
        return texture;
    }

}
