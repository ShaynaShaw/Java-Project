import java.awt.*;

public class TestWeight implements  WeightFunction{

    /*This function calculates the euclidean distance between two given points*/
    public double euclideanDistanceFormula(Point p1, Point p2) {
        return  Math.sqrt(Math.pow(p1.getY() - p2.getY(),2) + Math.pow(p1.getX() - p2.getX(),2));
    }

    /*This function consists of the default weight function*/
    public float weight(Point p1, Point p2) {
        return (float) ((p1.getX()+p1.getY())/(p2.getX()+p2.getY()));
    }
}
