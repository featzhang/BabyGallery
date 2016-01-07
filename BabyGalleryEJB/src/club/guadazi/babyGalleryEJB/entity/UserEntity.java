package club.guadazi.babyGalleryEJB.entity;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * Created by Mariaaron on 16/1/4.
 */
@Entity
@Table(name = "t_user", schema = "", catalog = "BabyGallery")
public class UserEntity {
    private int id;
    private String name;
    private int photoCount;
    private String email;
    private String babyName;
    private Timestamp babyBirthday;

    @Id
    @Column(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "photo_count")
    public int getPhotoCount() {
        return photoCount;
    }

    public void setPhotoCount(int photoCount) {
        this.photoCount = photoCount;
    }

    @Basic
    @Column(name = "email")
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Basic
    @Column(name = "baby_name")
    public String getBabyName() {
        return babyName;
    }

    public void setBabyName(String babyName) {
        this.babyName = babyName;
    }

    @Basic
    @Column(name = "baby_birthday")
    public Timestamp getBabyBirthday() {
        return babyBirthday;
    }

    public void setBabyBirthday(Timestamp babyBirthday) {
        this.babyBirthday = babyBirthday;
    }

    @Override
    public String toString() {
        return "UserEntity{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", photoCount=" + photoCount +
                ", email='" + email + '\'' +
                ", babyName='" + babyName + '\'' +
                ", babyBirthday=" + babyBirthday +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserEntity that = (UserEntity) o;

        if (id != that.id) return false;
        if (photoCount != that.photoCount) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (email != null ? !email.equals(that.email) : that.email != null) return false;
        if (babyName != null ? !babyName.equals(that.babyName) : that.babyName != null) return false;
        if (babyBirthday != null ? !babyBirthday.equals(that.babyBirthday) : that.babyBirthday != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + photoCount;
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (babyName != null ? babyName.hashCode() : 0);
        result = 31 * result + (babyBirthday != null ? babyBirthday.hashCode() : 0);
        return result;
    }
}
