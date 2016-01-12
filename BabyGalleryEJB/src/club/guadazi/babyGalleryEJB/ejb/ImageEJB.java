package club.guadazi.babyGalleryEJB.ejb;

import club.guadazi.babyGalleryEJB.dao.ImageDao;
import club.guadazi.babyGalleryEJB.ifc.ImageData;
import club.guadazi.babyGalleryEJB.ifc.ImageIfc;

import javax.annotation.PostConstruct;
import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
@Local(ImageIfc.class)
public class ImageEJB implements ImageIfc {
    private ImageDao imageDao;

    @PersistenceContext(unitName = "BabyGalleryPU")
    private EntityManager em;

    @PostConstruct
    private void postConstruct() {
        imageDao = new ImageDao(em);
    }

    @Override
    public int add(ImageData imageData) {
        return imageDao.add(imageData);
    }

    @Override
    public boolean update(ImageData userData) {
        return imageDao.update(userData);
    }

    @Override
    public ImageData findById(int id) {
        return imageDao.findById(id);
    }
}
