package si.fri.nrg.sem.app.models;

import com.fasterxml.jackson.databind.ObjectMapper;
import si.fri.nrg.sem.app.CmdOptions;
import si.fri.nrg.sem.app.models.files.LoadRange;
import si.fri.nrg.sem.app.models.files.base.LoadFiles;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class LidarConfiguration {

    public static String FILE_NAME = "lidar.conf";

    public String outputFolder;
    public LoadFiles loadFiles;


    public LidarConfiguration() {
    }

    public LidarConfiguration(CmdOptions options) {
        outputFolder = options.outputFolder;

        if(options.loadRange != null){
            loadFiles = new LoadRange(options.loadRange);
        }
    }

    public static LidarConfiguration init() throws IOException {
        if(exists()){
            ObjectMapper mapper = new ObjectMapper();
            return mapper.readValue(new File(FILE_NAME), LidarConfiguration.class);
        } else {
            return null;
        }

    }

    public static boolean exists(){
        return Files.exists(Paths.get(FILE_NAME));
    }
}
