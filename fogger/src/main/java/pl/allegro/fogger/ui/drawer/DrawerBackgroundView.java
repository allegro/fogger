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

package pl.allegro.fogger.ui.drawer;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.ImageView;
import pl.allegro.fogger.FoggerConfig;
import pl.allegro.fogger.R;

import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;

public class DrawerBackgroundView {

    private static final String TAG = DrawerBackgroundView.class.getName();

    public static final float NOT_TRANSPARENT_ALPHA_LEVEL = 1f;
    private static final int DELAY_MILLIS_BETWEEN_NEXT_FADE_IN_STEP = 20;
    private boolean isBackgroundSet = false;
    private boolean isReadyToImmidiatelySetAlpha = false;
    private float lastAlphaLevel = 0f;

    protected ImageView blurBackground;
    protected ImageView orginalBackground;
    private DrawerLayoutWithBlurredBackground drawerLayoutWithBlurredBackground;
    protected BlockingDeque<Float> blockingQueue = new LinkedBlockingDeque<Float>();

    public DrawerBackgroundView(DrawerLayoutWithBlurredBackground drawerLayoutWithBlurredBackground) {
        this.drawerLayoutWithBlurredBackground = drawerLayoutWithBlurredBackground;
    }

    public synchronized void addBlurredLayer() {
        View blurredLayer = LayoutInflater.from(drawerLayoutWithBlurredBackground.getContext()).
                inflate(R.layout.blurred_background_for_drawer, drawerLayoutWithBlurredBackground, false);
        addViewAsNextToLast(blurredLayer);
        blurBackground = (ImageView) blurredLayer.findViewById(R.id.drawerBlurBackground);
        orginalBackground = (ImageView) blurredLayer.findViewById(R.id.drawerBlurBackgroundBackground);
    }

    public synchronized void setImageBitmap(Bitmap blurredImage) {
        Log.d(TAG, "Set blurred background");
        initBackground(blurredImage);

        enableBlurredLayers();
        if (isDrawerAlreadyOrAlmostOpen()) {
            isReadyToImmidiatelySetAlpha = true;
            showBlurWithAnimation();
            Log.i(TAG, "[blur time] Set blurred bitmap when drawer is already open, last offset: " + lastAlphaLevel);
        } else if (isDrawerLittleOpen()) {
            AnimationWorker animationWorker = new AnimationWorker(blockingQueue.poll());
            blurBackground.postDelayed(animationWorker, DELAY_MILLIS_BETWEEN_NEXT_FADE_IN_STEP);
        } else {
            isReadyToImmidiatelySetAlpha = true;
            Log.i(TAG, "[blur time] Set blurred bitmap when drawer is not open yet, last offset: " + lastAlphaLevel);
            setAlpha(lastAlphaLevel);
        }
    }

    private void initBackground(Bitmap blurredImage) {
        blurBackground.setAlpha(0f);
        blurBackground.setBackgroundColor(Color.WHITE);
        blurBackground.setImageBitmap(blurredImage);
        isBackgroundSet = true;
    }

    public synchronized void reset() {
        Log.d(TAG, "Reset background");
        isBackgroundSet = false;
        isReadyToImmidiatelySetAlpha = false;
        blurBackground.setImageBitmap(null);
        blurBackground.refreshDrawableState();
        blockingQueue.clear();
    }

    public synchronized void setAlpha(float alphaLevel) {
        lastAlphaLevel = alphaLevel;
        Log.d(TAG, "Add to alpha queue[" + alphaLevel + "] ");
        blockingQueue.add(alphaLevel);
        if (isBackgroundSet && isReadyToImmidiatelySetAlpha) {
            Log.d(TAG, "Set alpha[" + alphaLevel + "] \tfor background with background source: "
                    + blurBackground.getDrawable());
            blurBackground.setAlpha(alphaLevel);

        }
    }

    private void showBlurWithAnimation() {
        AlphaAnimation animation = new AlphaAnimation(0f, lastAlphaLevel);
        animation.setDuration(FoggerConfig.DRAWER_INITIAL_BLUR_ANIMATION_DURATION);
        blurBackground.setAlpha(lastAlphaLevel);
        blurBackground.startAnimation(animation);
    }

    private void addViewAsNextToLast(View view) {
        int childCount = drawerLayoutWithBlurredBackground.getChildCount();
        drawerLayoutWithBlurredBackground.addView(view, childCount - 1);
    }

    public void disableBlurredLayers() {
        blurBackground.setVisibility(View.GONE);
        orginalBackground.setVisibility(View.GONE);
    }

    public void enableBlurredLayers() {
        blurBackground.setVisibility(View.VISIBLE);
        orginalBackground.setVisibility(View.VISIBLE);
    }

    private boolean isDrawerAlreadyOrAlmostOpen() {
        return lastAlphaLevel == NOT_TRANSPARENT_ALPHA_LEVEL;
    }

    private boolean isDrawerLittleOpen() {
        return lastAlphaLevel > FoggerConfig.getBackgroundAnimationFirstAlphaLevel();
    }

    private final class AnimationWorker implements Runnable {

        private static final float MIN_LEVEL_STEP_TO_CHANGE_TRANSPARENCY = 0.02f;
        Float level;

        private AnimationWorker(Float level) {
            this.level = level;
        }

        @Override
        public void run() {
            Log.d(TAG, "handler Set alpha[" + level + "] \tfor background with background source: "
                    + blurBackground.getDrawable());
            blurBackground.setAlpha(level);
            Float nextLevel = extractNextAlphaLevel();
            if (nextLevel == null) {
                isReadyToImmidiatelySetAlpha = true;
                return;
            }
            AnimationWorker animationWorker = new AnimationWorker(nextLevel);
            blurBackground.postDelayed(animationWorker, FoggerConfig.getDrawerBackgroundAnimationSpeed());
        }

        private Float extractNextAlphaLevel() {
            Float nextLevel = blockingQueue.poll();
            while (nextLevel != null && levelCrossTheMinThreshold(nextLevel)) {
                nextLevel = blockingQueue.poll();
            }
            return nextLevel;
        }

        private boolean levelCrossTheMinThreshold(Float nextLevel) {
            return Math.abs(level - nextLevel) < MIN_LEVEL_STEP_TO_CHANGE_TRANSPARENCY;
        }
    }
}
