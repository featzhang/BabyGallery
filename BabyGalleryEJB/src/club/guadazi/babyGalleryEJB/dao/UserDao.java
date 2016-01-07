package club.guadazi.babyGalleryEJB.dao;

import club.guadazi.babyGalleryEJB.entity.UserEntity;
import club.guadazi.babyGalleryEJB.ifc.UserData;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

public class UserDao {
    Log log = LogFactory.getLog(UserDao.class);
    @PersistenceContext(unitName = "BabyGalleryPU")
    private EntityManager em;

    public UserDao(EntityManager em) {
        this.em = em;
    }

    public int addNewUser(UserData userData) {
        UserEntity userEntity = userData.toEntity();
        em.persist(userEntity);
        em.flush();
        return userEntity.getId();
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

    public UserData findUserByName(String name) {
        String hql = "from UserEntity where name=:name";
        Query query = em.createQuery(hql);
        query.setParameter("name", name);
        if (query.getResultList().size() == 0) {
            return null;
        } else {
            UserEntity userEntity = (UserEntity) query.getResultList().get(0);
            log.error("name:" + name + "\t UserEntity:" + userEntity);
            return userEntity == null ? null : new UserData(userEntity);
        }
    }
}
