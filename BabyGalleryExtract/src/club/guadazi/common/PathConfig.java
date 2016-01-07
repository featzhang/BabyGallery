package club.guadazi.common;

import java.io.File;

public class PathConfig {
    public static String getImagesRootPath() {
        return "~/documents/babyGallery/images";
    }

    public static String getImageby(String nameId, String id) {
        return getImagesRootPath() + File.separator + nameId + File.separator + id;
    }
}
