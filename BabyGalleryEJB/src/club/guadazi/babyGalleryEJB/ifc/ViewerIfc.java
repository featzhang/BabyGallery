package club.guadazi.babyGalleryEJB.ifc;

import java.util.List;

public interface ViewerIfc {
    public static final String jndi = "UserEJB/local";
    public static final String jndi7 = "java:global/BabyGallery/BabyGallery_ejb/UserEJB";

    int add(ViewerData viewerData);

    UserData findById(int id);

    UserData findByName(String name);

    List<ViewerData> listViewerOfUser(int userId);

    ViewerData update(ViewerData viewerData);
}
