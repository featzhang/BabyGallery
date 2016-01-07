package club.guadazi.babygallery.provider.sync;

import club.guadazi.babygallery.provider.entity.MessageData;

public class MessageDataUpdate {
    private MessageData messageData;
    private UPDATE_MODE mode;

    enum UPDATE_MODE {
        ADD, DELETE, UPDATE
    }

    public MessageDataUpdate(MessageData messageData, UPDATE_MODE mode) {
        this.messageData = messageData;
        this.mode = mode;
    }

    public MessageDataUpdate() {
    }

    public MessageData getMessageData() {
        return messageData;
    }

    public void setMessageData(MessageData messageData) {
        this.messageData = messageData;
    }

    public UPDATE_MODE getMode() {
        return mode;
    }

    public void setMode(UPDATE_MODE mode) {
        this.mode = mode;
    }
}
