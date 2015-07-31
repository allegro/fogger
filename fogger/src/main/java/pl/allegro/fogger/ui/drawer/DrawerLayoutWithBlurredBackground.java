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


import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.widget.DrawerLayout;
import android.util.AttributeSet;
import android.util.Log;
import pl.allegro.fogger.blur.Blur;
import pl.allegro.fogger.FoggerConfig;
import pl.allegro.fogger.R;
import pl.allegro.fogger.blur.BlurringImageListener;
import pl.allegro.fogger.exception.FoggerException;

public class DrawerLayoutWithBlurredBackground extends DrawerLayout implements BlurringImageListener {

    private static final String TAG = DrawerLayoutWithBlurredBackground.class.getName();
    protected static final int PREINIT_BITMAP_SIZE = 300;

    private DrawerBackgroundAdapter drawerBackgroundAdapter;
    private ObligatoryDrawerListener obligatoryDrawerListener;
    private DrawerBackgroundView drawerBackgroundView;
    protected Blur blur;

    public DrawerLayoutWithBlurredBackground(Context context) {
        super(context);
        setup();
    }

    public DrawerLayoutWithBlurredBackground(Context context, AttributeSet attrs) {
        super(context, attrs);
        setup();
    }

    public DrawerLayoutWithBlurredBackground(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        setup();
    }

    private void setup() {
        drawerBackgroundView = new DrawerBackgroundView(this);
        drawerBackgroundAdapter = new DrawerBackgroundAdapter(getContext(), drawerBackgroundView);
        String viewWithDrawerTag = getContext().getResources().getString(R.string.view_with_drawer_tag);
        drawerBackgroundAdapter.init(this, this, viewWithDrawerTag);

        obligatoryDrawerListener = new ObligatoryDrawerListener(drawerBackgroundAdapter, drawerBackgroundView);
        super.setDrawerListener(obligatoryDrawerListener);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        removeDefaultBackground();
        drawerBackgroundView.addBlurredLayer();

        preInitRenderScriptLibraries();
    }

    private void removeDefaultBackground() {
        super.setScrimColor(getContext().getResources().getColor(android.R.color.transparent));
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        resetBackground();
    }

    @Override
    public void onBlurringFinish(Bitmap blurredImage) {
        Log.d(TAG, "Catch blurred background");
        drawerBackgroundView.setImageBitmap(blurredImage);
    }

    private void resetBackground() {
        drawerBackgroundAdapter.reset();
        drawerBackgroundView.reset();
    }

    @Override
    public void setDrawerListener(DrawerListener listener) {
        obligatoryDrawerListener.setUserDrawerListener(listener);
    }

    @Override
    public void setScrimColor(int color) {
        throw new FoggerException(DrawerLayoutWithBlurredBackground.class.getName() + " does not support "
                + "public void setScrimColor(int color)");
    }

    private void preInitRenderScriptLibraries() {
        Bitmap bitmap = Bitmap.createBitmap(PREINIT_BITMAP_SIZE, PREINIT_BITMAP_SIZE, Bitmap.Config.ALPHA_8);
        blur = blur == null ? new Blur() : blur;
        Bitmap fastBlurResult = blur.blur(getContext(), bitmap, FoggerConfig.getBackgroundBlurRadius());
        fastBlurResult.recycle();
    }

}
