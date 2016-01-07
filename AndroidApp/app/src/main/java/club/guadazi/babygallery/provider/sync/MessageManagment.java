package club.guadazi.babygallery.provider.sync;


import android.content.Context;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import club.guadazi.babygallery.entity.MessageData;
import club.guadazi.babygallery.provider.dao.MessageDao;

public class MessageManagment {
    public static List<MessageDataUpdate> compare(List<MessageData> remoteDatas, List<MessageData> localDatas) {
        ArrayList<MessageDataUpdate> messageDataUpdates = new ArrayList<MessageDataUpdate>();
        if (localDatas == null || localDatas.size() == 0) {
            if (remoteDatas == null || remoteDatas.size() == 0) {
                return null;
            } else {
                for (MessageData remoteData : remoteDatas) {
                    messageDataUpdates.add(new MessageDataUpdate(remoteData, MessageDataUpdate.UPDATE_MODE.ADD));
                }
                return messageDataUpdates;
            }
        }
        if (remoteDatas == null || remoteDatas.size() == 0) {
            if (localDatas != null && localDatas.size() > 0) {
                for (MessageData localData : localDatas) {
                    messageDataUpdates.add(new MessageDataUpdate(localData, MessageDataUpdate.UPDATE_MODE.DELETE));
                }
                return messageDataUpdates;
            }
        }
        Map<Integer, MessageData> map = new HashMap<Integer, MessageData>();
        for (MessageData localData : localDatas) {
            int id = localData.getId();
            map.put(id, localData);
        }
        for (MessageData remoteData : remoteDatas) {
            int id = remoteData.getId();
            MessageData messageData = map.get(id);
            if (messageData == null) {
                messageDataUpdates.add(new MessageDataUpdate(remoteData, MessageDataUpdate.UPDATE_MODE.ADD));
            } else {
                Timestamp remoteUpdateTime = remoteData.getUpdateTime();
                Timestamp localUpdateTime = messageData.getUpdateTime();
                if (localUpdateTime == null || !localUpdateTime.equals(remoteUpdateTime)) {
                    messageDataUpdates.add(new MessageDataUpdate(remoteData, MessageDataUpdate.UPDATE_MODE.UPDATE));
                }
            }
        }
        if (messageDataUpdates.size() == 0) {
            return null;
        } else {
            return messageDataUpdates;
        }
    }

    public static void updateLocalMessageData(Context context, List<MessageDataUpdate> messageDataUpdates) {
        if (messageDataUpdates == null || messageDataUpdates.size() == 0) {
            return;
        }
        MessageDao messageDao = new MessageDao(context);
        for (MessageDataUpdate messageDataUpdate : messageDataUpdates) {
            MessageDataUpdate.UPDATE_MODE mode = messageDataUpdate.getMode();
            switch (mode) {
                case ADD:
                    messageDao.add(messageDataUpdate.getMessageData());
                    break;
                case UPDATE:
                    messageDao.update(messageDataUpdate.getMessageData());
                    break;
                case DELETE:
                    messageDao.deleteByMessage(messageDataUpdate.getMessageData().getId());
                    break;
            }
        }

    }
}
