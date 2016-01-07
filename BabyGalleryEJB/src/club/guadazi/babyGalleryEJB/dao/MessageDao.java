package club.guadazi.babyGalleryEJB.dao;

import club.guadazi.babyGalleryEJB.entity.MessageEntity;
import club.guadazi.babyGalleryEJB.ifc.MessageData;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;

public class MessageDao {
    @PersistenceContext(unitName = "BabyGalleryPU")
    private EntityManager em;

    public MessageDao(EntityManager em) {
        this.em = em;
    }

    public int addNewMessage(MessageEntity messageEntity) {
        em.persist(messageEntity);
        em.flush();
        return messageEntity.getId();
    }

    public List<MessageData> findByUserId(int userId) {
        Query query = em.createQuery("select w from MessageEntity w where userId=:uuid");
        query.setParameter("uuid", userId);
        List<MessageEntity> resultList = query.getResultList();
        if (resultList != null && resultList.size() > 0) {
            ArrayList<MessageData> messageDatas = new ArrayList<>();
            for (MessageEntity messageEntity : resultList) {
                messageDatas.add(new MessageData(messageEntity));
            }
            return messageDatas;
        }

        return null;
    }

    public void deleteByMessageId(int messageId) {
        Query query = em.createQuery("delete  from MessageEntity w where id=:uuid");
        query.setParameter("uuid", messageId);
        query.executeUpdate();
    }
}
