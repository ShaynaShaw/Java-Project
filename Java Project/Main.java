/**
 * This class represents the main hole filling class
 *
 * @author (Shayna Shaw)
 * @version (21.12.2022)
 */

import org.opencv.core.Core;
import org.opencv.core.Mat;

public class Main {

    static{ System.loadLibrary(Core.NATIVE_LIBRARY_NAME); }
    public static void main(String[] args) {
        try {
            Arguments functionArgs = new Arguments(args);//accepts input image files, 𝑧, ϵ and connectivity type
            //support any arbitrary weighting function provided by the user, each user will create an object according to its weight function object
            WeightFunction weight = new DefaultWeight(functionArgs.getEpsilon(), functionArgs.getZ());
            Mat combinedGrayScaleImg = ImageProcessing.carveHole(functionArgs.getImagePath(),functionArgs.getMaskPath());
            HoleFiller holeFill = new HoleFiller(combinedGrayScaleImg, functionArgs, weight);//use library to fill hole
            ImageProcessing.saveImage(holeFill.getImage(), functionArgs.getImagePath()," hole filled");
            System.out.println("Congrats! Hole filled successfully!");
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
            System.exit(1);
        }
    }
}