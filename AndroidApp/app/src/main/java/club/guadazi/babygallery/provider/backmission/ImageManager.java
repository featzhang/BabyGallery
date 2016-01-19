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
    private static Map<String, List<OnFinishListener>> finishListerMap = new HashMap<String, List<OnFinishListener>>();
    private final static int THUMB_MODE = 0;
    private final static int IMAGE_MODE = 1;

    interface OnFinishListener {
        void onFinish();
    }

    public static void setThumbnail(Context context, int remoteImageId, ImageView imageView) {
        setImageValue(context, remoteImageId, imageView, THUMB_MODE);
    }

    public static void setImage(Context context, int remoteImageId, ImageView imageView) {
        setImageValue(context, remoteImageId, imageView, IMAGE_MODE);
    }

    private static void setImageValue(final Context context, final int remoteImageId, final ImageView imageView, final int mode) {
        Log.i(TAG, "ImageManager.setImageValue remoteImageId=" + remoteImageId + "\t" + "mode=" + mode);
        if (mode == THUMB_MODE) {
            Drawable thumbnailDrawable = ImageFileManager.getThumbnailDrawable(context, remoteImageId);
            if (thumbnailDrawable != null) {
                imageView.setImageDrawable(thumbnailDrawable);
                return;
            }
        } else if (mode == IMAGE_MODE) {
            Drawable imageDrawable = ImageFileManager.getImageDrawable(context, remoteImageId);
            if (imageDrawable != null) {
                imageView.setImageDrawable(imageDrawable);
                return;
            }
        }
        BackMissionManager.MissionState missionState = null;
        if (mode == IMAGE_MODE) {
            missionState = BackMissionManager.requestImage(remoteImageId);
        } else if (mode == THUMB_MODE) {
            missionState = BackMissionManager.requestThumbnail(remoteImageId);
        }
        OnFinishListener onFinishListener = new OnFinishListener() {
            @Override
            public void onFinish() {
                Log.i(TAG, "ImageManager.OnFinishListener.OnFinish set image: remote image id=" + remoteImageId + "\tmode=" + mode);
                if (mode == THUMB_MODE) {
                    Drawable thumbnailDrawable = ImageFileManager.getThumbnailDrawable(context, remoteImageId);
                    Log.i(TAG, "thumbnailDrawable=" + thumbnailDrawable);
                    imageView.setImageDrawable(thumbnailDrawable);
                } else if (mode == IMAGE_MODE) {
                    Drawable imageDrawable = ImageFileManager.getImageDrawable(context, remoteImageId);
                    Log.i(TAG, "imageDrawable=" + imageDrawable);
                    imageView.setImageDrawable(imageDrawable);
                }
            }
        };
        switch (missionState) {
            case NOT_BEGIN:
                List<OnFinishListener> onFinishListeners = new ArrayList<OnFinishListener>();
                onFinishListeners.add(onFinishListener);
                finishListerMap.put(remoteImageId + "_" + mode, onFinishListeners);
                if (mode == IMAGE_MODE) {
                    BackMissionManager.setImageRequestState(remoteImageId, BackMissionManager.MissionState.WORKING);
                } else if (mode == THUMB_MODE) {
                    BackMissionManager.setThumbnailRequestState(remoteImageId, BackMissionManager.MissionState.WORKING);

                }
                Log.i(TAG, "set state working: remoteImageId=" + remoteImageId);

                new DownloadImageAsyncTask(context) {

                    @Override
                    public void onFinish(ImageEntity imageEntity) {
                        if (mode == IMAGE_MODE)
                            BackMissionManager.setImageRequestState(remoteImageId, BackMissionManager.MissionState.END);
                        else if (mode == THUMB_MODE) {
                            BackMissionManager.setThumbnailRequestState(remoteImageId, BackMissionManager.MissionState.END);

                        }
                        Log.i(TAG, "set state working: remoteImageId=" + remoteImageId);

                        List<OnFinishListener> onFinishListenersTHUMBNAIL_MODE = finishListerMap.get(remoteImageId + "_" + mode);
                        if (onFinishListenersTHUMBNAIL_MODE != null) {
                            for (OnFinishListener onFinishListener : onFinishListenersTHUMBNAIL_MODE) {
                                onFinishListener.onFinish();
                            }
                        }
                    }

                }.execute(mode == IMAGE_MODE ? ConstantValues.REQUEST_IMAGE : ConstantValues.REQUEST_IMAGE_NIC, remoteImageId + "", ConstantValues.getUserId(context) + "");

                break;
            case WORKING:
                List<OnFinishListener> onFinishListenersTHUMBNAIL_MODE = finishListerMap.get(remoteImageId);
                onFinishListenersTHUMBNAIL_MODE.add(onFinishListener);
                Log.i(TAG, "get state working: remoteImageId=" + remoteImageId + " add to onFinishListener");

                finishListerMap.put(remoteImageId + "_" + mode, onFinishListenersTHUMBNAIL_MODE);
                break;
            case END:
                onFinishListener.onFinish();
                break;
        }
    }
}
