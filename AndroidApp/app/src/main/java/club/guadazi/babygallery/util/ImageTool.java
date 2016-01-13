package club.guadazi.babygallery.util;

import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

/**
 * Created by aaron on 16-1-8.
 */
public class ImageTool {
    public static Drawable resizeImage(Bitmap bitmap, int w, int h) {
        if (bitmap == null) {
            return null;
        }
        Bitmap BitmapOrg = bitmap;
        int width = BitmapOrg.getWidth();
        int height = BitmapOrg.getHeight();
        int newWidth = w;
        int newHeight = h;

        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;

        scaleWidth = scaleWidth > scaleHeight ? scaleHeight : scaleWidth;
        scaleHeight = scaleWidth > scaleHeight ? scaleHeight : scaleWidth;
        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleHeight);
        // if you want to rotate the Bitmap
        // matrix.postRotate(45);
        Bitmap resizedBitmap = Bitmap.createBitmap(BitmapOrg, 0, 0, width,
                height, matrix, true);
        return new BitmapDrawable(resizedBitmap);
    }
}
