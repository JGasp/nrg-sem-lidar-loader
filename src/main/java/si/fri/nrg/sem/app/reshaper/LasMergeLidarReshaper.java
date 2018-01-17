package si.fri.nrg.sem.app.reshaper;

import si.fri.nrg.sem.app.LidarApp;
import si.fri.nrg.sem.app.reshaper.base.LidarReshaper;
import si.fri.nrg.sem.utility.LidarLog;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Path;
import java.nio.file.Paths;

public class LasMergeLidarReshaper extends LidarReshaper {

    public LasMergeLidarReshaper(LidarApp lidarApp) {
        super(lidarApp);
    }

    private Path buildDestination(String fileName) {
        String outputFileName = String.format("th-%01f-%s", app.options.gridThinSize, fileName);
        return Paths.get(app.options.outputDirectory, outputFileName);
    }

    @Override
    public void processFile(Path source) {
        try {
            String fileName = source.getFileName().toString();
            LidarLog.log(String.format("Starting reshaping file: %s", fileName));

            Path destination = buildDestination(fileName);

            String[] command = buildCommand(source, destination);

            ProcessBuilder pb = new ProcessBuilder(command);
            Process proc = pb.start();
            proc.waitFor();

            if(proc.exitValue() != 0){
                LidarLog.log(String.format("Error reshaping file: %s", fileName));
                printProcessOutput(proc);
            } else {
                LidarLog.log(String.format("Finished reshaping file: %s", fileName));
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    private String[] buildCommand(Path downloadFilePath,  Path reshapeFilePath) {
        String gridThinSize =  String.format("%f", app.options.gridThinSize);
        return new String[] {
                "C:\\PointCloud\\Programs\\LAStools\\bin\\lasmerge.exe",
                "-thin_with_grid", gridThinSize,
                "-i", downloadFilePath.toString().replace("/", "\\"),
                "-o", reshapeFilePath.toString().replace("/", "\\")
        };
    }

    public void printProcessOutput(Process proc) {
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
}

/*

lasmerge -thin_with_grid 0.5

Filter points with simple thinning.
  -keep_every_nth 2 -drop_every_nth 3
  -keep_random_fraction 0.1
  -thin_with_grid 1.0
  -thin_pulses_with_time 0.0001
  -thin_points_with_time 0.000001

Boolean combination of filters.
  -filter_and

Filter points based on classifications or flags.
  -keep_class 1 3 7
  -drop_class 4 2
  -keep_extended_class 43
  -drop_extended_class 129 135
  -drop_synthetic -keep_synthetic
  -drop_keypoint -keep_keypoint
  -drop_withheld -keep_withheld
  -drop_overlap -keep_overlap
 */