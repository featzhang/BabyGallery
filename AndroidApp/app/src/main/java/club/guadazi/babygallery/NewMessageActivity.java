package club.guadazi.babygallery;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import club.guadazi.babygallery.entity.MessageData;
import club.guadazi.babygallery.net.NetWorkConstant;
import club.guadazi.babygallery.resources.MessageImageManager;

public class NewMessageActivity extends Activity {
    private static final int CREATE_NEW_MESSAGE_SUCCESS = 0x01;
    private Date date;
    private GridView imagesGridView;
    private TextView markPointTextView;
    private EditText commentEditText;
    private final String TAG = "NewMessageActivity";
    private List<String> images;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        setContentView(R.layout.layout_new_message);
        imagesGridView = (GridView) findViewById(R.id.new_message_images_gv);
        markPointTextView = (TextView) findViewById(R.id.new_message_mark_point_et);
        commentEditText = (EditText) findViewById(R.id.new_message_comment_et);
        imagesGridView.setAdapter(new ImageAdapter());
    }

    public class ImageAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            if (images == null || images.size() == 0)
                return 1;
            else {
                return images.size() + 1;
            }
        }

        @Override
        public Object getItem(int i) {
            if (images == null || i >= images.size()) {
                return null;
            }
            return images.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            if (view == null) {
                ImageView imageView = new ImageView(NewMessageActivity.this);
                float dimension = NewMessageActivity.this.getResources().getDimension(R.dimen.message_item_image_icon_size);
                Log.d(TAG, "dimension:" + dimension);

                AbsListView.LayoutParams layoutParams = new AbsListView.LayoutParams((int) dimension, (int) dimension);
                imageView.setLayoutParams(layoutParams);
                imageView.setScaleType(ImageView.ScaleType.CENTER);
                if (images == null || i == images.size()) {
                    imageView.setImageResource(R.drawable.add_images);
                    imageView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            addNewImages(view);
                        }
                    });
                }
                return imageView;
            } else {
                ImageView imageView = (ImageView) view;
                return imageView;
            }
        }
    }

    private void addNewImages(View view) {
        Log.d(TAG, "show add new images");
    }

    public void selectMarkPointDate(View view) {
        final Calendar calendar = Calendar.getInstance();
        int currentYear = calendar.get(Calendar.YEAR);
        int currentMonth = calendar.get(Calendar.MONTH);
        int currentDayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
        final int currentHourOfDay = calendar.get(Calendar.HOUR_OF_DAY);
        final int currentMinute = calendar.get(Calendar.MINUTE);

        new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, final int year, final int month, final int dayOfMonth) {
                new TimePickerDialog(NewMessageActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int hourOfDay, int minute) {
                        Calendar calendar1 = Calendar.getInstance();
                        calendar1.set(Calendar.YEAR, year);
                        calendar1.set(Calendar.MONTH, month);
                        calendar1.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                        calendar1.set(Calendar.HOUR_OF_DAY, hourOfDay);
                        calendar1.set(Calendar.MINUTE, minute);
                        date = calendar1.getTime();
                        markPointTextView.setText(date.toString());
                    }
                }, currentHourOfDay, currentMinute, true).show();
            }
        }, currentYear, currentMonth, currentDayOfMonth) {
            @Override
            protected void onStop() {
//                super.onStop();
            }
        }.show();
    }

    public void submit() {
        String comment = commentEditText.getText().toString();
        String alertText = "";
        if (TextUtils.isEmpty(comment)) {
            alertText += "Comment can not empty!";
        }
        if (TextUtils.isEmpty(alertText)) {
            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setMessage("正在提交...");
            progressDialog.show();
            final MessageData messageData = new MessageData();
            messageData.setContent(comment);
            messageData.setUserId(MessageImageManager.getUserId(this));
            Gson gson = new Gson();
            String json = gson.toJson(messageData);
            Log.d(TAG, "json:" + json);
            AsyncHttpClient asyncHttpClient = new AsyncHttpClient("");
            RequestParams requestParams = new RequestParams();
            requestParams.put("messageDataString", json);
            asyncHttpClient.post(this, NetWorkConstant.CREATE_NEW_MESSAGE, requestParams, new AsyncHttpResponseHandler() {
                @Override
                public void onSuccess(String s) {
                    Log.d(TAG, "response: " + s);
                    Toast.makeText(NewMessageActivity.this, "提交成功，新的 message id 为" + s, Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                    Intent intent = new Intent();
                    intent.putExtra("message", messageData);
                    setResult(CREATE_NEW_MESSAGE_SUCCESS, intent);
                    finish();
                }

                @Override
                public void onFailure(Throwable throwable) {
                    super.onFailure(throwable);
                    progressDialog.dismiss();
                    Toast.makeText(NewMessageActivity.this, "联网失败！", Toast.LENGTH_SHORT).show();
                }
            });
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = new MenuInflater(this);
        menuInflater.inflate(R.menu.new_message, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        switch (itemId) {
            case R.id.menu_new_message_send:
                submit();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
