package club.guadazi.babyGalleryEJB.entity;

import javax.persistence.*;

/**
 * Created by aaron on 16-1-11.
 */
@Entity
@Table(name = "t_image", schema = "", catalog = "BabyGallery")
public class ImageEntity {
    private int id;
    private String imageName;

    @Id
    @Column(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "image_name")
    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ImageEntity that = (ImageEntity) o;

        if (id != that.id) return false;
        if (imageName != null ? !imageName.equals(that.imageName) : that.imageName != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (imageName != null ? imageName.hashCode() : 0);
        return result;
    }
}
