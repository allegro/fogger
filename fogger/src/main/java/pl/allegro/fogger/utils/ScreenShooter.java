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

package pl.allegro.fogger.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.view.View;
import pl.allegro.fogger.FoggerConfig;

public class ScreenShooter {

    protected static final int DEFAULT_CANVAS_BACKGROUND = android.R.color.white;

    protected static int systemStatusBarHeight;

    public  Bitmap createScreenShot(View view) {
        return createViewScreenshot(view, false);
    }

    public Bitmap createScreenShot(Activity activity) {
        return createAppScreenshotInScale(activity);
    }

    private Bitmap createAppScreenshotInScale(Activity activity) {
        View view = activity.getWindow().getDecorView();
        return createViewScreenshot(view, true);
    }

    private Bitmap createViewScreenshot(View view, boolean needToCutStatusBar) {
        extractStatusBarHeight(view.getContext());
        int scaledViewWidth = getScaledWidth(view);
        int scaledViewHeight = getScaledHeight(view);

        Bitmap localBitmap = createScreenShot(view, scaledViewWidth, scaledViewHeight);

        if (!needToCutStatusBar) {
            return localBitmap;
        }

        Bitmap screenshot = cutStatusBar(scaledViewWidth, scaledViewHeight, localBitmap);
        localBitmap.recycle();
        return screenshot;
    }

    private Bitmap cutStatusBar(int scaledViewWidth, int scaledViewHeight, Bitmap localBitmap) {
        int yOffset = (int) (FoggerConfig.getScreenshotScale() * systemStatusBarHeight);
        int xOffset = 0;
        return Bitmap.createBitmap(localBitmap, xOffset, yOffset, scaledViewWidth - xOffset,
                scaledViewHeight - yOffset, null, true);
    }

    private Bitmap createScreenShot(View view, int scaledViewWidth, int scaledViewHeight) {
        Bitmap localBitmap = Bitmap.createBitmap(scaledViewWidth, scaledViewHeight, Bitmap.Config.ARGB_8888);
        Canvas localCanvas = prepareCanvas(localBitmap, view.getContext());
        view.draw(localCanvas);
        return localBitmap;
    }

    private Canvas prepareCanvas(Bitmap localBitmap, Context context) {
        Canvas localCanvas = new Canvas(localBitmap);

        if (FoggerConfig.getScreenshotScale() < 1) {
            localCanvas.scale(FoggerConfig.getScreenshotScale(), FoggerConfig.getScreenshotScale());
        }

        int backgroundColor = FoggerConfig.getBackgroundColorResourceId() != -1
                ? context.getResources().getColor(FoggerConfig.getBackgroundColorResourceId())
                : DEFAULT_CANVAS_BACKGROUND;

        localCanvas.drawColor(backgroundColor);
        return localCanvas;
    }

    private int getScaledHeight(View view) {
        int viewHeight = view.getHeight();
        return (int) (FoggerConfig.getScreenshotScale() * viewHeight);
    }

    private int getScaledWidth(View view) {
        int viewWidth = view.getWidth();
        return (int) (FoggerConfig.getScreenshotScale() * viewWidth);
    }

    private void extractStatusBarHeight(Context context) {
        if (systemStatusBarHeight == 0) {
            int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
            if (resourceId > 0) {
                systemStatusBarHeight = context.getResources().getDimensionPixelSize(resourceId);
            }
        }
    }
}
