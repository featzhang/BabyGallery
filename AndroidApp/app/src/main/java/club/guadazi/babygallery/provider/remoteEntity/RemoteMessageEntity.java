package club.guadazi.babygallery.provider.remoteEntity;

import java.sql.Timestamp;

public class RemoteMessageEntity {

    private int id;
    private Integer userId;
    private String tag;
    private String imageIds;
    private Timestamp uploadDate;
    private String title;
    private String content;
    private Timestamp markPoint;


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


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public Timestamp getUploadDate() {
        return uploadDate;
    }

    public void setUploadDate(Timestamp uploadDate) {
        this.uploadDate = uploadDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RemoteMessageEntity that = (RemoteMessageEntity) o;

        if (id != that.id) return false;
        if (userId != null ? !userId.equals(that.userId) : that.userId != null) return false;
        if (tag != null ? !tag.equals(that.tag) : that.tag != null) return false;
        if (imageIds != null ? !imageIds.equals(that.imageIds) : that.imageIds != null)
            return false;
        if (uploadDate != null ? !uploadDate.equals(that.uploadDate) : that.uploadDate != null)
            return false;
        if (title != null ? !title.equals(that.title) : that.title != null) return false;
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
        result = 31 * result + (uploadDate != null ? uploadDate.hashCode() : 0);
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + (content != null ? content.hashCode() : 0);
        result = 31 * result + (markPoint != null ? markPoint.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "RemoteMessageEntity{" +
                "id=" + id +
                ", userId=" + userId +
                ", tag='" + tag + '\'' +
                ", imageIds='" + imageIds + '\'' +
                ", uploadDate=" + uploadDate +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", markPoint=" + markPoint +
                '}';
    }
}
