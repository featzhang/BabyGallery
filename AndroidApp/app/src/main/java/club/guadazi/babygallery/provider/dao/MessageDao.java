package club.guadazi.babygallery.provider.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import club.guadazi.babygallery.provider.entity.MessageData;

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
                        MessageData.TABLE_COLUMN_REMOTE_ID,
                        MessageData.TABLE_COLUMN_CONTENT,
                        MessageData.TABLE_COLUMN_IMAGE_IDS,
                        MessageData.TABLE_COLUMN_IMAGE_TAG,
                        MessageData.TABLE_COLUMN_MARK_POINT,
                        MessageData.TABLE_COLUMN_UPDATE_TIME,
                        MessageData.TABLE_COLUMN_USER_ID}, MessageData.TABLE_COLUMN_USER_ID + "=?", new String[]{userId + ""}, null, null, null);
        List<MessageData> messageDatas = new ArrayList<MessageData>();
        while (cursor.moveToNext()) {
            MessageData messageData = new MessageData();
            int id = cursor.getInt(cursor.getColumnIndex(MessageData.TABLE_COLUMN_ID));
            String content = cursor.getString(cursor.getColumnIndex(MessageData.TABLE_COLUMN_CONTENT));
            int remoteId = cursor.getInt(cursor.getColumnIndex(MessageData.TABLE_COLUMN_REMOTE_ID));
            String imageIds = cursor.getString(cursor.getColumnIndex(MessageData.TABLE_COLUMN_IMAGE_IDS));
            String tag = cursor.getString(cursor.getColumnIndex(MessageData.TABLE_COLUMN_IMAGE_TAG));
            String markPointString = cursor.getString(cursor.getColumnIndex(MessageData.TABLE_COLUMN_MARK_POINT));
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");


            messageData.setId(id);
            messageData.setContent(content);
            messageData.setImageIds(imageIds);
            messageData.setRemoteId(remoteId);
            messageData.setTag(tag);

            try {
                Date markPoint;
                if (!TextUtils.isEmpty(markPointString) && !markPointString.equals("null")) {
                    markPoint = format.parse(markPointString);
                    messageData.setMarkPoint(new Timestamp(markPoint.getTime()));
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
            String updateTimeString = cursor.getString(cursor.getColumnIndex(MessageData.TABLE_COLUMN_UPDATE_TIME));
            try {
                if (!TextUtils.isEmpty(updateTimeString) && !updateTimeString.equals("null")) {
                    Date updateTime = format.parse(updateTimeString);
                    messageData.setUpdateTime(new Timestamp(updateTime.getTime()));
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
            messageDatas.add(messageData);
        }
        if (messageDatas.size() == 0) {
            return null;
        }
        return messageDatas;
    }

    public MessageData findByMessageRemoteId(int messageId) {
        Cursor cursor = database.query(MessageData.TABLE_NAME,
                new String[]{MessageData.TABLE_COLUMN_ID,
                        MessageData.TABLE_COLUMN_CONTENT,
                        MessageData.TABLE_COLUMN_IMAGE_IDS,
                        MessageData.TABLE_COLUMN_IMAGE_TAG,
                        MessageData.TABLE_COLUMN_MARK_POINT,
                        MessageData.TABLE_COLUMN_UPDATE_TIME,
                        MessageData.TABLE_COLUMN_USER_ID}, MessageData.TABLE_COLUMN_REMOTE_ID + "=?", new String[]{messageId + ""}, null, null, null);
        while (cursor.moveToNext()) {
            MessageData messageData = new MessageData();
            int id = cursor.getInt(cursor.getColumnIndex(MessageData.TABLE_COLUMN_ID));
            String content = cursor.getString(cursor.getColumnIndex(MessageData.TABLE_COLUMN_CONTENT));
            String imageIds = cursor.getString(cursor.getColumnIndex(MessageData.TABLE_COLUMN_IMAGE_IDS));
            String tag = cursor.getString(cursor.getColumnIndex(MessageData.TABLE_COLUMN_IMAGE_TAG));
            String markPointString = cursor.getString(cursor.getColumnIndex(MessageData.TABLE_COLUMN_MARK_POINT));
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            messageData.setId(id);
            messageData.setRemoteId(messageId);
            messageData.setContent(content);
            messageData.setImageIds(imageIds);
            messageData.setTag(tag);

            try {
                Date markPoint;
                markPoint = format.parse(markPointString);
                messageData.setMarkPoint(new Timestamp(markPoint.getTime()));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            String updateTimeString = cursor.getString(cursor.getColumnIndex(MessageData.TABLE_COLUMN_UPDATE_TIME));
            try {
                Date updateTime = format.parse(updateTimeString);
                messageData.setUpdateTime(new Timestamp(updateTime.getTime()));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            return messageData;
        }
        return null;
    }

    public int deleteByMessageRemoteId(int messageId) {
        return database.delete(MessageData.TABLE_NAME, MessageData.TABLE_COLUMN_REMOTE_ID + "=?", new String[]{messageId + ""});
    }

    public int deleteByMessageId(int id) {
        return database.delete(MessageData.TABLE_NAME, MessageData.TABLE_COLUMN_ID + "=?", new String[]{id + ""});
    }

    public int updateByLocalId(MessageData messageData) {
        if (messageData == null) {
            return -1;
        }
        ContentValues contentValues = new ContentValues();
        contentValues.put(MessageData.TABLE_COLUMN_ID, messageData.getId());
        contentValues.put(MessageData.TABLE_COLUMN_REMOTE_ID, messageData.getRemoteId());
        contentValues.put(MessageData.TABLE_COLUMN_CONTENT, messageData.getContent());
        contentValues.put(MessageData.TABLE_COLUMN_IMAGE_IDS, messageData.getImageIds());
        contentValues.put(MessageData.TABLE_COLUMN_IMAGE_TAG, messageData.getTag());
        contentValues.put(MessageData.TABLE_COLUMN_MARK_POINT, String.valueOf(messageData.getMarkPoint()));
        contentValues.put(MessageData.TABLE_COLUMN_UPDATE_TIME, String.valueOf(messageData.getUpdateTime()));
        contentValues.put(MessageData.TABLE_COLUMN_USER_ID, messageData.getUserId());
        return database.update(MessageData.TABLE_NAME, contentValues, MessageData.TABLE_COLUMN_ID + "=?", new String[]{messageData.getId() + ""});

    }

    public long add(MessageData messageData) {

        if (messageData == null) {
            return -1;
        }
        ContentValues contentValues = new ContentValues();
        contentValues.put(MessageData.TABLE_COLUMN_REMOTE_ID, messageData.getRemoteId());
        contentValues.put(MessageData.TABLE_COLUMN_CONTENT, messageData.getContent());
        contentValues.put(MessageData.TABLE_COLUMN_IMAGE_IDS, messageData.getImageIds());
        contentValues.put(MessageData.TABLE_COLUMN_IMAGE_TAG, messageData.getTag());
        contentValues.put(MessageData.TABLE_COLUMN_MARK_POINT, String.valueOf(messageData.getMarkPoint()));
        contentValues.put(MessageData.TABLE_COLUMN_UPDATE_TIME, String.valueOf(messageData.getUpdateTime()));
        contentValues.put(MessageData.TABLE_COLUMN_USER_ID, messageData.getUserId());
        return database.insert(MessageData.TABLE_NAME, null, contentValues);
    }

}
