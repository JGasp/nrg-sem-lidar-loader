package si.fri.nrg.sem.app.loader;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import si.fri.nrg.sem.app.LidarApp;
import si.fri.nrg.sem.app.models.LidarConfiguration;
import si.fri.nrg.sem.app.reshaper.LidarReshaper;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class LidarLoader {

    private LidarConfiguration configuration;
    private LidarReshaper lidarReshaper;


    public LidarLoader(LidarConfiguration configuration) {
        this.configuration = configuration;
    }

    public LidarReshaper getLidarReshaper() {
        return lidarReshaper;
    }

    public void setLidarReshaper(LidarReshaper lidarReshaper) {
        this.lidarReshaper = lidarReshaper;
    }

    private void processFile(String url) {

        String fileName = url.substring(url.lastIndexOf("/") + 1);

        Path downloadFilePath = Paths.get(configuration.outputFolder, fileName);
        Path reshapedFilePath = lidarReshaper == null ? null :
                lidarReshaper.getReshapedFilePath(fileName);

        if (reshapedFilePath == null || !Files.exists(reshapedFilePath)) {
            if (Files.exists(downloadFilePath)) {
                LidarApp.log("Using local file");
            } else {
                downloadFile(downloadFilePath, fileName, url);
            }
            if (reshapedFilePath != null) {
                lidarReshaper.reshape(downloadFilePath, reshapedFilePath, fileName);
            }
        } else {
            LidarApp.log("Reshaped local file exists");
        }
    }

    private void downloadFile(Path filePath, String fileName, String url) {
        CloseableHttpClient client = HttpClients.createDefault();
        try {
            LidarApp.log(String.format("Started downloading: %s", fileName));

            CloseableHttpResponse response = client.execute(new HttpGet(url));
            HttpEntity entity = response.getEntity();
            if (response.getStatusLine().getStatusCode() == 200 && entity != null) {
                FileOutputStream out = new FileOutputStream(filePath.toString());
                entity.writeTo(out);

                out.flush();
                out.close();

                LidarApp.log(String.format("Finished downloading: %s", fileName));
            } else {
                LidarApp.log(String.format("Failed to download: %s", fileName));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void load() {
        if(configuration.loadFiles != null){

            String url;
            while((url = configuration.loadFiles.getNextFileUrl()) != null){
                processFile(url);
            }
        } else {
            LidarApp.log("No files specified");
        }
    }

}
