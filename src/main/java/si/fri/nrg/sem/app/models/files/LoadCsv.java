package si.fri.nrg.sem.app.models.files;

import si.fri.nrg.sem.app.LidarApp;
import si.fri.nrg.sem.app.models.files.base.LoadFiles;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class LoadCsv extends LoadFiles {

    private static String FILE_NAME = "lidarFiles.csv";

    private int currIndex = 0;
    private List<LidarCsvFile> lidarCsvFiles = new ArrayList<>();


    public LoadCsv(List<LidarCsvFile> lidarCsvFiles) {
        this.lidarCsvFiles = lidarCsvFiles;
    }


    public static LoadCsv init() throws IOException {
        if(exists()){
            ArrayList<LidarCsvFile> files = new ArrayList<>();

            BufferedReader br = new BufferedReader(new FileReader(FILE_NAME));

            br.readLine(); // Read head
            String line;
            while((line = br.readLine()) != null) {
                String[] vals = line.split(";");
                files.add(new LidarCsvFile(vals[0], vals[1]));
            }
            return new LoadCsv(files);
        } else {
            return null;
        }
    }

    public static boolean exists(){
        return Files.exists(Paths.get(FILE_NAME));
    }

    @Override
    public String getNextFileUrl() {
        if(currIndex < lidarCsvFiles.size()) {
            LidarCsvFile file = lidarCsvFiles.get(currIndex);
            String url = getFileName(file.block, file.name);

            LidarApp.log(String.format("Loading file %d/%d", currIndex, lidarCsvFiles.size()));

            currIndex++;
            return url;
        } else {
            LidarApp.log("All files were loaded");
            return null;
        }
    }
}
