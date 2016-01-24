package club.guadazi.babygallery.view;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.ArrayList;

import club.guadazi.babygallery.R;
import club.guadazi.babygallery.provider.entity.MessageData;
import club.guadazi.babygallery.util.ConstantValues;
import club.guadazi.babygallery.util.yueyueniao.ImageManager2;

public class ShowMessageDetailActivity extends Activity {
    private final String TAG = "ShowMessageDetailAct";
    private ViewPager mViewPager = null;
    private LinearLayout mViewGroup = null;

    private int[] mImageIds;

    private ImageView[] mImageViews = null;

    private ImageView[] mTips = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_image_detail);

        mViewGroup = (LinearLayout) findViewById(R.id.viewGroup);
        mViewPager = (ViewPager) findViewById(R.id.viewPager);

        Intent intent = getIntent();
        MessageData message = intent.getParcelableExtra("message");
        if (message != null) {
            String imageIds = message.getImageIds();
            if (!TextUtils.isEmpty(imageIds)) {
                ArrayList<Integer> tmpInts = new ArrayList<Integer>();
                String[] strings = imageIds.split(MessageData.IMAGE_ID_SEPERATER);
                if (strings != null && strings.length > 0) {
                    for (String string : strings) {
                        try {
                            int i = Integer.parseInt(string);
                            tmpInts.add(i);
                        } catch (Exception e) {
                            Log.e(TAG, e.getMessage());
                        }
                    }
                }
                if (tmpInts.size() > 0) {
                    mImageIds = new int[tmpInts.size()];
                    for (int i = 0; i < tmpInts.size(); i++) {
                        mImageIds[i] = tmpInts.get(i);
                    }
                }
            }
        }

        if (mImageIds == null) {
            mImageIds = new int[0];
        }
        mTips = new ImageView[mImageIds.length];
        //动态创建小点并加到布局中
        for (int i = 0; i < mTips.length; i++) {
            ImageView iv = new ImageView(this);
            iv.setLayoutParams(new LayoutParams(20, 20));
            mTips[i] = iv;

            if (i == 0) {
                iv.setBackgroundResource(R.drawable.page_indicator_focused);
            } else {
                iv.setBackgroundResource(R.drawable.page_indicator_unfocused);
            }
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
            lp.leftMargin = 5;
            lp.rightMargin = 5;
            mViewGroup.addView(iv, lp);
        }
        mImageViews = new ImageView[mImageIds.length];
        for (int i = 0; i < mImageViews.length; i++) {
            ImageView iv = new ImageView(this);
            mImageViews[i] = iv;
//            int reqWidth = getWindowManager().getDefaultDisplay().getWidth();
//            int reqHeight = getWindowManager().getDefaultDisplay().getHeight();
            ImageManager2.from(this).displayImage(iv, ConstantValues.getRequestImageUrl(this, mImageIds[i]), -1);
//            iv.setImageBitmap(decodeSampledBitmapFromResource(getResources(), mImageIds[i], reqWidth, reqHeight));
        }

        mViewPager.setAdapter(new MyPagerAdapter());
        mViewPager.setOnPageChangeListener(new MyOnPageChangeListener());
    }

    class MyPagerAdapter extends PagerAdapter {
        @Override
        public int getCount() {
            return mImageIds.length;
        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == arg1;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            try {
                container.addView(mImageViews[position]);
            } catch (Exception e) {
            }
            return mImageViews[position];
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
        }
    }


    private static Bitmap decodeSampledBitmapFromResource(Resources res, int resId, int reqWidth, int reqHeight) {
        BitmapFactory.Options opts = new BitmapFactory.Options();
        opts.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(res, resId);
        int inSampleSize = cacluateInSampledSize(opts, reqWidth, reqHeight);
        opts.inSampleSize = inSampleSize;
        opts.inJustDecodeBounds = false;
        return BitmapFactory.decodeResource(res, resId, opts);
    }

    private static int cacluateInSampledSize(BitmapFactory.Options opts, int width, int height) {
        if (opts == null) {
            return 1;
        }
        int inSampleSize = 1;
        int realWidth = opts.outWidth;
        int realHeight = opts.outHeight;

        if (realWidth > width || realHeight > height) {
            int heightRatio = realHeight / height;
            int widthRatio = realWidth / width;

            inSampleSize = (widthRatio > heightRatio) ? heightRatio : widthRatio;
        }
        return inSampleSize;
    }

    public class MyOnPageChangeListener implements OnPageChangeListener {

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {

            for (int i = 0; i < mTips.length; i++) {
                if (position == i) {
                    mTips[i].setBackgroundResource(R.drawable.page_indicator_focused);
                } else {
                    mTips[i].setBackgroundResource(R.drawable.page_indicator_unfocused);
                }
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    }
}