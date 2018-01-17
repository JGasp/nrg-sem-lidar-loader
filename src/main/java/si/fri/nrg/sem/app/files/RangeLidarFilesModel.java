package si.fri.nrg.sem.app.files;

import si.fri.nrg.sem.app.files.base.LidarFilesModel;
import si.fri.nrg.sem.app.files.model.LidarFile;

public class RangeLidarFilesModel extends LidarFilesModel {

    private String block;

    private int fromX;
    private int fromY;

    private int toX;
    private int toY;

    private int currX;
    private int currY;

    public RangeLidarFilesModel(String block, int fromX, int toX, int fromY, int toY) {
        this.block = block;
        this.fromX = fromX;
        this.toX = toX;
        this.fromY = fromY;
        this.toY = toY;

        currX = fromX;
        currY = fromY;
    }

    public RangeLidarFilesModel(String loadRange) {
        String[] values = loadRange.split(":");

        this.block = values[0];
        this.fromX = Integer.valueOf(values[1]);
        this.fromY = Integer.valueOf(values[2]);
        this.toX = Integer.valueOf(values[3]);
        this.toY = Integer.valueOf(values[4]);

        currX = fromX;
        currY = fromY;
    }

    @Override
    public LidarFile getNextFile() {
        if(currY <= toY) {
            LidarFile lidarFile = new LidarFile(block, currX, currY);

            currX++;
            if(currX > toX){
                currX = fromX;
                currY++;
            }

            return lidarFile;
        } else {
            return null;
        }
    }
}
