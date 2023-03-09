import java.awt.*;

/*this interface represents the weight function, supports any arbitrary weighting function implemented by the user*/
public interface WeightFunction {
    public float weight(Point u, Point v);
}

