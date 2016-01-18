package club.guadazi.babygallery.provider.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import club.guadazi.babygallery.provider.entity.ImageEntity;
import club.guadazi.babygallery.provider.entity.MessageData;
import club.guadazi.babygallery.provider.remoteEntity.UserData;


public class BabyGallerySqliteOpenHelper extends SQLiteOpenHelper {
    private static String databaseName = "babyG.db";
    private static int lastestVersion = 1;

    private BabyGallerySqliteOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    private BabyGallerySqliteOpenHelper(Context context) {
        super(context, databaseName, null, lastestVersion);
    }

    private static BabyGallerySqliteOpenHelper instance = null;

    public static BabyGallerySqliteOpenHelper getInstance(Context context) {
        if (instance == null) {
            instance = new BabyGallerySqliteOpenHelper(context);
        }
        return instance;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(UserData.CREATE_SQL);
        sqLiteDatabase.execSQL(MessageData.CREATE_SQL);
        sqLiteDatabase.execSQL(ImageEntity.CREATE_SQL);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
