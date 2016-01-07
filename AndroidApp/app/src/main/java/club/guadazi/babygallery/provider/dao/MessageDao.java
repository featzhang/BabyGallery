package club.guadazi.babygallery.provider.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import club.guadazi.babygallery.entity.MessageData;

public class MessageDao {
    private Context mContext;
    private final SQLiteDatabase database;

    public MessageDao(Context context) {
        this.mContext = context;
        BabyGallerySqliteOpenHelper openHelper = new BabyGallerySqliteOpenHelper(mContext);
        database =
                openHelper.getWritableDatabase();
    }

    public List<MessageData> listAllMessageByUserId(int userId) {
        Cursor cursor = database.query(MessageData.TABLE_NAME,
                new String[]{MessageData.TABLE_COLUMN_ID,
                        MessageData.TABLE_COLUMN_CONTENT,
                        MessageData.TABLE_COLUMN_IMAGE_IDS,
                        MessageData.TABLE_COLUMN_IMAGE_TAG,
                        MessageData.TABLE_COLUMN_MARK_POINT,
                        MessageData.TABLE_COLUMN_UPDATE_TIME,
                        MessageData.TABLE_COLUMN_USER_ID}, MessageData.TABLE_COLUMN_USER_ID + "=?", new String[]{userId + ""}, null, null, null);
        List<MessageData> messageDatas = new ArrayList<>();
        while (cursor.moveToNext()) {
            MessageData messageData = new MessageData();
            int id = cursor.getInt(cursor.getColumnIndex(MessageData.TABLE_COLUMN_ID));
            String content = cursor.getString(cursor.getColumnIndex(MessageData.TABLE_COLUMN_CONTENT));
            String imageIds = cursor.getString(cursor.getColumnIndex(MessageData.TABLE_COLUMN_IMAGE_IDS));
            String tag = cursor.getString(cursor.getColumnIndex(MessageData.TABLE_COLUMN_IMAGE_TAG));
            String markPointString = cursor.getString(cursor.getColumnIndex(MessageData.TABLE_COLUMN_MARK_POINT));
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            Date markPoint;
            try {
                markPoint = format.parse(markPointString);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            String updateTime = cursor.getString(cursor.getColumnIndex(MessageData.TABLE_COLUMN_UPDATE_TIME));
            try {
                Date date=format.parse(updateTime);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return messageDatas;
    }
}
