package club.guadazi.babygallery;

import android.content.Intent;
import android.graphics.Canvas;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.actionbarsherlock.view.Menu;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingFragmentActivity;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import java.util.List;

import club.guadazi.babygallery.provider.entity.MessageData;
import club.guadazi.babygallery.provider.remoteEntity.RemoteMessageEntity;
import club.guadazi.babygallery.provider.sync.MessageManager;
import club.guadazi.babygallery.util.ConstantValues;
import club.guadazi.babygallery.view.MessageAdaptor;
import club.guadazi.babygallery.view.MessageViewHolder;
import club.guadazi.babygallery.view.NewMessageActivity;
import club.guadazi.babygallery.view.SlidingMenuFragment;

public class MainActivity extends SlidingFragmentActivity {

    private static final int ADD_NEW_MESSAGE = 0x01;
    private ListView listView;
    private List<MessageData> messageDatas;
    private MessageAdaptor adapter;
    private final String TAG = "MainActivity";
    private MessageViewHolder.MessageAction messageAction;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initSlidingMenu();

        listView = (ListView) findViewById(R.id.main_act_message_list);
        messageAction = new MessageViewHolder.MessageAction() {

            @Override
            public void delete(final MessageData message) {
                AsyncHttpClient asyncHttpClient = new AsyncHttpClient(null);
                RequestParams requestParams = new RequestParams();
                requestParams.put("messageId", message.getRemoteId() + "");
                asyncHttpClient.post(MainActivity.this, ConstantValues.DELETE_MESSAGE, requestParams, new AsyncHttpResponseHandler() {
                    @Override
                    public void onFinish() {
                        Log.d(TAG, "MessageViewHolder.MessageAction onFinish");
                        Toast.makeText(MainActivity.this, "已删除！", Toast.LENGTH_SHORT).show();
                        int index = MessageManager.deleteLocalMessageById(MainActivity.this, message.getId());
                        List<MessageData> newDatas = MessageManager.loadAllLocalMessages(MainActivity.this);
                        setAndRefreshListView(newDatas);
                    }

                    @Override
                    public void onFailure(Throwable throwable) {
                        super.onFailure(throwable);
                    }
                });
            }
        };
        messageDatas = MessageManager.loadAllLocalMessages(this);
        adapter = new MessageAdaptor(this, messageAction);
        adapter.setMessageDatas(messageDatas);
        listView.setAdapter(adapter);


        AsyncHttpClient asyncHttpClient = new AsyncHttpClient(null);
        final RequestParams requestParams = new RequestParams();
        requestParams.put("userId", ConstantValues.getUserId(this) + "");
        Log.i(TAG, "请求最新消息列表");

        asyncHttpClient.post(ConstantValues.GET_MESSAGES_BY_USER_ID, requestParams, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(String response) {
                Log.d(TAG, "response:" + response);
                Toast.makeText(MainActivity.this, "请求成功", Toast.LENGTH_SHORT).show();
                if (response.equals("null")) {
                    Log.i(TAG, "列表为空，清空本地数据！");
                    MessageManager.deleteAllLocalMessageData(MainActivity.this);
                    setAndRefreshListView(null);
                    return;
                }
                Gson gson = ConstantValues.getDateFormatGson();
                List<RemoteMessageEntity> remoteDatas = gson.fromJson(response, new TypeToken<List<RemoteMessageEntity>>() {
                }.getType());
                List<MessageData> newDatas = MessageManager.updateLocalMessagesByRemote(MainActivity.this, remoteDatas);
                Log.i(TAG, "请求到列表，大小为:" + remoteDatas.size());
                setAndRefreshListView(newDatas);
            }

            @Override
            public void onFailure(Throwable throwable) {
                Log.e(TAG, "connect internet failure!");
                Toast.makeText(MainActivity.this, "联网失败!", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void initSlidingMenu() {
        SlidingMenu sm = getSlidingMenu();

        sm.setShadowWidthRes(R.dimen.sliding_menu_shadow_width);//设置阴影宽度
        sm.setShadowDrawable(R.drawable.shadow);

        sm.setBehindOffsetRes(R.dimen.slidingmenu_offset);//拉开后离边框距离

        sm.setFadeEnabled(true);//是否有渐变
        sm.setFadeDegree(0.3f);//设置渐变比率

//        sm.attachToActivity(this, SlidingMenu.SLIDING_CONTENT);//使SlidingMenu附加在Activity上
        sm.toggle(true);

        setBehindContentView(R.layout.sliding_menu_behind_frame);
        sm.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
        sm.setMode(SlidingMenu.LEFT);
        sm.setBehindWidth(R.dimen.sliding_menu_behind_width);

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setSlidingActionBarEnabled(false);//左右两侧slidingmenu的fragment是否显示标题栏

        SlidingMenu.CanvasTransformer mTransformer = new SlidingMenu.CanvasTransformer() {
            @Override
            public void transformCanvas(Canvas canvas, float percentOpen) {
                float scale = (float) (percentOpen * 0.25 + 0.75);
                canvas.scale(scale, scale, canvas.getWidth() / 2, canvas.getHeight() / 2);
            }
        };
        sm.setBehindCanvasTransformer(mTransformer);
//        setAndRefreshListView();

        SlidingMenuFragment menuFragment = new SlidingMenuFragment();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.menu_frame, menuFragment, "MyMenu").commit();

        setSlidingActionBarEnabled(true);//ActionBar 也滑动
    }

    private void setAndRefreshListView(List<MessageData> newDatas) {
        if (messageDatas.size() > 0) {
            messageDatas.clear();
        }
        messageDatas.addAll(newDatas);
        MessageAdaptor adapter = (MessageAdaptor) listView.getAdapter();
        adapter.setMessageDatas(messageDatas);
        adapter.notifyDataSetChanged();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getSupportMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(com.actionbarsherlock.view.MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                toggle();
                return true;
            case R.id.menu_main_add_message:
                Intent intent = new Intent(MainActivity.this, NewMessageActivity.class);
                startActivityForResult(intent, ADD_NEW_MESSAGE);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (resultCode) {
            case NewMessageActivity.CREATE_NEW_MESSAGE_SUCCESS:
                List<MessageData> newMessageDatas = MessageManager.loadAllLocalMessages(this);
                setAndRefreshListView(newMessageDatas);
                break;
        }
    }

    public void closeApplication(View view) {

    }
}
