package club.guadazi.babyGalleryEJB.dao;

import club.guadazi.babyGalleryEJB.entity.ImageEntity;
import club.guadazi.babyGalleryEJB.entity.UserEntity;
import club.guadazi.babyGalleryEJB.ifc.ImageData;
import club.guadazi.babyGalleryEJB.ifc.UserData;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

public class ImageDao {
    Log log = LogFactory.getLog(ImageDao.class);
    @PersistenceContext(unitName = "BabyGalleryPU")
    private EntityManager em;

    public ImageDao(EntityManager em) {
        this.em = em;
    }


    public UserData findUserById(int id) {
        String hql = "select u from UserEntity u where u.id=:idd";
        Query query = em.createQuery(hql);
        query.setParameter("idd", id);
//        query.setMaxResults(1);
        //先判断空
        if (query.getResultList().size() == 0) {
            log.info("result is empty!");
            return null;
        } else {
            UserEntity userEntity = (UserEntity) query.getResultList().get(0);
            log.info("id:" + id + "\t UserEntity:" + userEntity);
            return userEntity == null ? null : new UserData(userEntity);
        }

    }


    public int add(ImageData imageData) {
        ImageEntity imageEntity = imageData.toEntity();
        em.persist(imageData);
        em.flush();
        return imageEntity.getId();
    }

    public boolean update(ImageData userData) {
        boolean flag = false;
        try {
            em.merge(userData.toEntity());
            flag = true;
        } catch (Exception e) {
            flag = false;
            log.error("Eao更新实体失败", e);
        }
        return flag;
    }

    public ImageData findById(int id) {
        ImageEntity imageEntity = em.find(ImageEntity.class, id);

        return imageEntity == null ? null : new ImageData(imageEntity);
    }
}
