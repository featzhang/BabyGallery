package club.guadazi.babygallery.net;

/**
 * Created by Mariaaron on 16/1/5.
 */
public class NetWorkConstant {
    public final static String ipWithPort = "192.168.1.106:8080";
    public final static String GET_MESSAGES_BY_USER_ID = "http://" + ipWithPort +
            "/BabyGallery/listMessages.action";
    public final static String CREATE_NEW_MESSAGE = "http://" + ipWithPort +
            "/BabyGallery/addMessage.action";
    public static final String DELETE_MESSAGE = "http://" + ipWithPort +
            "/BabyGallery/deleteMessage.action";
}
