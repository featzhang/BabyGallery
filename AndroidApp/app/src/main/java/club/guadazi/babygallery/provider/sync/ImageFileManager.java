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

public class ImageFileManager {

    private static final String TAG = "ImageFileManager";

    /**
     * @param context
     * @param imageFileNameId image file remote id
     * @return
     */
    public static String getImageFilePath(Context context, long imageFileNameId) {
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            String imageName = new ImageDao(context).findImageNameByRemoteId(imageFileNameId);
            Log.d(TAG, "" + imageName);
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

    public static String getThumbnailFilePath(Context mContext, String imageName) {
        String imagePath = getImagePathByImageName(mContext, imageName);
        String thumbnailPath = getThumbnailNameByImageName(imagePath);
        return thumbnailPath;
    }

    public static String getThumbnailFilePath(Context mContext, long imageId) {
        String imageName = new ImageDao(mContext).findImageNameByRemoteId(imageId);
        String thumbnailFilePath = getThumbnailFilePath(mContext, imageName);
        return thumbnailFilePath;
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


    public static Drawable getThumbnailDrawable(Context mContext, long imageId) {
        String imageFilePathInDB = getImageFilePath(mContext, imageId);
        if (imageFilePathInDB == null) {
            return null;
        }
        Log.d(TAG, "image id: " + imageId + " fileName: " + imageFilePathInDB);
        imageFilePathInDB = getThumbnailNameByImageName(imageFilePathInDB);
        if (!new File(imageFilePathInDB).exists()) {
            Log.d(TAG, "file not exist!");
            return null;
        }
        return getThumbnailByThumbnailPath(mContext, imageFilePathInDB);
    }

    public static Drawable getThumbnailByThumbnailPath(Context mContext, String imagePath) {

        float dimension = mContext.getResources().getDimension(R.dimen.message_item_image_icon_size);
        Bitmap bitmap = BitmapFactory.decodeFile(imagePath);

        Drawable drawable = ImageTool.resizeImage(bitmap, (int) dimension, (int) dimension);
        return drawable;
    }

}
