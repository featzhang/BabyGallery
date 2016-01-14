package club.guadazi.babygallery.provider.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;
import android.util.Log;

import club.guadazi.babygallery.provider.entity.ImageEntity;

public class ImageDao {
    private Context mContext;
    private final SQLiteDatabase database;

    public ImageDao(Context context) {
        this.mContext = context;
        BabyGallerySqliteOpenHelper openHelper = new BabyGallerySqliteOpenHelper(mContext);
        database =
                openHelper.getWritableDatabase();
    }

    public String findImageName(long imageId) {
        Cursor cursor = database.query(ImageEntity.TABLE_NAME, new String[]{ImageEntity.TABLE_COLUMN_ID, ImageEntity.TABLE_COLUMN_REMOTE_IMAGE_ID, ImageEntity.TABLE_COLUMN_IMAGE_LOCAL_NAME}, ImageEntity.TABLE_COLUMN_ID + "=?", new String[]{imageId + ""}, null, null, null);
        while (cursor.moveToNext()) {
            String string = cursor.getString(cursor.getColumnIndex(ImageEntity.TABLE_COLUMN_IMAGE_LOCAL_NAME));
            return string;
        }
        return null;
    }

    public String findImageNameByRemoteId(long remoteId) {
        Cursor cursor = database.query(ImageEntity.TABLE_NAME, new String[]{ImageEntity.TABLE_COLUMN_ID, ImageEntity.TABLE_COLUMN_REMOTE_IMAGE_ID, ImageEntity.TABLE_COLUMN_IMAGE_LOCAL_NAME}, ImageEntity.TABLE_COLUMN_REMOTE_IMAGE_ID + "=?", new String[]{remoteId + ""}, null, null, null);
        while (cursor.moveToNext()) {
            int columnIndex = cursor.getColumnIndex(ImageEntity.TABLE_COLUMN_IMAGE_LOCAL_NAME);
            String string = cursor.getString(columnIndex);
            Log.d("ImageDao", string + "");
            return string;
        }
        return null;
    }

    public long add(ImageEntity imageEntity) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(ImageEntity.TABLE_COLUMN_REMOTE_IMAGE_ID, imageEntity.getRemoteImageId());
        contentValues.put(ImageEntity.TABLE_COLUMN_IMAGE_LOCAL_NAME, imageEntity.getImageLocalName());
        long insert = database.insert(ImageEntity.TABLE_NAME, null, contentValues);
        return insert;
    }

    public long addIfNotExist(ImageEntity imageEntity) {
        int remoteImageId = imageEntity.getRemoteImageId();
        String imageNameByRemoteId = findImageNameByRemoteId(remoteImageId);
        if (TextUtils.isEmpty(imageNameByRemoteId)) {
            return add(imageEntity);
        } else {
            return update(imageEntity);
        }
    }

    private long update(ImageEntity imageEntity) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(ImageEntity.TABLE_COLUMN_REMOTE_IMAGE_ID, imageEntity.getRemoteImageId());
        contentValues.put(ImageEntity.TABLE_COLUMN_IMAGE_LOCAL_NAME, imageEntity.getImageLocalName());
        int update = database.update(ImageEntity.TABLE_NAME, contentValues, ImageEntity.TABLE_COLUMN_REMOTE_IMAGE_ID + "=?", new String[]{imageEntity.getRemoteImageId() + ""});
        return update;
    }
}
