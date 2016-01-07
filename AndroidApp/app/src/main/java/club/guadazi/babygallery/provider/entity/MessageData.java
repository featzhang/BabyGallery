package club.guadazi.babygallery.provider.entity;


import java.io.Serializable;
import java.sql.Timestamp;

public class MessageData implements Serializable {
    private int id;
    private Integer userId;
    private String tag;
    private String imageIds;
    private Timestamp updateTime;
    //    private String title;
    private String content;
    private Timestamp markPoint;
    public static final String CREATE_SQL = "CREATE TABLE " + MessageData.TABLE_NAME +
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
    public static final String TABLE_NAME = "t_message";
    public static final String TABLE_COLUMN_ID = "id";
    public static final String TABLE_COLUMN_USER_ID = "user_id";
    public static final String TABLE_COLUMN_IMAGE_IDS = "image_ids";
    public static final String TABLE_COLUMN_IMAGE_TAG = "tag";
    public static final String TABLE_COLUMN_UPDATE_TIME = "update_time";
    public static final String TABLE_COLUMN_CONTENT = "content";
    public static final String TABLE_COLUMN_MARK_POINT = "mark_point";


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
                ", userId=" + userId +
                ", tag='" + tag + '\'' +
                ", imageIds='" + imageIds + '\'' +
                ", updateTime=" + updateTime +
                ", content='" + content + '\'' +
                ", markPoint=" + markPoint +
                '}';
    }

    public Timestamp getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Timestamp updateTime) {
        this.updateTime = updateTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MessageData that = (MessageData) o;

        if (id != that.id) return false;
        if (userId != null ? !userId.equals(that.userId) : that.userId != null) return false;
        if (tag != null ? !tag.equals(that.tag) : that.tag != null) return false;
        if (imageIds != null ? !imageIds.equals(that.imageIds) : that.imageIds != null)
            return false;
        if (updateTime != null ? !updateTime.equals(that.updateTime) : that.updateTime != null)
            return false;
        if (content != null ? !content.equals(that.content) : that.content != null) return false;
        if (markPoint != null ? !markPoint.equals(that.markPoint) : that.markPoint != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (userId != null ? userId.hashCode() : 0);
        result = 31 * result + (tag != null ? tag.hashCode() : 0);
        result = 31 * result + (imageIds != null ? imageIds.hashCode() : 0);
        result = 31 * result + (updateTime != null ? updateTime.hashCode() : 0);
        result = 31 * result + (content != null ? content.hashCode() : 0);
        result = 31 * result + (markPoint != null ? markPoint.hashCode() : 0);
        return result;
    }
}
