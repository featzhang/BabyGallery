package club.guadazi.babygallery.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.ViewFlipper;

import club.guadazi.babygallery.R;
import club.guadazi.babygallery.provider.backmission.ImageManager;
import club.guadazi.babygallery.provider.entity.MessageData;

public class ShowImageDetailActivity extends Activity {

    private static final String TAG = "ShowImageDetailActivity";
    private ImageSwitcher viewPager;
    private ImageView processingImageView;
    private MessageData message;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.image_detail_layout);
        viewPager = (ImageSwitcher) findViewById(R.id.image_detail_vp);
//        processingImageView = (ImageView) findViewById(R.id.image_detail_processing_iv);

        getActionBar().hide();
        Intent intent = getIntent();
        if (intent == null) {
            return;
        }
        message = intent.getParcelableExtra("message");
        int imageIndex = intent.getIntExtra("selectImageIndex", 0);
        if (message != null) {
            String imageIds = message.getImageIds();
            if (!TextUtils.isEmpty(imageIds)) {
                String[] strings = imageIds.split(MessageData.IMAGE_ID_SEPERATER);
                for (String string : strings) {
                    int imageId;
                    try {
                        imageId = Integer.parseInt(string);
                    } catch (Exception e) {
                        Log.d(TAG, "parse image id string to int error, imageId=" + string);
                        return;
                    }
                    ImageView imageView = new ImageView(this);
                    ImageManager.setThumbnail(this, imageId, imageView);
                    viewPager.addView(imageView);
                }
            }
        }
        viewPager.setSelected(true);
//        viewPager.setCurrentItem(imageIndex);
    }

    class MyViewPagerAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return 0;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            return null;
        }
    }
}
