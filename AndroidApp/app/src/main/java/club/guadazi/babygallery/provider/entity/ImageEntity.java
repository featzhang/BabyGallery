package club.guadazi.babygallery.provider.entity;

import java.io.Serializable;

public class ImageEntity implements Serializable {
    public static final String TABLE_NAME = "t_image";
    public static final String TABLE_COLUMN_ID = "id";
    public static final String TABLE_COLUMN_REMOTE_IMAGE_ID = "remote_image_id";
    public static final String TABLE_COLUMN_IMAGE_LOCAL_NAME = "image_local_name";
    public static final String CREATE_SQL = "CREATE TABLE " + TABLE_NAME +
            "(" + TABLE_COLUMN_ID + " INTEGER  PRIMARY KEY  NOT NULL, " +
            TABLE_COLUMN_REMOTE_IMAGE_ID + " INTEGER, " +
            TABLE_COLUMN_IMAGE_LOCAL_NAME + " CHAR(25) " +
            ");";
    private int id;
    private int remoteImageId;
    private String imageLocalName;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getRemoteImageId() {
        return remoteImageId;
    }

    public void setRemoteImageId(int remoteImageId) {
        this.remoteImageId = remoteImageId;
    }

    public String getImageLocalName() {
        return imageLocalName;
    }

    public void setImageLocalName(String imageLocalName) {
        this.imageLocalName = imageLocalName;
    }
}
