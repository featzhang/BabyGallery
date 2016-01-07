package club.guadazi.babygallery.provider.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import club.guadazi.babygallery.provider.entity.UserData;

/**
 * Created by Mariaaron on 16/1/6.
 */
public class UserDao {
    private final Context mContext;
    private final BabyGallerySqliteOpenHelper openHelper;
    private final SQLiteDatabase database;

    public UserDao(Context context) {
        this.mContext = context;
        openHelper = new BabyGallerySqliteOpenHelper(context);
        database = openHelper.getWritableDatabase();
    }

    public int getUserId() {
        UserData userData = getUserData();
        return userData == null ? -1 : userData.getId();
    }

    public UserData getUserData() {
        Cursor cursor = database.query(UserData.TABLE_NAME, new String[]{UserData.TABLE_COLUMN_ID, UserData.TABLE_COLUMN_EMAIL, UserData.TABLE_COLUMN_PHOTO_COUNT, UserData.TABLE_COLUMN_USER_NAME}, null, null, null, null, null);
        while (cursor.moveToNext()) {
            UserData userData = new UserData();

            int id = cursor.getInt(cursor.getColumnIndex(UserData.TABLE_COLUMN_ID));
            int photoCount = cursor.getInt(cursor.getColumnIndex(UserData.TABLE_COLUMN_PHOTO_COUNT));
            String email = cursor.getString(cursor.getColumnIndex(UserData.TABLE_COLUMN_EMAIL));
            String userName = cursor.getString(cursor.getColumnIndex(UserData.TABLE_COLUMN_USER_NAME));

            userData.setId(id);
            userData.setEmail(email);
            userData.setName(userName);
            userData.setPhotoCount(photoCount);
            return userData;
        }
        return null;
    }
}
