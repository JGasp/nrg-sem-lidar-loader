package si.fri.nrg.sem.app;

import org.kohsuke.args4j.Option;

public class LidarCmdOptions {

    @Option(name="-o", usage="Directory where files will be saved to")
    public String outputDirectory;

    @Option(name="-r", usage="Load range in format <block:xFrom:xTo:yFrom:yTo>")
    public String loadRange;

    @Option(name="-grid-th", usage="Specify thin grid size <1.0>")
    public float gridThinSize = 1.0f;

    @Option(name="-i", usage="Use local file loader, need to specity folder root")
    public String inputDirectory;

}
