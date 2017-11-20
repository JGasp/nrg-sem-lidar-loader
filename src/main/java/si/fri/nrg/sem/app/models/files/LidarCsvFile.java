package si.fri.nrg.sem.app.models.files;

public class LidarCsvFile {

    public String name;
    public String block;

    public LidarCsvFile(String block, String name) {
        this.name = name;
        this.block = block;
    }
}
