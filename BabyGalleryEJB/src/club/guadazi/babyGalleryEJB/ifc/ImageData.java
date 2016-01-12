package club.guadazi.babyGalleryEJB.ifc;

import club.guadazi.babyGalleryEJB.entity.ImageEntity;

/**
 * Created by aaron on 16-1-11.
 */
public class ImageData {
    private int id;
    private String imageName;

    public ImageData(ImageEntity imageEntity) {
        id = imageEntity.getId();
        imageName = imageEntity.getImageName();
    }

    public ImageData() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

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

        if (id != that.getId()) return false;
        if (imageName != null ? !imageName.equals(that.getImageName()) : that.getImageName() != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (imageName != null ? imageName.hashCode() : 0);
        return result;
    }

    public ImageEntity toEntity() {
        ImageEntity imageEntity = new ImageEntity();
        imageEntity.setId(id);
        imageEntity.setImageName(imageName);
        return imageEntity;
    }
}
