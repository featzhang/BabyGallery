package club.guadazi.babygallery.provider.sync;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;

import java.io.File;

import club.guadazi.babygallery.R;
import club.guadazi.babygallery.provider.dao.ImageDao;
import club.guadazi.babygallery.util.ImageTool;

public class ImageManager {

    private static final String TAG = "ImageManager";

    public static String getImagePathByRemoteId(Context context, long imageFileNameId) {
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            String imageName = new ImageDao(context).findImageNameByRemoteId(imageFileNameId);
            Log.d("ImageManager", "" + imageName);
            if (TextUtils.isEmpty(imageName)) {
                return null;
            }
            return getImagePathByImageName(context, imageName);
        }

        return null;
    }

    public static String getImagePathByImageName(Context context, String imageName) {
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            if (TextUtils.isEmpty(imageName)) {
                return null;
            }
            File sdCardDir = Environment.getExternalStorageDirectory();
            String appName = context.getResources().getString(R.string.app_name);
            String path = "";
            path += sdCardDir.getPath() + File.separator;
            path += "" + appName + File.separator;
            path += "tmp" + File.separator;
            path += imageName;
            return path;
        }

        return null;
    }

    private static String getThumbnailNameByImageName(String imageName) {
        if (imageName == null) {
            return null;
        }
        int pointIndex = imageName.lastIndexOf(".");
        String s1 = imageName.substring(0, pointIndex);
        String s2 = imageName.substring(pointIndex);
        return s1 + "_nic" + s2;
    }

    public static String getThumbnailPathByImageName(Context context, String imageName) {
        imageName = getThumbnailNameByImageName(imageName);
        return getImagePathByImageName(context, imageName);
    }

    public static Drawable getThumbnailByRemoteId(Context mContext, long imageId) {
        Log.d(TAG, "image id: " + imageId);
        String imageFilePathInDB = getImagePathByRemoteId(mContext, imageId);
        Log.d(TAG, "fileName: " + imageFilePathInDB);
        if (imageFilePathInDB == null) {
            return null;
        }
        if (!new File(imageFilePathInDB).exists()) {
            return null;
        }
        imageFilePathInDB = getThumbnailPathByImageName(mContext, imageFilePathInDB);
        return getThumbnailByThumbnailPath(mContext, imageFilePathInDB);
    }

    public static Drawable getThumbnailByThumbnailPath(Context mContext, String imagePath) {

        float dimension = mContext.getResources().getDimension(R.dimen.message_item_image_icon_size);
        Bitmap bitmap = BitmapFactory.decodeFile(imagePath);

        Drawable drawable = ImageTool.resizeImage(bitmap, (int) dimension, (int) dimension);
        return drawable;
    }

    public static String getThumbnailPathByRemoteId(Context mContext, long imageId) {
        String imageName = new ImageDao(mContext).findImageNameByRemoteId(imageId);
        String imagePath = getImagePathByImageName(mContext, imageName);
        String thumbnailPath = getThumbnailNameByImageName(imagePath);
        return thumbnailPath;
    }
}
