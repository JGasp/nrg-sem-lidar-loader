package si.fri.nrg.sem.app.loader;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import si.fri.nrg.sem.app.LidarApp;
import si.fri.nrg.sem.app.files.model.LidarFile;
import si.fri.nrg.sem.app.loader.base.LidarLoader;
import si.fri.nrg.sem.utility.LidarLog;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class OnlineLidarLoader  extends LidarLoader {

    private static String URL_TEMPLATE = "http://gis.arso.gov.si/lidar/gkot/laz/%s/" +
            LIDAR_TYPE + "/" + LIDAR_FILE_NAME_TEMPALTE;


    public OnlineLidarLoader(LidarApp lidarApp) {
        super(lidarApp);
    }


    public String getUrl(LidarFile f){
        return String.format(URL_TEMPLATE, f.getBlock(), f.getX(), f.getY());
    }


    @Override
    public Path prepareFile(LidarFile lidarFile) {
        String fileName = lidarFile.getUniqueFileName();
        String url = getUrl(lidarFile);

        Path filePath = Paths.get(app.options.outputDirectory, fileName);

        try {
            LidarLog.log(String.format("Started downloading: %s", fileName));

            CloseableHttpClient client = HttpClients.createDefault();
            CloseableHttpResponse response = client.execute(new HttpGet(url));
            HttpEntity entity = response.getEntity();
            if (response.getStatusLine().getStatusCode() == 200 && entity != null) {
                FileOutputStream out = new FileOutputStream(filePath.toString());
                entity.writeTo(out);

                out.flush();
                out.close();

                LidarLog.log(String.format("Finished downloading: %s", fileName));

                return filePath;
            } else {
                LidarLog.log(String.format("Failed to download: %s", fileName));
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}
