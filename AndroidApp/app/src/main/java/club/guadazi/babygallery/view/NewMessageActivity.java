package club.guadazi.babygallery.view;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
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

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import club.guadazi.babygallery.R;
import club.guadazi.babygallery.provider.entity.MISSION_TYPE;
import club.guadazi.babygallery.provider.entity.MessageData;
import club.guadazi.babygallery.provider.entity.MissionEntity;
import club.guadazi.babygallery.provider.entity.MissionManager;
import club.guadazi.babygallery.provider.sync.ImageLocalManager;
import club.guadazi.babygallery.provider.sync.MessageManager;
import club.guadazi.babygallery.util.ConstantValues;
import club.guadazi.babygallery.util.ImageTool;

public class NewMessageActivity extends Activity {
    public static final int CREATE_NEW_MESSAGE_SUCCESS = 0x01;
    private static final int SELECT_PIC_KITKAT = 0x11;
    private static final int SELECT_PIC = 0x12;

    private Date date;
    private GridView imagesGridView;
    private TextView markPointTextView;
    private EditText contentEditText;
    private final String TAG = "NewMessageActivity";
    private List<String> images;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        setContentView(R.layout.layout_new_message);
        imagesGridView = (GridView) findViewById(R.id.new_message_images_gv);
        markPointTextView = (TextView) findViewById(R.id.new_message_mark_point_et);
        contentEditText = (EditText) findViewById(R.id.new_message_content_et);
        imagesGridView.setAdapter(new ImageAdapter());
    }


    private void addNewImages(View view) {
        Log.d(TAG, "show add new images");
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);//ACTION_OPEN_DOCUMENT
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("image/jpeg");
        Intent chooser = Intent.createChooser(intent, "添加图片");
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
            startActivityForResult(chooser, SELECT_PIC_KITKAT);
        } else {
            startActivityForResult(chooser, SELECT_PIC);
        }


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
        String content = contentEditText.getText().toString();
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("正在提交...");
        progressDialog.show();
        final MessageData messageData = new MessageData();
        messageData.setContent(content);
        messageData.setUserId(ConstantValues.getUserId(this));
        messageData.setUpdateTime(new Timestamp(System.currentTimeMillis()));
        if (date != null)
            messageData.setMarkPoint(new Timestamp(date.getTime()));
        if (images != null && images.size() > 0) {
            List<Long> longs = ImageLocalManager.addNewImages(this, images);
            String imageString = "";
            for (int i = 0; i < longs.size(); i++) {
                if (i == 0) {
                    imageString = longs.get(i) + "";
                } else {
                    imageString += MessageData.IMAGE_ID_SEPERATER + longs.get(i);
                }
            }
            messageData.setImageIds(imageString);
        }

        Gson gson = new Gson();
        String json = gson.toJson(messageData);
        Log.d(TAG, "json:" + json);
        AsyncHttpClient asyncHttpClient = new AsyncHttpClient("");
        RequestParams requestParams = new RequestParams();
        requestParams.put("messageDataString", json);
        asyncHttpClient.post(this, ConstantValues.CREATE_NEW_MESSAGE, requestParams, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(String s) {
                Log.d(TAG, "response: " + s);
                Toast.makeText(NewMessageActivity.this, "提交成功，新的 message id 为" + s, Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
                MessageManager.addMessageToLocalDB(NewMessageActivity.this, messageData);
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
                long index = MessageManager.addMessageToLocalDB(NewMessageActivity.this, messageData);
                MissionManager.addNewMission(NewMessageActivity.this, new MissionEntity(MISSION_TYPE.ADD_MESSAGE, index + ""));
            }
        });

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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != RESULT_OK) {
            Log.e(TAG, "select picture result error!");
            return;
        }
       /* Bitmap bm = null;
        //外界的程序访问ContentProvider所提供数据 可以通过ContentResolver接口
        ContentResolver resolver = getContentResolver();
        //此处的用于判断接收的Activity是不是你想要的那个
        if (requestCode == SELECT_PIC || requestCode == SELECT_PIC_KITKAT) {
            try {
                Uri originalUri = data.getData();        //获得图片的uri
                bm = MediaStore.Images.Media.getBitmap(resolver, originalUri);
                //显得到bitmap图片                这里开始的第二部分，获取图片的路径：
                String[] proj = {MediaStore.Images.Media.DATA};
                //好像是android多媒体数据库的封装接口，具体的看Android文档
                Cursor cursor = managedQuery(originalUri, proj, null, null, null);
                //按我个人理解 这个是获得用户选择的图片的索引值
                int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                //将光标移至开头 ，这个很重要，不小心很容易引起越界
                cursor.moveToFirst();
                //最后根据索引值获取图片路径
                String path = cursor.getString(column_index);
                Log.i(TAG, "select image PATH" + path);
            } catch (IOException e) {
                Log.e(TAG, e.toString());
            } }*/

        String path = PicturesURLTool.getPath(this, data.getData());
        Log.i(TAG, "select image PATH: " + path);
        if (images == null) {
            images = new ArrayList<String>();
        }
        images.add(path);
        ImageAdapter imageAdapter = (ImageAdapter) imagesGridView.getAdapter();
        imageAdapter.notifyDataSetChanged();
    }


///////////////////////////////////////////////////////////

    public class ImageAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            if (images == null || images.size() == 0)
                return 1;
            else {
                int size = images.size();
                if (size < ConstantValues.max_images) {
                    return size + 1;
                } else {
                    return size;
                }
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

            ImageView imageView = new ImageView(NewMessageActivity.this);
            float dimension = NewMessageActivity.this.getResources().getDimension(R.dimen.message_item_image_icon_size);
            Log.d(TAG, "dimension:" + dimension);

            AbsListView.LayoutParams layoutParams = new AbsListView.LayoutParams((int) dimension, (int) dimension);
            imageView.setLayoutParams(layoutParams);
            imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
            if (images == null || i == images.size()) {
                imageView.setImageResource(R.drawable.add_images);
                imageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        addNewImages(view);
                    }
                });
            } else {
                Drawable thumbnailByImagePath = ImageLocalManager.getThumbnailByImagePath(NewMessageActivity.this, images.get(i));
                imageView.setImageDrawable(thumbnailByImagePath);
            }
            return imageView;

        }
    }
}
