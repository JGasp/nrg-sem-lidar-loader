package si.fri.nrg.sem.app.models.files.base;

public abstract class LoadFiles {

    // http://gis.arso.gov.si/lidar/gkot/laz/b_35/D48GK/

    public static String URL_TEMPLATE_XY = "http://gis.arso.gov.si/lidar/gkot/laz/%s/D48GK/GK_%d_%d.laz";
    public static String URL_TEMPLATE_NAME = "http://gis.arso.gov.si/lidar/gkot/laz/%s/D48GK/GK_%s.laz";

    public String getFileName(String block, int fileX, int fileY){
        return String.format(URL_TEMPLATE_XY, block, fileX, fileY);
    }

    public String getFileName(String block, String file){
        return String.format(URL_TEMPLATE_NAME, block, file);
    }


    public abstract String getNextFileUrl();
}
