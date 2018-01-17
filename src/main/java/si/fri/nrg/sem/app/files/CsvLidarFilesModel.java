package si.fri.nrg.sem.app.files;

import si.fri.nrg.sem.app.files.base.LidarFilesModel;
import si.fri.nrg.sem.app.files.model.LidarFile;
import si.fri.nrg.sem.utility.LidarLog;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class CsvLidarFilesModel extends LidarFilesModel {

    private static String FILE_NAME = "lidarFiles.csv";

    private int currIndex = 0;
    private List<LidarFile> files = new ArrayList<>();


    public CsvLidarFilesModel() {
        init();
    }

    public void init() {
        if(exists()) {
            try {
                BufferedReader br = new BufferedReader(new FileReader(FILE_NAME));

                br.readLine(); // Read head
                String line;
                while ((line = br.readLine()) != null) {
                    String[] vals = line.split(";");

                    String block = vals[0];

                    String[] cords = vals[1].split("_");
                    int x = Integer.parseInt(cords[0]);
                    int y = Integer.parseInt(cords[1]);

                    files.add(new LidarFile(block, x, y));
                }
            } catch(IOException e) {
                e.printStackTrace();
            }
        } else {
            LidarLog.log("Missing file: lidarFiles.csv");
        }
    }

    public static boolean exists(){
        return Files.exists(Paths.get(FILE_NAME));
    }

    @Override
    public LidarFile getNextFile() {
        if(currIndex < files.size()) {
            LidarFile file = files.get(currIndex);
            currIndex++;
            return file;
        } else {
            return null;
        }
    }
}
