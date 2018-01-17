package si.fri.nrg.sem.app.reshaper.base;

import si.fri.nrg.sem.app.LidarApp;

import java.nio.file.Path;

public abstract class LidarReshaper {

    protected LidarApp app;

    public LidarReshaper(LidarApp app) {
        this.app = app;
    }

    public abstract void processFile(Path source);

}
