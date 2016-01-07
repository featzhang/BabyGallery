package club.guadazi.babygallery.entity;

import java.io.Serializable;

public class ImageEntity implements Serializable {
//    private int id;
    private int imageId;
    private String imageLocal;

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

    public String getImageLocal() {
        return imageLocal;
    }

    public void setImageLocal(String imageLocal) {
        this.imageLocal = imageLocal;
    }
}
