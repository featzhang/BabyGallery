package club.guadazi.babyGalleryEJB.ejb;

import club.guadazi.babyGalleryEJB.dao.UserDao;
import club.guadazi.babyGalleryEJB.ifc.UserData;
import club.guadazi.babyGalleryEJB.ifc.UserIfc;

import javax.annotation.PostConstruct;
import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
@Local(UserIfc.class)
public class UserEJB implements UserIfc {
    private UserDao userDao;

    @PersistenceContext(unitName = "BabyGalleryPU")
    private EntityManager em;

    @PostConstruct
    private void postConstruct() {
        userDao = new UserDao(em);
    }

    @Override
    public int addNewUser(UserData userData) {
        int id = userDao.addNewUser(userData);
        return id;
    }

    @Override
    public UserData update(UserData userData) {
        return null;
    }

    @Override
    public UserData findUserById(int id) {
        UserData userData = userDao.findUserById(id);
        return userData;
    }

    @Override
    public UserData findUserByName(String name) {
        UserData userData = userDao.findUserByName(name);
        return userData;
    }
}
