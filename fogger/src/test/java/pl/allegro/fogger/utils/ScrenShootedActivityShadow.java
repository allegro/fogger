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
import android.view.View;
import android.view.Window;

import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Implementation;
import org.robolectric.annotation.Implements;
import org.robolectric.shadows.ShadowActivity;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

@Implements(Activity.class)
public class ScrenShootedActivityShadow extends ShadowActivity {

    protected static final int WINDOW_HEIGHT = 200;
    protected static final int WINDOW_WIDTH = 100;

    @Implementation
    public final Window getWindow() {
        View viewMock = createdMockedDecorView();
        Window windowMock = mock(Window.class);
        given(windowMock.getDecorView()).willReturn(viewMock);
        return windowMock;
    }

    private View createdMockedDecorView() {
        View viewMock = mock(View.class);
        given(viewMock.getHeight()).willReturn(WINDOW_HEIGHT);
        given(viewMock.getWidth()).willReturn(WINDOW_WIDTH);

        given(viewMock.getContext()).willReturn(RuntimeEnvironment.application.getBaseContext());
        return viewMock;
    }


}
