package club.guadazi.babyGalleryEJB.ejb;

import club.guadazi.babyGalleryEJB.dao.MessageDao;
import club.guadazi.babyGalleryEJB.entity.MessageEntity;
import club.guadazi.babyGalleryEJB.ifc.MessageData;
import club.guadazi.babyGalleryEJB.ifc.MessageIfc;

import javax.annotation.PostConstruct;
import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * Created by Mariaaron on 16/1/4.
 */
@Stateless
@Local(MessageIfc.class)
public class MessageEJB implements MessageIfc {
    private MessageDao messageDao;

    @PersistenceContext(unitName = "BabyGalleryPU")
    private EntityManager em;

    @PostConstruct
    private void postConstruct() {
        messageDao = new MessageDao(em);
    }

    @Override
    public int addNewMessage(MessageData messageData) {
        MessageEntity messageEntity = messageData.toEntity();
        int id = messageDao.addNewMessage(messageEntity);
        return id;
    }

    @Override
    public List<MessageData> getMessageByUserId(int userId) {
        List<MessageData> messageDatas= messageDao.findByUserId(userId);
        return messageDatas;
    }

    @Override
    public MessageData update(MessageData messageData) {
        return null;
    }

    @Override
    public void deleteByMessageId(int messageId) {
        messageDao.deleteByMessageId(messageId);
    }

}
