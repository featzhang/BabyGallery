package club.guadazi.babygallery.provider.sync;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Environment;
import android.text.TextUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import club.guadazi.babygallery.R;
import club.guadazi.babygallery.provider.dao.ImageDao;
import club.guadazi.babygallery.provider.entity.ImageEntity;
import club.guadazi.babygallery.util.FileUtils;
import club.guadazi.babygallery.util.ImageTool;
import club.guadazi.babygallery.util.RandomStringUtils;

public class ImageLocalManager {

    public static String getImageFilePathInDB(Context context, long imageFileNameId) {
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            String imageName = new ImageDao(context).findImageName(imageFileNameId);
            if (TextUtils.isEmpty(imageName)) {
                return null;
            }
            return getLocalImageFilePath(context, imageName);
        }

        return null;
    }

    public static String getLocalImageFilePath(Context context, String imageName) {
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            if (TextUtils.isEmpty(imageName)) {
                return null;
            }
            File sdCardDir = Environment.getExternalStorageDirectory();
            String appName = context.getResources().getString(R.string.app_name);
            String path = "";
            path += sdCardDir.getPath() + File.separator;
            path += "" + appName + File.separator;
            path += ".tmp" + File.separator;
            path += imageName;
            return path;
        }

        return null;
    }

    /**
     * // copy image file to
     * // add image info to db
     * // return index of db
     *
     * @param context
     * @param path
     * @return
     */
    public static long addNewImage(Context context, String path) {
        if (TextUtils.isEmpty(path)) {
            return -1;
        }
        String randomFileNameString = RandomStringUtils.getRandomString2(16);
        String fileNameProfix = path.substring(path.indexOf("."), path.length());
        String localFileName = randomFileNameString + fileNameProfix;
        String localImageFilePath = getLocalImageFilePath(context, localFileName);
        FileUtils.copyFile(path, localImageFilePath);
        ImageEntity imageEntity = new ImageEntity();
        imageEntity.setImageLocalName(localFileName);
        return new ImageDao(context).add(imageEntity);
    }

    public static List<Long> addNewImages(Context context, List<String> paths) {
        if (paths == null || paths.size() == 0) {
            return null;
        }
        List<Long> indexs = new ArrayList<Long>();
        for (String path : paths) {
            long index = addNewImage(context, path);
            if (index != -1) {
                indexs.add(index);
            }
        }
        if (indexs.size() == 0) {
            return null;
        }
        return indexs;

    }


    public static Drawable getThumbnailByImageId(Context mContext, long imageId) {
        String imageFilePathInDB = getImageFilePathInDB(mContext, imageId);
        if (imageFilePathInDB == null) {
            return null;
        }
        if (!new File(imageFilePathInDB).exists()) {
            return null;
        }
        return getThumbnailByImagePath(mContext, imageFilePathInDB);
    }

    public static Drawable getThumbnailByImagePath(Context mContext, String imagePath) {

        float dimension = mContext.getResources().getDimension(R.dimen.message_item_image_icon_size);
        Bitmap bitmap = BitmapFactory.decodeFile(imagePath);

        Drawable drawable = ImageTool.resizeImage(bitmap, (int) dimension, (int) dimension);
        return drawable;
    }
}
