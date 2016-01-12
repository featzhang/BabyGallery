package club.guadazi.babyGalleryEJB.ifc;

public interface ImageIfc {
    public static final String jndi = "ImageEJB/local";
    public static final String jndi7 = "java:global/BabyGallery/BabyGallery_ejb/ImageEJB";

    int add(ImageData imageData);

    boolean update(ImageData userData);

    ImageData findById(int id);

}
