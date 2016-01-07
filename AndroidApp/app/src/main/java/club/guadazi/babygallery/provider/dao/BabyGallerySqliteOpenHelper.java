package club.guadazi.babygallery.provider.dao;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

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
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
