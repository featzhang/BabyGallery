package club.guadazi.babyGallery;

import club.guadazi.babyGalleryEJB.ifc.UserData;
import club.guadazi.babyGalleryEJB.ifc.UserIfc;
import club.guadazi.common.EjbUtils;
import com.alibaba.fastjson.JSON;
import com.opensymphony.xwork2.ActionSupport;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class UserAction extends ActionSupport {
    private Log log = LogFactory.getLog(UserAction.class);
    private UserIfc userIfc = (UserIfc) EjbUtils.getEjb(UserIfc.class);
    private int id = -1;
    private String name = "";
    private UserData userData;
    private String userDataString;

    public void setName(String name) {
        this.name = name;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setUserDataString(String userDataString) {
        this.userDataString = userDataString;
    }

    public UserData getUserData() {
        return userData;
    }

    public String registerNewUser() {
        UserData userData;
        if (userDataString != null && org.apache.commons.lang.StringUtils.isNotEmpty(userDataString)) {
            userData = JSON.parseObject(userDataString, UserData.class);
            log.info(userData);
            id = userIfc.addNewUser(userData);
        } else {
            log.error("user data json string is empty!");
        }
        return "success";
    }

    public String findUser() throws Exception {
        if (id != -1) {
            userData = userIfc.findUserById(id);
            log.info("id:" + id + "\t UserEntity:" + userData);

        }
        log.error("name:" + name);
        if (name != null && StringUtils.isNotEmpty(name)) {
            userData = userIfc.findUserByName(name);
            log.info("name:" + name + "\t UserEntity:" + userData);
        }
        return "success";
    }

    public String updateUser() {
        if (userDataString != null && org.apache.commons.lang.StringUtils.isNotEmpty(userDataString)) {
            userData = JSON.parseObject(userDataString, UserData.class);
            userData = userIfc.update(userData);
        }
        return SUCCESS;
    }
}
