package si.fri.nrg.sem.app;

import org.kohsuke.args4j.Option;

public class CmdOptions {

    @Option(name="-d", usage="Where to save files")
    public String outputFolder;

    @Option(name="-r", usage="Load range in format xFrom:xTo:yFrom:yTo")
    public String loadRange;

}