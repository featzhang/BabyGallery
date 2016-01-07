package club.guadazi.babyGalleryEJB.entity;

import javax.persistence.*;

/**
 * Created by Mariaaron on 16/1/4.
 */
@Entity
@Table(name = "t_comment", schema = "", catalog = "BabyGallery")
public class CommentEntity {
    private int id;
    private String content;
    private String imageId;
    private Integer viewId;

    @Id
    @Column(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
    @Column(name = "image_id")
    public String getImageId() {
        return imageId;
    }

    public void setImageId(String imageId) {
        this.imageId = imageId;
    }

    @Basic
    @Column(name = "view_id")
    public Integer getViewId() {
        return viewId;
    }

    public void setViewId(Integer viewId) {
        this.viewId = viewId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CommentEntity that = (CommentEntity) o;

        if (id != that.id) return false;
        if (content != null ? !content.equals(that.content) : that.content != null) return false;
        if (imageId != null ? !imageId.equals(that.imageId) : that.imageId != null) return false;
        if (viewId != null ? !viewId.equals(that.viewId) : that.viewId != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (content != null ? content.hashCode() : 0);
        result = 31 * result + (imageId != null ? imageId.hashCode() : 0);
        result = 31 * result + (viewId != null ? viewId.hashCode() : 0);
        return result;
    }
}
