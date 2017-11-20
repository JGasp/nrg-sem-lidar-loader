package si.fri.nrg.sem.app;

import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import si.fri.nrg.sem.app.loader.LidarLoader;
import si.fri.nrg.sem.app.models.LidarConfiguration;
import si.fri.nrg.sem.app.models.files.LoadCsv;
import si.fri.nrg.sem.app.reshaper.LidarReshaper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Date;


public class LidarApp {

    private static SimpleDateFormat logSdf = new SimpleDateFormat("hh:MM:ss");

    private LidarConfiguration configuration;


    public LidarApp(String[] args) {
        initConfig(args);
    }

    private void initConfig(String[] args) {
        try {
            configuration = LidarConfiguration.init();

            if(configuration == null){
                configuration = initCmdArguments(args);
            }

            LoadCsv loadCsv =  LoadCsv.init();
            if(loadCsv != null){
                configuration.loadFiles = loadCsv;
            }
        } catch(CmdLineException | IOException e ) {
            e.printStackTrace();
            System.exit(0);
        }
    }

    private LidarConfiguration initCmdArguments(String[] args) throws CmdLineException {
        CmdOptions options = new CmdOptions();
        CmdLineParser parser = new CmdLineParser(options);
        parser.parseArgument(args);
        return new LidarConfiguration(options);
    }

    public void start(){
        LidarLoader lidarLoader = new LidarLoader(configuration);
        lidarLoader.setLidarReshaper(new LidarReshaper(configuration));

        lidarLoader.load();
    }

    public static void printProcessOutput(Process proc) {
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(proc.getErrorStream()));
            String line;
            while((line = br.readLine()) != null) {
                System.out.println(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void log(String message) {
        System.out.printf("[%s] %s\n", logSdf.format(new Date()), message);
    }

}
