package club.guadazi.babyGalleryEJB.ifc;

import club.guadazi.babyGalleryEJB.entity.UserEntity;

import java.io.Serializable;
import java.sql.Timestamp;

public class UserData implements Serializable {
    private int id;
    private String name;
    private int phoneCount;
    private String email;
    private String babyName;
    private Timestamp babyBirthday;

    public UserData(UserEntity singleResult) {
        if (singleResult != null) {
            id = singleResult.getId();
            name = singleResult.getName();
            phoneCount = singleResult.getPhotoCount();
            email = singleResult.getEmail();
            babyName = singleResult.getBabyName();
            babyBirthday = singleResult.getBabyBirthday();
        }
    }

    public UserData() {
    }

    public int getPhoneCount() {
        return phoneCount;
    }

    public void setPhoneCount(int phoneCount) {
        this.phoneCount = phoneCount;
    }

    public Timestamp getBabyBirthday() {
        return babyBirthday;
    }

    public void setBabyBirthday(Timestamp babyBirthday) {
        this.babyBirthday = babyBirthday;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getBabyName() {
        return babyName;
    }

    public void setBabyName(String babyName) {
        this.babyName = babyName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public UserEntity toEntity() {
        UserEntity userEntity = new UserEntity();
        userEntity.setId(id);
        userEntity.setBabyBirthday(babyBirthday);
        userEntity.setBabyName(babyName);
        userEntity.setEmail(email);
        userEntity.setName(name);
        userEntity.setPhotoCount(phoneCount);
        return userEntity;
    }

    @Override
    public String toString() {
        return "UserData{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", phoneCount=" + phoneCount +
                ", email='" + email + '\'' +
                ", babyName='" + babyName + '\'' +
                ", babyBirthday=" + babyBirthday +
                '}';
    }
}
