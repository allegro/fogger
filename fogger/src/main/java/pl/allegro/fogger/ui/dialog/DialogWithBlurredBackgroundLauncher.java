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

package pl.allegro.fogger.ui.dialog;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import pl.allegro.fogger.FoggerConfig;
import pl.allegro.fogger.R;
import pl.allegro.fogger.blur.BlurringImageListener;
import pl.allegro.fogger.ui.BlurredBackgroundAdapter;

public class DialogWithBlurredBackgroundLauncher implements BlurringImageListener {

    private static final String TAG = DialogWithBlurredBackgroundLauncher.class.getName();
    private static final float FADE_IN_ANIMATION_END_VALUE = 1.0f;
    private static final String FADE_IN_ANIMATION_PARAMETER = "alpha";

    protected BlurredBackgroundAdapter blurredBackgroundAdapter;

    private int blurAnimationDuration = FoggerConfig.DIALOG_BACKGROUND_FADE_IN_ANIMATION_DURATION;
    private ImageView blurImageView;
    private Dialog dialog;

    private final Activity activity;

    public static void showDialog(Activity activity, Dialog dialog) {
       new DialogWithBlurredBackgroundLauncher(activity).showDialog(dialog);
    }

    public DialogWithBlurredBackgroundLauncher(Activity activity) {
        this.activity = activity;
        blurredBackgroundAdapter = BlurredBackgroundAdapter.getInstance(activity.getApplication());
    }

    public Dialog showDialog(Dialog dialog) {
        this.dialog = dialog;
        try {
            blurredBackgroundAdapter.prepareBlurredBackgroundForActivity(activity);
            setBlurBackground();
            addBlurredLayer();
            turnOffNativeBackgroundBehaviour(dialog);
        } catch(RuntimeException e) {
            Log.e(TAG, "Error during preparing blurred background", e);
            blurredBackgroundAdapter.reset();
        }

        dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialogInterface) {
                ViewGroup decorView = (ViewGroup) activity.getWindow().getDecorView();
                decorView.removeView(blurImageView);
            }
        });
        dialog.show();
        return dialog;
    }

    private void turnOffNativeBackgroundBehaviour(Dialog dialog) {
        dialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
    }

    private void setBlurBackground() {
        blurredBackgroundAdapter.resetBlurringListener(this);
    }

    private void addBlurredLayer() {
        ViewGroup decorView = (ViewGroup) activity.getWindow().getDecorView();
        blurImageView = (ImageView) activity.getLayoutInflater().inflate(R.layout.image, decorView, false);
        moveBlurredLayerBelowStatusBar();
        decorView.addView(blurImageView);
    }

    private void moveBlurredLayerBelowStatusBar() {
        int resourceId = activity.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            int systemStatusBarHeight = activity.getResources().getDimensionPixelSize(resourceId);
            ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) blurImageView.getLayoutParams();
            params.setMargins(0, systemStatusBarHeight, 0, 0);
            blurImageView.setLayoutParams(params);
        }
    }

    @Override
    public void onBlurringFinish(Bitmap blurredImage) {
        if (blurredImage != null) {
            showBlurredBackground(blurredImage);
        } else {
            dimBackground();
        }
    }

    private void showBlurredBackground(Bitmap blurredImage) {
        blurImageView.setImageBitmap(blurredImage);
        ObjectAnimator fadeInAnimator = ObjectAnimator.ofFloat(blurImageView,
                FADE_IN_ANIMATION_PARAMETER,
                FADE_IN_ANIMATION_END_VALUE);
        fadeInAnimator.setDuration(blurAnimationDuration);
        fadeInAnimator.start();
    }

    private void dimBackground() {
        blurImageView.setVisibility(View.GONE);
        dialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
    }
}
