/**
 * This class represents the command line arguments specified for current run
 *
 * @author (Shayna Shaw)
 * @version (20.12.2022)
 */

public class Arguments{
    private final String imagePath;
    private final String maskPath;
    private final float e;
    private final float z;
    private final int connectivity;

    public String getImagePath() {
        return imagePath;
    }

    public String getMaskPath() {
        return maskPath;
    }

    public float getEpsilon() {
        return e;
    }

    public float getZ() {
        return z;
    }

    public int getConnectivity() {
        return connectivity;
    }

    /*This constructor gets the cmd input and updates the arguments accordingly*/
    public Arguments(String[] args) throws Exception {
        try {
            this.imagePath = args[0];
            this.maskPath = args[1];
            this.z = Integer.parseInt(args[2]);
            this.e = Float.parseFloat(args[3]);
            this.connectivity = Integer.parseInt(args[4]);
        } catch (Exception e) {
            throw new Exception("Missing Arguments");
        }
        if (connectivity != 4 && connectivity != 8)
            throw new Exception("Invalid Connectivity");
    }
}
