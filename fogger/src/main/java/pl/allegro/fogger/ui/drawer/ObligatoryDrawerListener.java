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


import android.support.v4.widget.DrawerLayout;
import android.view.View;

public class ObligatoryDrawerListener implements DrawerLayout.DrawerListener {

    public static final float DRAWER_CLOSED_OFFSET = 0.0f;
    public static final float DRAWER_OPENED_OFFSET = 1.0f;

    private DrawerBackgroundView drawerBackgroundView;
    private DrawerBackgroundAdapter drawerBackgroundAdapter;
    private DrawerLayout.DrawerListener userDrawerListener;

    private float lastSlideOffset = 0f;
    private boolean blurringTaskAlreadyRun = false;

    public ObligatoryDrawerListener(DrawerBackgroundAdapter drawerBackgroundAdapter,
                                    DrawerBackgroundView drawerBackgroundView) {
        this.drawerBackgroundAdapter = drawerBackgroundAdapter;
        this.drawerBackgroundView = drawerBackgroundView;
    }

    public synchronized void setUserDrawerListener(DrawerLayout.DrawerListener userDrawerListener) {
        this.userDrawerListener = userDrawerListener;
    }

    @Override
    public synchronized void onDrawerSlide(View view, float slideOffset) {
        if (!blurringTaskAlreadyRun) {
            prepareToBluringProcess();
        }
        drawerBackgroundView.setAlpha(slideOffset);
        lastSlideOffset = slideOffset;

        if (hasUserDrawerListener()) {
            userDrawerListener.onDrawerSlide(view, slideOffset);
        }
    }

    private void prepareToBluringProcess() {
        drawerBackgroundView.enableBlurredLayers();
        drawerBackgroundAdapter.prepareBlurredBackgroundOnDrawerEvent();
        blurringTaskAlreadyRun = true;
    }

    @Override
    public synchronized void onDrawerStateChanged(int drawerState) {
        if (drawerState == DrawerLayout.STATE_DRAGGING && !blurringTaskAlreadyRun) {
            prepareToBluringProcess();
        } else if (drawerState == DrawerLayout.STATE_IDLE && lastSlideOffset == DRAWER_CLOSED_OFFSET) {
            reset();
        }

        if (hasUserDrawerListener()) {
            userDrawerListener.onDrawerStateChanged(drawerState);
        }
    }

    private void reset() {
        drawerBackgroundView.disableBlurredLayers();
        blurringTaskAlreadyRun = false;
    }

    @Override
    public synchronized void onDrawerOpened(View view) {
        if (!blurringTaskAlreadyRun) {
            blurringTaskAlreadyRun = true;
            lastSlideOffset = DRAWER_OPENED_OFFSET;
            drawerBackgroundView.setAlpha(lastSlideOffset);
            drawerBackgroundAdapter.prepareBlurredBackgroundOnDrawerEvent();
        }
        if (hasUserDrawerListener()) {
            userDrawerListener.onDrawerOpened(view);
        }
    }

    @Override
    public synchronized void onDrawerClosed(View view) {
        reset();
        if (hasUserDrawerListener()) {
            userDrawerListener.onDrawerClosed(view);
        }
    }

    private boolean hasUserDrawerListener() {
        return userDrawerListener != null;
    }
}
