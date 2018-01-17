package si.fri.nrg.sem.app.loader.base;

import si.fri.nrg.sem.app.LidarApp;
import si.fri.nrg.sem.app.files.model.LidarFile;

import java.nio.file.Path;

public abstract class LidarLoader {

    protected static String LIDAR_TYPE = "D96TM";
    protected static String LIDAR_TYPE_PREFIX = "TM";

    protected static String LIDAR_FILE_NAME_TEMPALTE = LIDAR_TYPE_PREFIX + "_%d_%d.laz";


    protected LidarApp app;


    public LidarLoader(LidarApp app) {
        this.app = app;
    }


    public abstract Path prepareFile(LidarFile lidarFile);
}
