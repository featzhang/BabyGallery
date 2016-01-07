package club.guadazi.babygallery;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import club.guadazi.babygallery.entity.MessageData;

public class MessageViewHolder {
    public interface MessageAction {
        void delete(MessageData message);
    }

    private MessageAction messageAction;

    public void setMessageAction(MessageAction messageAction) {
        this.messageAction = messageAction;
    }

    private Context mContext;
    private TextView praiseCountTextView;
    private ImageView praiseImageView;
    private GridView imagesGridView;
    private TextView commentTextView;
    private TextView commentCountTextView;
    private ImageView commentImageView;
    private LinearLayout layout;
    private PopupWindow popupWindow;
    private String TAG = "MessageViewHolder";

    public MessageViewHolder(Context context, View view1) {

        this.mContext = context;
        if (view1 != null) {
            commentCountTextView = (TextView) view1.findViewById(R.id.message_list_content_comment_count_tv);
            commentImageView = (ImageView) view1.findViewById(R.id.message_list_content_comment_iv);
            commentTextView = (TextView) view1.findViewById(R.id.message_list_content_content);
            imagesGridView = (GridView) view1.findViewById(R.id.message_list_content_images);
            praiseCountTextView = (TextView) view1.findViewById(R.id.message_list_content_praise_count_tv);
            praiseImageView = (ImageView) view1.findViewById(R.id.message_list_content_praise_iv);
            layout = (LinearLayout) view1.findViewById(R.id.message_item_content_layout);
        }
    }

    private MessageData message;
    private int[] imageIds;

    public void setMessage(MessageData message) {
        this.message = message;
        commentCountTextView.setText("10535");
        commentTextView.setText(message.getContent());
        String imageIdIs = message.getImageIds();
        if (!TextUtils.isEmpty(imageIdIs)) {
            String[] imageIdArray = imageIdIs.split("::");
            imageIds = new int[imageIdArray.length];
            for (int i = 0; i < imageIdArray.length; i++) {
                imageIds[i] = Integer.parseInt(imageIdArray[i]);
            }
        }
        MessageLongClickListener longClickListener = new MessageLongClickListener(message);
        commentTextView.setOnLongClickListener(longClickListener);

    }

    public class ImageGridAdaptor extends BaseAdapter {

        @Override
        public int getCount() {
            if (imageIds == null || imageIds.length == 0)
                return 0;
            else {
                return imageIds.length;
            }
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            return null;
        }
    }

    public class MessageLongClickListener implements View.OnLongClickListener {
        private MessageData messageData;

        public MessageLongClickListener(MessageData messageData) {
            this.messageData = messageData;
        }

        @Override
        public boolean onLongClick(View view) {
            Log.d(TAG, "onLongClick");
            // 获取自定义布局文件activity_popupwindow_left.xml的视图
            View popupWindowView = ((LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.message_item_popup_menu, null, false);

            // 创建PopupWindow实例,200,LayoutParams.MATCH_PARENT分别是宽度和高度
            popupWindow = new PopupWindow(popupWindowView, 100, 100, true);
            TextView deleteTextView = (TextView) popupWindowView.findViewById(R.id.message_item_menu_delete);
            deleteTextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.d(TAG, "deleteTextView onClick");
                    if (messageAction != null) {
                        messageAction.delete(message);
                        popupWindow.dismiss();
                    }
                }
            });
            // 设置动画效果
//            popupWindow.setAnimationStyle(R.style.AnimationFade);
            // 这里是位置显示方式,在屏幕的左侧
            popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);
            // 点击其他地方消失
            popupWindowView.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    if (popupWindow != null && popupWindow.isShowing()) {
                        popupWindow.dismiss();
                        popupWindow = null;
                    }
                    return false;
                }
            });
            return false;
        }
    }
}