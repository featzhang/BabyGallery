package club.guadazi.babygallery.provider.sync;


import android.content.Context;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import club.guadazi.babygallery.MainActivity;
import club.guadazi.babygallery.provider.dao.MessageDao;
import club.guadazi.babygallery.provider.entity.MessageData;
import club.guadazi.babygallery.provider.remoteEntity.RemoteMessageEntity;
import club.guadazi.babygallery.util.ConstantValues;

public class MessageManager {
    public static List<MessageDataUpdate> compare(List<RemoteMessageEntity> remoteDatas, List<MessageData> localDatas) {
        ArrayList<MessageDataUpdate> messageDataUpdates = new ArrayList<MessageDataUpdate>();
        if (localDatas == null || localDatas.size() == 0) {
            if (remoteDatas == null || remoteDatas.size() == 0) {
                return null;
            } else {
                for (RemoteMessageEntity remoteData : remoteDatas) {
                    messageDataUpdates.add(new MessageDataUpdate(new MessageData(remoteData), MessageDataUpdate.UPDATE_MODE.ADD));
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
        for (RemoteMessageEntity remoteData : remoteDatas) {
            int id = remoteData.getId();
            MessageData messageData = map.get(id);
            if (messageData == null) {
                messageDataUpdates.add(new MessageDataUpdate(new MessageData(remoteData), MessageDataUpdate.UPDATE_MODE.ADD));
            } else {
                Timestamp remoteUpdateTime = remoteData.getUploadDate();
                Timestamp localUpdateTime = messageData.getUpdateTime();
                if (localUpdateTime == null || !localUpdateTime.equals(remoteUpdateTime)) {
                    messageDataUpdates.add(new MessageDataUpdate(new MessageData(remoteData), MessageDataUpdate.UPDATE_MODE.UPDATE));
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
                    messageDao.updateByLocalId(messageDataUpdate.getMessageData());
                    break;
                case DELETE:
                    messageDao.deleteByMessageRemoteId(messageDataUpdate.getMessageData().getId());
                    break;
            }
        }

    }

    public static void updateLocalMessagesByRemote(Context context, List<RemoteMessageEntity> remoteDates) {
        List<MessageData> localDatas = loadAllLocalMessages(context);
        List<MessageDataUpdate> dataUpdates = MessageManager.compare(remoteDates, localDatas);
        MessageManager.updateLocalMessageData(context, dataUpdates);
    }

    public static void deleteAllLocalMessageData(Context context) {
        List<MessageData> localDatas = new MessageDao(context).listAllMessageByUserId(ConstantValues.getUserId(context));
        if (localDatas == null || localDatas.size() == 0) {
            return;
        }
        MessageDao messageDao = new MessageDao(context);
        for (MessageData localData : localDatas) {
            messageDao.deleteByMessageId(localData.getId());
        }
    }

    public static List<MessageData> loadAllLocalMessages(Context context) {
        return new MessageDao(context).listAllMessageByUserId(ConstantValues.getUserId(context));
    }

    public static long addMessageToLocalDB(Context context, MessageData messageData) {
        return new MessageDao(context).add(messageData);
    }

    public static int deleteLocalMessageById(Context context, int id) {
        return new MessageDao(context).deleteByMessageId(id);
    }
}
