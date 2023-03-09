/**
 * This class represents the default weight function
 *
 * @author (Shayna Shaw)
 * @version (21.12.2022)
 */

import java.awt.*;

public class DefaultWeight implements  WeightFunction{
    private float e;
    private float z;

    public DefaultWeight(float e, float z){
        this.e = e;
        this.z = z;
    }

    /*This function calculates the euclidean distance between two given points*/
    public double euclideanDistanceFormula(Point p1, Point p2) {
        return  Math.sqrt(Math.pow(p1.getY() - p2.getY(),2) + Math.pow(p1.getX() - p2.getX(),2));
    }

    /*This function consists of the default weight function*/
    public float weight(Point p1, Point p2) {
        return 1 / ((float) Math.pow(euclideanDistanceFormula(p1, p2), z) + e);
    }
}
