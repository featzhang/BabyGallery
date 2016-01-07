package club.guadazi.babygallery;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import java.util.List;

import club.guadazi.babygallery.provider.entity.MessageData;
import club.guadazi.babygallery.provider.dao.MessageDao;
import club.guadazi.babygallery.provider.sync.MessageDataUpdate;
import club.guadazi.babygallery.provider.sync.MessageManagment;
import club.guadazi.babygallery.resources.ConstantValues;
import club.guadazi.babygallery.view.MessageAdaptor;
import club.guadazi.babygallery.view.MessageViewHolder;
import club.guadazi.babygallery.view.NewMessageActivity;

public class MainActivity extends Activity {

    private static final int ADD_NEW_MESSAGE = 0x01;
    private ListView listView;
    private List<MessageData> messageDatas;
    private MessageAdaptor adapter;
    private final String TAG = "MainActivity";
    private MessageViewHolder.MessageAction messageAction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = (ListView) findViewById(R.id.main_act_message_list);
        messageAction = new MessageViewHolder.MessageAction() {

            @Override
            public void delete(MessageData message) {
                AsyncHttpClient asyncHttpClient = new AsyncHttpClient(null);
                RequestParams requestParams = new RequestParams();
                requestParams.put("messageId", message.getId() + "");
                asyncHttpClient.post(MainActivity.this, ConstantValues.DELETE_MESSAGE, requestParams, new AsyncHttpResponseHandler() {
                    @Override
                    public void onFinish() {
                        Log.d(TAG, "MessageViewHolder.MessageAction onFinish");
                        super.onFinish();
                        Toast.makeText(MainActivity.this, "已删除！", Toast.LENGTH_SHORT).show();
                        adapter.setMessageDatas(messageDatas);

                        adapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onFailure(Throwable throwable) {
                        super.onFailure(throwable);
                    }
                });
            }
        };
        messageDatas = new MessageDao(this).listAllMessageByUserId(ConstantValues.getUserId(this));
        adapter = new MessageAdaptor(this, messageAction);
        adapter.setMessageDatas(messageDatas);
        listView.setAdapter(adapter);

        AsyncHttpClient asyncHttpClient = new AsyncHttpClient(null);
        RequestParams requestParams = new RequestParams();
        requestParams.put("userId", ConstantValues.getUserId(this) + "");
        asyncHttpClient.post(ConstantValues.GET_MESSAGES_BY_USER_ID, requestParams, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(String response) {
                Log.d(TAG, response);
                Gson gson = new Gson();
                List<MessageData> remoteDatas = gson.fromJson(response, new TypeToken<List<MessageData>>() {
                }.getType());

                if (remoteDatas != null) {
                    if (messageDatas != null) {
                        messageDatas.clear();
                        messageDatas.addAll(remoteDatas);
                    } else {
                        messageDatas = remoteDatas;
                    }
                    for (MessageData messageData : remoteDatas) {
                        Log.d(TAG, messageData.toString());
                    }
                    adapter.setMessageDatas(messageDatas);
                    adapter.notifyDataSetChanged();
                    List<MessageDataUpdate> dataUpdates = MessageManagment.compare(remoteDatas, new MessageDao(MainActivity.this).listAllMessageByUserId(ConstantValues.getUserId(MainActivity.this)));
                    MessageManagment.updateLocalMessageData(MainActivity.this, dataUpdates);
                }
            }

            @Override
            public void onFailure(Throwable throwable) {
                super.onFailure(throwable);
                Toast.makeText(MainActivity.this, "联网失败!", Toast.LENGTH_SHORT).show();
            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = new MenuInflater(MainActivity.this);
        menuInflater.inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        switch (itemId) {
            case R.id.menu_main_add_message:
                Intent intent = new Intent(MainActivity.this, NewMessageActivity.class);
                startActivityForResult(intent, ADD_NEW_MESSAGE);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
