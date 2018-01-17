package si.fri.nrg.sem;

import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import si.fri.nrg.sem.app.LidarApp;
import si.fri.nrg.sem.app.LidarCmdOptions;

public class Main {

    public static void main(String[] args) {

        try {
            LidarCmdOptions options = new LidarCmdOptions();
            CmdLineParser parser = new CmdLineParser(options);
            parser.parseArgument(args);

            checkOptions(options);

            LidarApp app = new LidarApp(options);
            app.start();

        } catch (CmdLineException e) {
            e.printStackTrace();
        }
    }

    public static void checkOptions(LidarCmdOptions options){
        if(options.outputDirectory == null) {
            System.out.println("No output directory specified");
            System.exit(0);
        }
    }

}
