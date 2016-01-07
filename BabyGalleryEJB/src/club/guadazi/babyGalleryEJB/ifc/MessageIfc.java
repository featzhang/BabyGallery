package club.guadazi.babyGalleryEJB.ifc;

import java.util.List;

public interface MessageIfc {
    public static final String jndi = "MessageEJB/local";
    public static final String jndi7 = "java:global/BabyGallery/BabyGallery_ejb/MessageEJB";

    int addNewMessage(MessageData userEntity);

    List<MessageData> getMessageByUserId(int userId);

    MessageData update(MessageData messageData);

    void deleteByMessageId(int messageId);
}
