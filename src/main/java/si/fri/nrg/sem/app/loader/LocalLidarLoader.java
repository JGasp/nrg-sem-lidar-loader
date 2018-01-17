package si.fri.nrg.sem.app.loader;

import si.fri.nrg.sem.app.LidarApp;
import si.fri.nrg.sem.app.files.model.LidarFile;
import si.fri.nrg.sem.app.loader.base.LidarLoader;

import java.nio.file.Path;
import java.nio.file.Paths;

public class LocalLidarLoader extends LidarLoader {

    protected static String LOCAL_FILE_TEMPLATE = "gkot/%s/" + LIDAR_TYPE + "/" + LIDAR_FILE_NAME_TEMPALTE;


    public LocalLidarLoader(LidarApp lidarApp) {
        super(lidarApp);
    }


    public String getLocalFileName(LidarFile f){
        return String.format(LOCAL_FILE_TEMPLATE, f.getBlock(), f.getX(), f.getY());
    }


    @Override
    public Path prepareFile(LidarFile lidarFile) {
        Path localFilePath = Paths.get(app.options.inputDirectory, getLocalFileName(lidarFile));
        return localFilePath;
    }

}
