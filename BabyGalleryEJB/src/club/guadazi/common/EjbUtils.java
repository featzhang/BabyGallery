package club.guadazi.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.naming.InitialContext;
import java.lang.reflect.Field;

/**
 * Created by Aaron on 2014/12/29.
 */
public class EjbUtils {

    private static boolean usingJboss7 = true;
    private static Logger log = LoggerFactory.getLogger(EjbUtils.class);

    private static <T> String getEjbJndiName(Class<T> t) {
        String jndi7 = "";
        String jndi = "";
        try {
            Field f1 = t.getField("jndi7");
            jndi7 = (String) f1.get(null);
            Field f2 = t.getField("jndi");
            jndi = (String) f2.get(null);
        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return usingJboss7 ? jndi7 : jndi;
    }

    /**
     * lookup ejb
     *
     * @param jndi
     * @return
     */
    private static Object lookupEJB(String jndi) {
        if (null == jndi || jndi.length() < 1) {
            log.error("jndi is NULL");
            return null;
        }

//        if (usingJboss7 && jndi.endsWith("/local")) {
//            if (jndi.equalsIgnoreCase("UserEJB/local")) {
//                jndi = "java:global/BabyGallery/BabyGallery_ejb/UserEJB";
//            }else if (jndi.equalsIgnoreCase("MessageEJB/local")) {
//                jndi = "java:global/BabyGallery/BabyGallery_ejb/MessageEJB";
//            }
//        }
        try {
            InitialContext ctx = new InitialContext();
            Object o = ctx.lookup(jndi);
            return o;
        } catch (Exception ex) {
            log.error("Failed to lookup " + jndi, ex);
        }

        return null;
    }

    public static <T> Object getEjb(Class<T> t) {
        String ejbJndiName = getEjbJndiName(t);
        Object ejb = lookupEJB(ejbJndiName);
        return ejb;
    }
}
