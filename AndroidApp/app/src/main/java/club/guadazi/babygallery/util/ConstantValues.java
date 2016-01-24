package club.guadazi.babygallery.util;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class ConstantValues {
    public static int getUserId(Context context) {
//        return new UserDao(context).getUserId();
        return 1;
    }

    public final static String ipWithPort = "192.168.1.9:8080";

    public final static String GET_MESSAGES_BY_USER_ID = "http://" + ipWithPort +
            "/BabyGallery/listMessages.action";

    public static final String DELETE_MESSAGE = "http://" + ipWithPort +
            "/BabyGallery/deleteMessage.action";
    public static final int max_images = 8;
    public static final String ADD_NEW_MESSAGE_URL = "http://" + ipWithPort + "/BabyGallery/addMessage.action";
    public static final String REQUEST_IMAGE = "http://" + ipWithPort + "/BabyGallery/download-image.action";
    public static final String REQUEST_IMAGE_NIC = "http://" + ipWithPort + "/BabyGallery/download-image-nic.action";

    public static String getRequestImageNicUrl(Context context, int remoteId) {
        return REQUEST_IMAGE_NIC + "?imageId=" + remoteId + "&userId=" + getUserId(context);
    }

    public static String getRequestImageUrl(Context context, int remoteId) {
        return REQUEST_IMAGE + "?imageId=" + remoteId + "&userId=" + getUserId(context);
    }

    public static Gson getDateFormatGson() {
        return new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
    }
}
