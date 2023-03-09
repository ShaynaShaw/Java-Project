/**
 * This class represents an image processing implementation
 *
 * @author (Shayna Shaw)
 * @version (20.12.2022)
 */
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;

import java.io.File;


public class ImageProcessing {
    public static final int MASK_HOLE = 0;//hole in mask = black color
    public static final float IMAGE_HOLE = -255;//hole in colored image

    /*This function gets an image path and a mask path, then calls combineImages function to carve hole in image,
    *than converts image to grayscale
    * returns the grayscale image with the hole
    * */
    public static Mat carveHole(String imagePath, String maskPath) {
        Mat image = combineImages(readImage(imagePath), readImage(maskPath)); //the image with hole to fix
        image.convertTo(image, -1, (1.0 / 255));   // convert to grayscale
        return image;
    }

    /*This function gets an RGB image path and reads the RGB image
     * returns the image
     * */
    private static Mat readImage(String filePath) {
        Mat image = Imgcodecs.imread(filePath, Imgcodecs.IMREAD_GRAYSCALE);
        image.convertTo(image, CvType.CV_64FC1);
        return image;
    }

    /*This function gets an image and a mask, and carves a hole in the image according to mask
     *(wherever mask is black = RGB value of zero), by putting value of -255. that way  when converting to grayscale will get -1
     * returns combined image with the hole
     * */
    private static Mat combineImages(Mat image, Mat mask) {
        Mat combinedImg = image.clone();
        for (int x = 0; x < mask.rows(); x++)
            for (int y = 0; y < mask.cols(); y++)
                if (mask.get(x, y)[0] == MASK_HOLE)
                    combinedImg.put(x, y, IMAGE_HOLE);//when we will convert to grayscale we will get val -1
        return combinedImg;
    }

    /*This function gets an image, path, and name and saves the image giving it name in the specified path*/
    public static void saveImage(Mat image, String ImagePath, String text) {
        image.convertTo(image, CvType.CV_8UC1, 255, 0); //convert back to grayscale
        File f = new File(ImagePath);
        Imgcodecs.imwrite(f.getParent() +"\\" + text + " " + f.getName(), image);
    }
}
