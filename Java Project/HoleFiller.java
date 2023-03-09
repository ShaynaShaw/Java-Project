
/**
 * This class rrepresents a library that deals with filling holes in a grayscale image
 *
 * @author (Shayna Shaw)
 * @version (20.12.2022)
 */

import org.opencv.core.Mat;

import java.awt.*;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Queue;

public class HoleFiller {

    public static final float GRAYSCALE_HOLE = -1;//hole in grayscale
    public static final float VISITED = -2;
    public static final int EIGHT_CONNECTIVITY = 8;

    private Mat image;
    private Arguments functionArgs;
    private WeightFunction weight;
    private ArrayList<PixelPoint> holePixels;
    private ArrayList<PixelPoint> boundarySet;

    public Mat getImage() {
        return image;
    }


    public HoleFiller(Mat image, Arguments functionArgs, WeightFunction weight) {
        this.image = image;
        this.functionArgs = functionArgs;
        this.holePixels = new ArrayList<PixelPoint>();
        this.boundarySet = new ArrayList<PixelPoint>();
        this.weight = weight;
        //for simplicity, we assume every image has a single hole, that way we can find the first hole and progress accordingly
        findHole(image);
    }

    /*this function returns true if there is a hole at given index (i,j) in given image*/
    private Boolean isHole(Mat image, int i, int j) {
        return (image.get(i, j)[0] == GRAYSCALE_HOLE);
    }

    /*this function finds the whole hole in the image according to a given hole pixel, based on the BFS algorithm
     * assuming the hole is 8 connected*/
    private void findHole(Mat image) {
        Queue<PixelPoint> holePixelsQueue = new ArrayDeque<>();//use BFS to find hole
        boolean flag = false;
        for (int i = 0; i < image.rows(); i++) {//find first hole pixel
            if (flag)//already found first hole pixel
                break;
            for (int j = 0; j < image.cols(); j++)
                if (isHole(image, i, j)) {//if pixel has value of -1
                    addHolePoint(new PixelPoint(i, j, VISITED), holePixelsQueue);
                    flag = true;
                    break;
                }
        }
        while (!holePixelsQueue.isEmpty()) { //still hole pixels to add there neighbors
            PixelPoint curr = holePixelsQueue.poll();//pop head of queue
            visitNeighbors(curr, holePixelsQueue);
        }
        fillHoles();//after finding hole and boundary - go ahead and fill the hole according to algorithm
    }

    /*this function goes through the neighbors of a given pixel*/
    private void visitNeighbors(PixelPoint currPoint, Queue<PixelPoint> q) {
        for (int i = -1; i <= 1; i++)
            for (int j = -1; j <= 1; j++) {//assume hole is 8 connective, add the missing neighbors to Queue and hole list
                if (i == 0 && j == 0)//currPoint
                    continue;
                if (isHole(this.image, (int) currPoint.getX() + i, (int) currPoint.getY() + j))
                    addHolePoint(new PixelPoint((int) currPoint.getX() + i, (int) currPoint.getY() + j, VISITED), q);
                else//might be boundary pixel
                    /*use enum to allow specific values and them polymorphism to call wanted function*/
                    if (i == 0 || j == 0 || this.functionArgs.getConnectivity() == EIGHT_CONNECTIVITY)
                        addBoundaryPoint((int) currPoint.getX() + i, (int) currPoint.getY() + j);
            }
    }

    /*this function adds a given boundary point to the boundary set*/
    private void addBoundaryPoint(int i, int j) {
        if (this.image.get(i, j)[0] != VISITED) //if didn't visit pixel yet
            this.boundarySet.add(new PixelPoint(i, j, (float) this.image.get(i, j)[0]));
    }

    /*this function adds the given hole pixel to the Queue. and assigns its value to VISITED to avoid visiting again*/
    private void addHolePoint(PixelPoint holePixel, Queue<PixelPoint> holePixelsQueue) {
        holePixelsQueue.add(holePixel);
        this.image.put((int) holePixel.getX(), (int) holePixel.getY(), holePixel.getValue());
        this.holePixels.add(holePixel);
    }

    /*this function fills every pixel hole in the image according to the hole filling algorithm*/
    private void fillHoles() {
        for (PixelPoint curr : this.holePixels)
            image.put((int) curr.getX(), (int) curr.getY(), algorithm(curr, this.boundarySet));//update value in image
    }

    /*this function calculates the new value of a hole pixel according to the given boundary*/
    private float algorithm(PixelPoint hole, ArrayList<PixelPoint> boundaries) {
        float sum1 = 0;
        float sum2 = 0;
        for (PixelPoint b : boundaries) {
            float w = this.weight.weight(new Point((int) hole.getX(), (int) hole.getY()), new Point((int) b.getX(), (int) b.getY()));
            sum1 += w * b.getValue();
            sum2 += w;
        }
        return (sum1 / (sum2 + this.functionArgs.getEpsilon()));
    }
}



