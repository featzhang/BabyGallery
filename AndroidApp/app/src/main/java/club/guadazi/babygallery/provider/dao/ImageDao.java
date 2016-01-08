package club.guadazi.babygallery.provider.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

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
        Cursor cursor = database.query(ImageEntity.TABLE_NAME, new String[]{ImageEntity.TABLE_COLUMN_ID, ImageEntity.TABLE_COLUMN_IMAGE_ID, ImageEntity.TABLE_COLUMN_IMAGE_LOCAL_NAME}, ImageEntity.TABLE_COLUMN_IMAGE_ID + "=?", new String[]{imageId + ""}, null, null, null);
        while (cursor.moveToNext()) {
            String string = cursor.getString(cursor.getColumnIndex(ImageEntity.TABLE_COLUMN_IMAGE_LOCAL_NAME));
            return string;
        }
        return null;
    }

    public long add(ImageEntity imageEntity) {
        ContentValues contentValues = new ContentValues();
//        contentValues.put(ImageEntity.TABLE_COLUMN_ID, imageEntity.getRemoteId());
        contentValues.put(ImageEntity.TABLE_COLUMN_IMAGE_ID, imageEntity.getRemoteImageId());
        contentValues.put(ImageEntity.TABLE_COLUMN_IMAGE_LOCAL_NAME, imageEntity.getImageLocalName());
        long insert = database.insert(ImageEntity.TABLE_NAME, null, contentValues);
        return insert;
    }
}
