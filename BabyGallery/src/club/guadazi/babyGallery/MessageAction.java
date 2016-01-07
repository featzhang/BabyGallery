package club.guadazi.babyGallery;

import club.guadazi.babyGalleryEJB.ifc.MessageData;
import club.guadazi.babyGalleryEJB.ifc.MessageIfc;
import club.guadazi.common.EjbUtils;
import com.alibaba.fastjson.JSON;
import com.google.gson.Gson;
import com.opensymphony.xwork2.ActionSupport;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.List;

public class MessageAction extends ActionSupport {
    Log log = LogFactory.getLog(MessageAction.class);
    private List<MessageData> messageDatas;
    private int userId = -1;
    private int messageId;
    private String messageDataString;
    private MessageData messageData;
    private MessageIfc messageIfc = (MessageIfc) EjbUtils.getEjb(MessageIfc.class);

    public void setMessageDataString(String messageDataString) {
        this.messageDataString = messageDataString;
    }

    public String listMessages() {
        log.info("user id : " + userId);
        if (userId != -1) {
            messageDatas = messageIfc.getMessageByUserId(userId);
            if (messageDatas != null) {
                for (MessageData data : messageDatas) {
                    log.info(data);
                }
            } else {
                log.error("can not find messages of user with id " + userId);
            }
        }
        return SUCCESS;

    }

    public List<MessageData> getMessageDatas() {
        return messageDatas;
    }

    public String addMessage() {
        log.info("addMessage" + messageDataString);
        if (StringUtils.isNotEmpty(messageDataString)) {
            Gson gson = new Gson();
            messageData = gson.fromJson(messageDataString, MessageData.class);
//            messageData = JSON.parseObject(messageDataString, MessageData.class);
            messageId = messageIfc.addNewMessage(messageData);
            log.info("messageId:" + messageId);
        }
        return SUCCESS;
    }

    public int getMessageId() {
        return messageId;
    }

    public MessageData getMessageData() {
        return messageData;
    }

    public String updateMessage() {
        if (StringUtils.isNotEmpty(messageDataString)) {
            messageData = JSON.parseObject(messageDataString, MessageData.class);
            messageData = messageIfc.update(messageData);
        }
        return SUCCESS;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String deleteMessage() {
        log.info("message id:" + messageId);
        if (messageId != -1) {
            messageIfc.deleteByMessageId(messageId);
        }
        return SUCCESS;
    }

    public void setMessageId(int messageId) {
        this.messageId = messageId;
    }
}
