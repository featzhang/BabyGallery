package club.guadazi.babygallery.provider.dao;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import club.guadazi.babygallery.entity.MessageData;

/**
 * Created by Mariaaron on 16/1/6.
 */
public class BabyGallerySqliteOpenHelper extends SQLiteOpenHelper {
    private static String databaseName = "babyG.db";
    private static int lastestVersion = 1;

    public BabyGallerySqliteOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public BabyGallerySqliteOpenHelper(Context context) {
        super(context, databaseName, null, lastestVersion);

    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String sql = "CREATE TABLE t_user (\n" +
                "  id integer PRIMARY KEY AUTOINCREMENT NOT NULL,\n" +
                "  user_name char(128),\n" +
                "  email char(128),\n" +
                "  photo_count integer\n" +
                ");";
        sqLiteDatabase.execSQL(sql);
        String sql2 = "CREATE TABLE " + MessageData.TABLE_NAME +
                " (\n" +
                "  " + MessageData.TABLE_COLUMN_ID +
                " integer PRIMARY KEY  NOT NULL,\n" +
                "  " + MessageData.TABLE_COLUMN_USER_ID + " integer,\n" +
                "  " + MessageData.TABLE_COLUMN_IMAGE_IDS + " char(100),\n" +
                "  " + MessageData.TABLE_COLUMN_IMAGE_TAG + " char(10),\n" +
                "  " + MessageData.TABLE_COLUMN_UPDATE_TIME + " datetime,\n" +
                "  " + MessageData.TABLE_COLUMN_MARK_POINT + " datetime,\n" +
                "  " + MessageData.TABLE_COLUMN_CONTENT + " char(500)\n" +
                ");";
        sqLiteDatabase.execSQL(sql2);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
