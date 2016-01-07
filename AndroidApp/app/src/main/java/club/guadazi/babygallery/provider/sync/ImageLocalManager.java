package club.guadazi.babygallery.provider.sync;

import android.content.Context;
import android.os.Environment;
import android.text.TextUtils;

import java.io.File;

import club.guadazi.babygallery.R;
import club.guadazi.babygallery.provider.dao.ImageDao;

public class ImageLocalManager {
    public static String getImageFilePath(Context context, int imageFileName) {
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            String imageName = new ImageDao(context).findImageName(imageFileName);
            if (TextUtils.isEmpty(imageName)) {
                return null;
            }
            File sdCardDir = Environment.getExternalStorageDirectory();
            String appName = context.getResources().getString(R.string.app_name);
            String path = "";
            path += sdCardDir.getPath() + File.separator;
            path += "." + appName + File.separator;
            path += ".tmp" + File.separator;
            path += imageName;
            File file = new File(path);
            if (file.exists()) {
                return path;
            }
        }

        return null;
    }

}
