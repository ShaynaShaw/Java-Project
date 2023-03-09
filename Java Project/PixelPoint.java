/**
 * This class represents a pixel point in an image
 *
 * @author (Shayna Shaw)
 * @version (20.12.2022)
 */
import java.awt.*;

public class PixelPoint extends Point {
    private float value;

    public PixelPoint(int x, int y, float value) {
        super(x, y);
        this.value = value;
    }

    public float getValue() {
        return value;
    }

    public void setValue(float value) {
        this.value = value;
    }
}
