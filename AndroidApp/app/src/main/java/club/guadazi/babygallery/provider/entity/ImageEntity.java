package club.guadazi.babygallery.provider.entity;

import java.io.Serializable;

public class ImageEntity implements Serializable {
    public static final String TABLE_NAME = "t_image";
    public static final String TABLE_COLUMN_IMAGE_ID = "image_id";
    public static final String TABLE_COLUMN_IMAGE_LOCAL_NAME = "image_local_name";
    public static final String CREATE_SQL = "CREATE TABLE " + TABLE_NAME  +
            "(" + TABLE_COLUMN_IMAGE_ID + " INTEGER, " + TABLE_COLUMN_IMAGE_LOCAL_NAME + " CHAR(25) " +
            ");";
    private int imageId;
    private String imageLocalName;

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

    public String getImageLocalName() {
        return imageLocalName;
    }

    public void setImageLocalName(String imageLocalName) {
        this.imageLocalName = imageLocalName;
    }
}
