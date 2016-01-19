package club.guadazi.babygallery.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ViewFlipper;

import club.guadazi.babygallery.R;
import club.guadazi.babygallery.provider.backmission.ImageManager;
import club.guadazi.babygallery.provider.entity.MessageData;

public class ShowImageDetailActivity extends Activity {

    private static final String TAG = "ShowImageDetailActivity";
    private ViewFlipper viewFlipper;
    private MessageData message;
    private int selectImageIndex;
    private int[] imageIds;
    private GestureDetector gestureDetector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.image_detail_layout);
        viewFlipper = (ViewFlipper) findViewById(R.id.image_detail_vp);


        getActionBar().hide();
        Intent intent = getIntent();
        if (intent == null) {
            return;
        }
        gestureDetector = new GestureDetector(this, new MyOnGestureListener());
        message = intent.getParcelableExtra("message");
        selectImageIndex = intent.getIntExtra("selectImageIndex", 0);
        if (message != null) {
            String messageImageIds = message.getImageIds();
            if (!TextUtils.isEmpty(messageImageIds)) {
                String[] imageIdStrings = messageImageIds.split(MessageData.IMAGE_ID_SEPERATER);
                if (imageIdStrings.length > 0) {
                    imageIds = new int[imageIdStrings.length];
                }
                for (int i = 0; i < imageIdStrings.length; i++) {
                    String imageIdString = imageIdStrings[i];
                    try {
                        int i1 = Integer.parseInt(imageIdString);
                        imageIds[i] = i1;
                        ImageView imageView = new ImageView(this);
                        imageView.setScaleType(ImageView.ScaleType.FIT_XY);

                        ImageManager.setThumbnail(this, i1, imageView);
                        ImageManager.setImage(this, i1, imageView);
                        viewFlipper.addView(imageView, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT,
                                LinearLayout.LayoutParams.FILL_PARENT));
                    } catch (Exception e) {

                    }
                }
            }
        }
        viewFlipper.setSelected(true);
    }

    public class MyOnGestureListener implements GestureDetector.OnGestureListener {

        @Override
        public boolean onDown(MotionEvent e) {
            return false;
        }

        @Override
        public void onShowPress(MotionEvent e) {

        }

        @Override
        public boolean onSingleTapUp(MotionEvent e) {
            return false;
        }

        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            return false;
        }

        @Override
        public void onLongPress(MotionEvent e) {

        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            if (e2.getX() - e1.getX() > 120) { // 从左向右滑动（左进右出）
                Animation rInAnim = AnimationUtils.loadAnimation(ShowImageDetailActivity.this,
                        R.anim.push_left_in); // 向右滑动左侧进入的渐变效果（alpha 0.1 -> 1.0）
                Animation rOutAnim = AnimationUtils.loadAnimation(ShowImageDetailActivity.this,
                        R.anim.push_right_out); // 向右滑动右侧滑出的渐变效果（alpha 1.0 -> 0.1）

                viewFlipper.setInAnimation(rInAnim);
                viewFlipper.setOutAnimation(rOutAnim);
                viewFlipper.showPrevious();
                return true;
            } else if (e2.getX() - e1.getX() < -120) { // 从右向左滑动（右进左出）
                Animation lInAnim = AnimationUtils.loadAnimation(ShowImageDetailActivity.this,
                        R.anim.push_left_in); // 向左滑动左侧进入的渐变效果（alpha 0.1 -> 1.0）
                Animation lOutAnim = AnimationUtils.loadAnimation(ShowImageDetailActivity.this,
                        R.anim.push_left_out); // 向左滑动右侧滑出的渐变效果（alpha 1.0 -> 0.1）

                viewFlipper.setInAnimation(lInAnim);
                viewFlipper.setOutAnimation(lOutAnim);
                viewFlipper.showNext();
                return true;
            }
            return true;
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        viewFlipper.stopFlipping(); // 点击事件后，停止自动播放
        viewFlipper.setAutoStart(false);
        return gestureDetector.onTouchEvent(event); // 注册手势事件
    }
}
