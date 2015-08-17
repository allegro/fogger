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

package pl.allegro.fogger.ui;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.View;
import pl.allegro.fogger.FoggerConfig;
import pl.allegro.fogger.blur.BlurringImageListener;
import pl.allegro.fogger.utils.ScreenShooter;
import pl.allegro.fogger.blur.BlurringMachineFactory;
import pl.allegro.fogger.utils.ImageUtils;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func0;
import rx.schedulers.Schedulers;
import rx.util.async.Async;

public class BlurredBackgroundAdapter {

    private static final String TAG = BlurredBackgroundAdapter.class.getName();

    protected enum BlurredBackgroundAdapterState {
        WORKING,
        RESETTED,
        IDLE,
        READY
    }

    private static BlurredBackgroundAdapter instance;

    protected static String internalFilesDirPath;
    protected ImageUtils imageUtils;
    protected BlurringMachineFactory blurringMachineFactory;
    protected ScreenShooter screenShooter;

    protected BlurredBackgroundAdapterState state = BlurredBackgroundAdapterState.IDLE;
    private BlurringImageListener blurringImageListener;
    private Bitmap blurredImage;

    public static synchronized BlurredBackgroundAdapter getInstance(Application application) {
        if (instance == null) {
            instance = new BlurredBackgroundAdapter();
        }
        instance.internalFilesDirPath = application.getFilesDir().getPath();
        return instance;
    }

    private BlurredBackgroundAdapter() {
        imageUtils = new ImageUtils();
        blurringMachineFactory = new BlurringMachineFactory();
        screenShooter = new ScreenShooter();
    }

    public synchronized void prepareBlurredBackgroundForActivity(Activity activity) {
        prepareAdapterToStartBlurringBackgroundProcess();
        blur(activity, () -> screenShooter.createScreenShot(activity));
    }

    private synchronized void prepareAdapterToStartBlurringBackgroundProcess() {
        if (state == BlurredBackgroundAdapterState.WORKING) {
            throw new IllegalStateException("BlurredBackgroundAdapter already working, "
                    + "it can not handle more then one pl.allegro.fogger.pl.blur request.");
        }
        state = BlurredBackgroundAdapterState.WORKING;
        leaveCurrentBlurredImage();
    }

    private void blur(Context context, Func0<Bitmap> screenShotProvider) {
        Async.start(screenShotProvider)
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .map(screenShot -> BlurringMachineFactory.create(context).blur(screenShot))
                .subscribe(blurredBitmap -> onBlurringFinish(blurredBitmap));
    }

    public synchronized void prepareBlurredBackgroundForView(Context context, View viewToBlur) {
        prepareAdapterToStartBlurringBackgroundProcess();
        blur(context, () -> screenShooter.createScreenShot(viewToBlur));
    }

    //visiblefortesting
    protected synchronized void onBlurringFinish(Bitmap blurredImage) {
        if (state == BlurredBackgroundAdapterState.RESETTED) {
            Log.i(TAG, "BlurringAdapter was reseted, so I recycle created bitmap and reset BlurringAdapterState.");
            blurredImage.recycle();
            blurringImageListener = null;
        }
        state = BlurredBackgroundAdapterState.READY;
        this.blurredImage = blurredImage;
        if(blurringImageListener != null) {
            blurringImageListener.onBlurringFinish(blurredImage);
        }
    }

    public synchronized void reset() {
        leaveCurrentBlurredImage();
        blurringImageListener = null;
        state = BlurredBackgroundAdapterState.RESETTED;
    }

    private void leaveCurrentBlurredImage() {
        if (blurredImage != null) {
            blurredImage.recycle();
            blurredImage = null;
        }
    }

    public synchronized void resetBlurringListener(BlurringImageListener listener) {
        if (state == BlurredBackgroundAdapterState.WORKING) {
            blurringImageListener = listener;
        } else if (state == BlurredBackgroundAdapterState.READY && listener != null && blurredImage != null) {
            listener.onBlurringFinish(blurredImage);
        } else if (state != BlurredBackgroundAdapterState.RESETTED && listener != null) {
            Log.w(TAG, "Something was wrong. There isn't any ready blurred background"
                    + " Thus I try to restore some from internal storage.");
            leaveCurrentBlurredImage();
            blurredImage = imageUtils.createBitmapFromFile(internalFilesDirPath
                                                            + FoggerConfig.BLURRED_SCREENSHOT_FILE_NAME);
            listener.onBlurringFinish(blurredImage);
        }
    }
}
