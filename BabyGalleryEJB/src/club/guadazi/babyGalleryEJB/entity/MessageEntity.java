package club.guadazi.babyGalleryEJB.entity;

import org.apache.struts2.json.annotations.JSON;

import javax.persistence.*;
import java.sql.Timestamp;


@Entity
@Table(name = "t_message", schema = "", catalog = "BabyGallery")
public class MessageEntity {
    private int id;
    private Integer userId;
    private String tag;
    private String imageIds;
    private Timestamp updateTime;
    private String title;
    private String content;
    private Timestamp markPoint;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    @Basic
    @Column(name = "user_id")
    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    @Basic
    @Column(name = "tag")
    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }


    @Basic
    @Column(name = "image_ids")
    public String getImageIds() {
        return imageIds;
    }

    public void setImageIds(String imageIds) {
        this.imageIds = imageIds;
    }

    @Basic
    @JSON(format="yyyy-MM-dd HH:mm:ss")
    @Column(name = "update_time")
    public Timestamp getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Timestamp updateTime) {
        this.updateTime = updateTime;
    }

    @Basic
    @Column(name = "title")
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Basic
    @Column(name = "content")
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Basic
    @JSON(format="yyyy-MM-dd HH:mm:ss")
    @Column(name = "mark_point")
    public Timestamp getMarkPoint() {
        return markPoint;
    }

    public void setMarkPoint(Timestamp markPoint) {
        this.markPoint = markPoint;
    }
}
