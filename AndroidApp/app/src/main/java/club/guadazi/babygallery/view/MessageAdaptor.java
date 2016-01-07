package club.guadazi.babygallery.view;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

import club.guadazi.babygallery.R;
import club.guadazi.babygallery.provider.entity.MessageData;

public class MessageAdaptor extends BaseAdapter {
    private MessageViewHolder.MessageAction mMessageAction;
    private List<MessageData> messageDatas;
    private final String TAG = "MessageAdaptor";

    public MessageAdaptor(Context context, MessageViewHolder.MessageAction messageAction) {
        this.mContext = context;
        this.mMessageAction = messageAction;
    }

    public void setMessageDatas(List messageDatas) {
        this.messageDatas = messageDatas;
    }

    public List getMessageDatas() {
        return messageDatas;
    }

    private Context mContext;

    @Override
    public int getCount() {
        if (messageDatas == null)
            return 0;
        else {
            return messageDatas.size();
        }
    }

    @Override
    public Object getItem(int i) {
        if (messageDatas == null)
            return null;
        else {
            return messageDatas.get(i);
        }
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        Log.d(TAG, "getView: " + i);
        if (i < 0 || messageDatas == null || i >= messageDatas.size()) {
            return view;
        }
        if (view != null) {
            MessageViewHolder messageViewHolder = (MessageViewHolder) view.getTag();
            messageViewHolder.setMessage(messageDatas.get(i));
            return view;
        } else {
            LayoutInflater layoutInflater = (LayoutInflater) mContext.getSystemService(mContext.LAYOUT_INFLATER_SERVICE);
            View newView = layoutInflater.inflate(R.layout.message_item, viewGroup, false);
            MessageViewHolder messageViewHolder = new MessageViewHolder(mContext, newView);
            messageViewHolder.setMessage(messageDatas.get(i));
            messageViewHolder.setMessageAction(mMessageAction);
            newView.setTag(messageViewHolder);
            return newView;
        }
//            return new TextView(MainActivity.this);
    }

}

