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

import android.app.Application;
import android.content.Context;
import android.view.View;
import pl.allegro.fogger.blur.BlurringImageTask;
import pl.allegro.fogger.ui.BlurredBackgroundAdapter;

public class DrawerBackgroundAdapter {

    protected BlurredBackgroundAdapter blurredBackgroundAdapter;

    private Context context;
    private View rootView;
    private BlurringImageTask.BlurringImageListener blurringImageListener;
    private DrawerBackgroundView drawerBackgroundView;
    private String viewToBeBlurredWhenDrawerOpenTag;

    public DrawerBackgroundAdapter(Context context, DrawerBackgroundView drawerBackgroundView) {
        this.context = context;
        this.blurredBackgroundAdapter = BlurredBackgroundAdapter.getInstance((Application) context.getApplicationContext());
        this.drawerBackgroundView = drawerBackgroundView;
    }

    public synchronized void init(View rootView,
                     BlurringImageTask.BlurringImageListener blurringImageListener,
                     String viewTag) {
        this.rootView = rootView;
        this.blurringImageListener = blurringImageListener;
        this.viewToBeBlurredWhenDrawerOpenTag = viewTag;
    }

    public synchronized void prepareBlurredBackgroundOnDrawerEvent() {
            drawerBackgroundView.reset();
            blurredBackgroundAdapter.reset();
            blurredBackgroundAdapter.prepareBlurredBackgroundForView(context,
                    rootView.findViewWithTag(viewToBeBlurredWhenDrawerOpenTag));
            blurredBackgroundAdapter.resetBlurringListener(blurringImageListener);
    }

    public synchronized void reset() {
        blurredBackgroundAdapter.reset();
    }

}
