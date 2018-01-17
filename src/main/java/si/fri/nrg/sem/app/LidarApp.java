package si.fri.nrg.sem.app;

import si.fri.nrg.sem.app.files.CsvLidarFilesModel;
import si.fri.nrg.sem.app.files.RangeLidarFilesModel;
import si.fri.nrg.sem.app.files.base.LidarFilesModel;
import si.fri.nrg.sem.app.files.model.LidarFile;
import si.fri.nrg.sem.app.loader.LocalLidarLoader;
import si.fri.nrg.sem.app.loader.OnlineLidarLoader;
import si.fri.nrg.sem.app.loader.base.LidarLoader;
import si.fri.nrg.sem.app.reshaper.LasMergeLidarReshaper;
import si.fri.nrg.sem.utility.LidarLog;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;


public class LidarApp {

    public LidarCmdOptions options;

    public LidarFilesModel filesModel;
    public LasMergeLidarReshaper reshaper;
    public LidarLoader loader;

    public LidarApp(LidarCmdOptions options) {
        this.options = options;

        init();
    }

    public void init(){
        initLidarFilesModel();
        initLidarLoader();
        initReshaper();
    }

    public void initLidarFilesModel(){
        if(options.loadRange != null){
            filesModel = new RangeLidarFilesModel(options.loadRange);
        } else {
            filesModel = new CsvLidarFilesModel();
        }
    }

    public void initLidarLoader(){
        if(options.inputDirectory != null){
            loader = new LocalLidarLoader(this);
        } else {
            loader = new OnlineLidarLoader(this);
        }
    }

    public void initReshaper(){
        reshaper = new LasMergeLidarReshaper(this);
    }

    public void start() {
        if(filesModel != null){
            LidarFile lidarFile;
            while((lidarFile = filesModel.getNextFile()) != null){
                processFile(lidarFile);
            }
        } else {
            LidarLog.log("No files to process.");
        }
    }

    public void processFile(LidarFile lidarFile) {

        Path preparedFile = loader.prepareFile(lidarFile);

        if(Files.exists(preparedFile)){
            reshaper.processFile(preparedFile);
        }
    }

    public void cleanUpFiles(Path preparedFile) {
        if(loader instanceof OnlineLidarLoader){
            try {
                Files.deleteIfExists(preparedFile);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
