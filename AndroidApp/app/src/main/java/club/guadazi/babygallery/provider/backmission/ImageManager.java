package club.guadazi.babygallery.provider.backmission;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import club.guadazi.babygallery.net.DownloadImageAsyncTask;
import club.guadazi.babygallery.provider.entity.ImageEntity;
import club.guadazi.babygallery.provider.sync.ImageFileManager;
import club.guadazi.babygallery.util.ConstantValues;

/**
 * 图片管理的任务<br/>
 */
public class ImageManager {
    private static final String TAG = "ImageManager";
    private static Map<Integer, List<OnFinishListener>> finishListerMap = new HashMap<Integer, List<OnFinishListener>>();

    interface OnFinishListener {
        void onFinish();
    }

    public static void setThumbnail(Context context, int remoteImageId, ImageView imageView) {
        setImageValue(context, remoteImageId, imageView, 0);
    }

    public static void setImage(Context context, int remoteImageId, ImageView imageView) {
        setImageValue(context, remoteImageId, imageView, 1);
    }

    private static void setImageValue(final Context context, final int remoteImageId, final ImageView imageView, final int mode) {
        Log.i(TAG, "ImageManager.setImageValue remoteImageId=" + remoteImageId + "\t");
        if (mode == 0) {
            Drawable thumbnailDrawable = ImageFileManager.getThumbnailDrawable(context, remoteImageId);
            if (thumbnailDrawable != null) {
                imageView.setImageDrawable(thumbnailDrawable);
                return;
            }
        } else {
//                    imageView.setImageDrawable(ImageFileManager.getThumbnailDrawable(context, remoteImageId));
        }
        BackMissionManager.MissionState missionState = BackMissionManager.requestImage(remoteImageId);
        OnFinishListener onFinishListener = new OnFinishListener() {
            @Override
            public void onFinish() {
                Log.i(TAG, "ImageManager.OnFinishListener.OnFinish set image: remote image id=" + remoteImageId);
                if (mode == 0) {
                    Drawable thumbnailDrawable = ImageFileManager.getThumbnailDrawable(context, remoteImageId);
                    Log.i(TAG, "thumbnailDrawable=" + thumbnailDrawable);
                    imageView.setImageDrawable(thumbnailDrawable);
                } else {
//                    imageView.setImageDrawable(ImageFileManager.getThumbnailDrawable(context, remoteImageId));
                }
            }
        };
        switch (missionState) {
            case NOT_BEGIN:
                List<OnFinishListener> onFinishListeners = new ArrayList<OnFinishListener>();
                onFinishListeners.add(onFinishListener);
                finishListerMap.put(remoteImageId, onFinishListeners);
                BackMissionManager.setImageRequestState(remoteImageId, BackMissionManager.MissionState.WORKING);
                Log.i(TAG, "set state working: remoteImageId=" + remoteImageId);

                new DownloadImageAsyncTask(context) {

                    @Override
                    public void onFinish(ImageEntity imageEntity) {
                        BackMissionManager.setImageRequestState(remoteImageId, BackMissionManager.MissionState.END);
                        Log.i(TAG, "set state working: remoteImageId=" + remoteImageId);

                        List<OnFinishListener> onFinishListeners1 = finishListerMap.get(remoteImageId);
                        if (onFinishListeners1 != null) {
                            for (OnFinishListener onFinishListener : onFinishListeners1) {
                                onFinishListener.onFinish();
                            }
                        }
                    }

                }.execute(ConstantValues.REQUEST_IMAGE_NIC, remoteImageId + "", ConstantValues.getUserId(context) + "");

                break;
            case WORKING:
                List<OnFinishListener> onFinishListeners1 = finishListerMap.get(remoteImageId);
                onFinishListeners1.add(onFinishListener);
                Log.i(TAG, "get state working: remoteImageId=" + remoteImageId + " add to onFinishListener");

                finishListerMap.put(remoteImageId, onFinishListeners1);
                break;
            case END:
                onFinishListener.onFinish();
                break;
        }
    }
}
