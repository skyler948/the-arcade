package components;

import static com.raylib.Raylib.*;

public class Transform extends Component {

    private Vector2 localPosition;
    private float localRotation;
    private float localScale;

    public Transform(Vector2 localPosition, float localRotation, float localScale) {
        this.localPosition = localPosition;
        this.localRotation = localRotation;
        this.localScale = localScale;
    }

    public Transform(Vector2 localPosition) {
        this(localPosition, 0.f, 1.f);
    }

    public Transform() {
        this(new Vector2().x(0.f).y(0.f));
    }

    @Override
    public String getType() {
        return "Transform";
    }

    @Override
    public void tick() { }

    @Override
    public void render() { }

    public Vector2 getLocalPosition() {
        return localPosition;
    }

    public void setLocalPosition(Vector2 localPosition) {
        this.localPosition = localPosition;
    }

    public void setLocalPosition(float x, float y) {
        this.localPosition = new Vector2().x(x).y(y);
    }

    public void changeLocalPosition(float dx, float dy) {
        localPosition.x(localPosition.x() + dx);
        localPosition.y(localPosition.y() + dy);
    }

    public float getLocalRotation() {
        return localRotation;
    }

    public void setLocalRotation(float localRotation) {
        this.localRotation = localRotation;
    }

    public float getLocalScale() {
        return localScale;
    }

    public void setLocalScale(float localScale) {
        this.localScale = localScale;
    }

}
