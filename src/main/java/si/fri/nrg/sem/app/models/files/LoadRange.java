package si.fri.nrg.sem.app.models.files;

import si.fri.nrg.sem.app.models.files.base.LoadFiles;

public class LoadRange extends LoadFiles {

    private String block;

    private int fromX;
    private int fromY;

    private int toX;
    private int toY;

    private int currX;
    private int currY;

    public LoadRange(int fromX, int toX, int fromY, int toY) {
        this.fromX = fromX;
        this.toX = toX;
        this.fromY = fromY;
        this.toY = toY;

        currX = fromX;
        currY = fromY;
    }

    public LoadRange(String loadRange) {
        String[] values = loadRange.split(":");

        this.fromX = Integer.valueOf(values[0]);
        this.toX = Integer.valueOf(values[1]);
        this.fromY = Integer.valueOf(values[2]);
        this.toY = Integer.valueOf(values[3]);
        this.block = values[4];

    }


    @Override
    public String getNextFileUrl() {
        String url = getFileName(block, currX, currY);

        currX++;
        if(currX > toX){
            currX = fromX;
            currY++;
        }

        if(currY <= toY){
            return url;
        } else {
            return null;
        }
    }
}
