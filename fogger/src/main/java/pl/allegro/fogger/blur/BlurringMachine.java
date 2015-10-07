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

import java.io.File;

import pl.allegro.fogger.FoggerConfig;
import pl.allegro.fogger.utils.ImageUtils;

public abstract class BlurringMachine {

    private static final String TAG = BlurringMachine.class.getName();

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
