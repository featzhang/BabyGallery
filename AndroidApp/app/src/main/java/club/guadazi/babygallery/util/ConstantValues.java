package club.guadazi.babygallery.util;

import android.content.Context;

public class ConstantValues {
    public static int getUserId(Context context) {
//        return new UserDao(context).getUserId();
        return 1;
    }

    public final static String ipWithPort = "192.168.13.103:8080";
    public final static String GET_MESSAGES_BY_USER_ID = "http://" + ipWithPort +
            "/BabyGallery/listMessages.action";
    public final static String CREATE_NEW_MESSAGE = "http://" + ipWithPort +
            "/BabyGallery/addMessage.action";
    public static final String DELETE_MESSAGE = "http://" + ipWithPort +
            "/BabyGallery/deleteMessage.action";
    public static final int max_images = 8;
}
