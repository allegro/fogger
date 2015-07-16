/*
 * Copyright 2014 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package pl.allegro.fogger.blur;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import pl.allegro.fogger.FoggerConfig;
import pl.allegro.fogger.utils.SafeAsyncTask;
import pl.allegro.fogger.utils.ImageUtils;

import java.io.File;

public class BlurringImageTask extends SafeAsyncTask<Bitmap> {

    private static final String TAG = BlurringImageTask.class.getName();

    private File blurredImage;
    private Context context;
    private BlurringImageListener blurringImageListener;
    private Bitmap bitmapToBlur;
    private Blur blur = new Blur();

    BlurringImageTask(Context context, BlurringImageListener blurringImageListener, Bitmap bitmapToBlur) {
        blurredImage = new File(context.getFilesDir() + FoggerConfig.BLURRED_SCREENSHOT_FILE_NAME);
        this.context = context;
        this.blurringImageListener = blurringImageListener;
        this.bitmapToBlur = bitmapToBlur;
    }

    @Override
    public Bitmap call() {
        Log.d(TAG, "Start Blurring task");
        Bitmap blurredBitmap = blur.fastblur(context, bitmapToBlur, FoggerConfig.getBackgroundBlurRadius());
        ImageUtils.storeImageAsPNG(blurredBitmap, blurredImage, false);
        return blurredBitmap;
    }

    @Override
    protected void onSuccess(Bitmap blurredBitmap) {
        blurringImageListener.onBlurringFinish(blurredBitmap);
    }

    @Override
    protected void onFinally() {
        bitmapToBlur.recycle();
    }

    public interface BlurringImageListener {
        void onBlurringFinish(Bitmap blurredImage);
    }
}
