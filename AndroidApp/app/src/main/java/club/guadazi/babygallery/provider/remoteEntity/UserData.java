package club.guadazi.babygallery.provider.remoteEntity;


import java.io.Serializable;
import java.sql.Timestamp;

public class UserData implements Serializable {
    //    "CREATE TABLE t_user (\n"+
//            "  id integer PRIMARY KEY AUTOINCREMENT NOT NULL,\n"+
//            "  user_name char(128),\n"+
//            "  email char(128),\n"+
//            "  photo_count integer\n"+
//            ");";
    public static String TABLE_NAME = "t_user";
    public static String TABLE_COLUMN_USER_NAME = "user_name";
    public static String TABLE_COLUMN_ID = "id";
    public static String TABLE_COLUMN_EMAIL = "email";
    public static String TABLE_COLUMN_PHOTO_COUNT = "photo_count";
    public static final String CREATE_SQL = "CREATE TABLE t_user (\n" +
            "  id integer PRIMARY KEY AUTOINCREMENT NOT NULL,\n" +
            "  user_name char(128),\n" +
            "  email char(128),\n" +
            "  photo_count integer\n" +
            ");";

    private int id;
    private String name;
    private int photoCount;
    private String email;
    private String babyName;
    private Timestamp babyBirthday;


    public UserData() {
    }

    public int getPhotoCount() {
        return photoCount;
    }

    public void setPhotoCount(int photoCount) {
        this.photoCount = photoCount;
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


    @Override
    public String toString() {
        return "UserData{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", photoCount=" + photoCount +
                ", email='" + email + '\'' +
                ", babyName='" + babyName + '\'' +
                ", babyBirthday=" + babyBirthday +
                '}';
    }
}
