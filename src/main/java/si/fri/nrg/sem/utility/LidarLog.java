package si.fri.nrg.sem.utility;

import java.text.SimpleDateFormat;
import java.util.Date;

public class LidarLog {

    private static SimpleDateFormat logSdf = new SimpleDateFormat("hh:MM:ss");

    public static void log(String message) {
        System.out.printf("[%s] %s\n", logSdf.format(new Date()), message);
    }

}
