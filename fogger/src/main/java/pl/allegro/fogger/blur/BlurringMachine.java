package pl.allegro.fogger.blur;


import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;

import java.io.File;

import pl.allegro.fogger.FoggerConfig;
import pl.allegro.fogger.utils.ImageUtils;

public abstract class BlurringMachine {

    private static final String TAG = BlurringImageTask.class.getName();

    private File blurredImage;
    private Context context;

    public BlurringMachine(Context context) {
        this.context = context;
    }

    protected abstract Bitmap blur(Context context, Bitmap bitmapToBlur, int radius);

    public Bitmap blur(Bitmap bitmapToBlur) {
        try {
            Log.d(TAG, "Start Blurring task");
            blurredImage = new File(context.getFilesDir() + FoggerConfig.BLURRED_SCREENSHOT_FILE_NAME);
            Bitmap blurredBitmap = blur(context, bitmapToBlur, FoggerConfig.getBackgroundBlurRadius());
            ImageUtils.storeImageAsPNG(blurredBitmap, blurredImage, false);
            return blurredBitmap;
        } finally {
            bitmapToBlur.recycle();
        }
    }

}
