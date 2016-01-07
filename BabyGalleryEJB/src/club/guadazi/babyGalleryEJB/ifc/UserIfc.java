package club.guadazi.babyGalleryEJB.ifc;

public interface UserIfc {
    public static final String jndi = "UserEJB/local";
    public static final String jndi7 = "java:global/BabyGallery/BabyGallery_ejb/UserEJB";

    int addNewUser(UserData userData);

    UserData update(UserData userData);

    UserData findUserById(int id);

    UserData findUserByName(String name);
}
