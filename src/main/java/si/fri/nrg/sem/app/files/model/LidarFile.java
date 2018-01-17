package si.fri.nrg.sem.app.files.model;

public class LidarFile {

    private static String UNIQUE_FILE_TEMPLATE = "%s_TM_%d_%d.laz";

    private String block;
    private int x;
    private int y;

    public LidarFile(String block, int x, int y) {
        this.block = block;
        this.x = x;
        this.y = y;
    }

    public String getUniqueFileName(){
        return String.format(UNIQUE_FILE_TEMPLATE, block, x, y);
    }


    public String getBlock() {
        return block;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
