package club.guadazi.babygallery.provider.entity;

import android.os.Parcel;
import android.os.Parcelable;

import java.sql.Timestamp;

import club.guadazi.babygallery.provider.remoteEntity.RemoteMessageEntity;


public class MessageData implements Parcelable {

    private int id;
    private int remoteId;
    private Integer userId;
    private String tag;
    private String imageIds;
    private Timestamp updateTime;
    private String content;
    private Timestamp markPoint;
    public static final String CREATE_SQL = "CREATE TABLE " + MessageData.TABLE_NAME +
            " (\n" +
            "  " + MessageData.TABLE_COLUMN_ID + " integer PRIMARY KEY  NOT NULL,\n" +
            "  " + MessageData.TABLE_COLUMN_REMOTE_ID + " integer ,\n" +
            "  " + MessageData.TABLE_COLUMN_USER_ID + " integer,\n" +
            "  " + MessageData.TABLE_COLUMN_IMAGE_IDS + " char(100),\n" +
            "  " + MessageData.TABLE_COLUMN_IMAGE_TAG + " char(10),\n" +
            "  " + MessageData.TABLE_COLUMN_UPDATE_TIME + " datetime,\n" +
            "  " + MessageData.TABLE_COLUMN_MARK_POINT + " datetime,\n" +
            "  " + MessageData.TABLE_COLUMN_CONTENT + " char(500)\n" +
            ");";
    public static final String TABLE_NAME = "t_message";
    public static final String TABLE_COLUMN_ID = "id";
    public static final String TABLE_COLUMN_REMOTE_ID = "remote_Id";
    public static final String TABLE_COLUMN_USER_ID = "user_id";
    public static final String TABLE_COLUMN_IMAGE_IDS = "image_ids";
    public static final String TABLE_COLUMN_IMAGE_TAG = "tag";
    public static final String TABLE_COLUMN_UPDATE_TIME = "update_time";
    public static final String TABLE_COLUMN_CONTENT = "content";
    public static final String TABLE_COLUMN_MARK_POINT = "mark_point";
    public static final String IMAGE_ID_SEPERATER = ":::";

    public MessageData() {
    }

    public MessageData(RemoteMessageEntity remoteData) {
        remoteId = remoteData.getId();
        userId = remoteData.getUserId();
        tag = remoteData.getTag();
        imageIds = remoteData.getImageIds();
        updateTime = remoteData.getUploadDate();
        content = remoteData.getContent();
        markPoint = remoteData.getMarkPoint();
    }

    protected MessageData(Parcel in) {
        id = in.readInt();
        remoteId = in.readInt();
        tag = in.readString();
        imageIds = in.readString();
        content = in.readString();
    }

    public static final Creator<MessageData> CREATOR = new Creator<MessageData>() {
        @Override
        public MessageData createFromParcel(Parcel in) {
            return new MessageData(in);
        }

        @Override
        public MessageData[] newArray(int size) {
            return new MessageData[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getRemoteId() {
        return remoteId;
    }

    public void setRemoteId(int remoteId) {
        this.remoteId = remoteId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getImageIds() {
        return imageIds;
    }

    public void setImageIds(String imageIds) {
        this.imageIds = imageIds;
    }

    public Timestamp getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Timestamp updateTime) {
        this.updateTime = updateTime;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Timestamp getMarkPoint() {
        return markPoint;
    }

    public void setMarkPoint(Timestamp markPoint) {
        this.markPoint = markPoint;
    }

    @Override
    public String toString() {
        return "MessageData{" +
                "id=" + id +
                ", remoteId=" + remoteId +
                ", userId=" + userId +
                ", tag='" + tag + '\'' +
                ", imageIds='" + imageIds + '\'' +
                ", updateTime=" + updateTime +
                ", content='" + content + '\'' +
                ", markPoint=" + markPoint +
                '}';
    }

    public RemoteMessageEntity toRemoteEntity() {
        RemoteMessageEntity remoteMessageEntity = new RemoteMessageEntity();
        remoteMessageEntity.setContent(content);
        remoteMessageEntity.setId(remoteId);
        remoteMessageEntity.setImageIds(imageIds);
        remoteMessageEntity.setMarkPoint(markPoint);
        remoteMessageEntity.setTag(tag);
        remoteMessageEntity.setUploadDate(updateTime);
        remoteMessageEntity.setUserId(userId);
        return remoteMessageEntity;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeInt(remoteId);
        dest.writeString(tag);
        dest.writeString(imageIds);
        dest.writeString(content);
    }
}
