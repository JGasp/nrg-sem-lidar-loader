package si.fri.nrg.sem.app.reshaper;

import si.fri.nrg.sem.app.LidarApp;
import si.fri.nrg.sem.app.models.LidarConfiguration;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class LidarReshaper {

    private LidarConfiguration configuration;


    public LidarReshaper(LidarConfiguration configuration) {
        this.configuration = configuration;
    }

    public void reshape(Path downloadFilePath, Path reshapeFilePath, String fileName) {
        try {
            String[] command = buildCommand(downloadFilePath, reshapeFilePath);

            LidarApp.log(String.format("Starting reshaping file: %s", fileName));

            ProcessBuilder pb = new ProcessBuilder(command);
            Process proc = pb.start();
            proc.waitFor();

            if(proc.exitValue() != 0){
                LidarApp.printProcessOutput(proc);
            } else {
                LidarApp.log(String.format("Finished reshaping file: %s", fileName));
                Files.deleteIfExists(downloadFilePath);
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    public Path getReshapedFilePath(String fileName) {
        return Paths.get(configuration.outputFolder, "th20-" + fileName);
    }

    private String[] buildCommand(Path downloadFilePath,  Path reshapeFilePath) {
        return new String[] {
                "C:\\PointCloud\\Programs\\LAStools\\bin\\lasmerge.exe",
                "-thin_with_grid", "2",
                "-i", downloadFilePath.toString().replace("/", "\\"),
                "-o", reshapeFilePath.toString().replace("/", "\\")
        };
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