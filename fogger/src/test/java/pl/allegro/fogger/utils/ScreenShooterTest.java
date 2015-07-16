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

import android.graphics.Bitmap;
import android.view.View;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

import pl.allegro.fogger.FoggerConfig;

import static org.fest.assertions.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.robolectric.Shadows.shadowOf;

@RunWith(RobolectricGradleTestRunner.class)
@Config(shadows={ScrenShootedActivityShadow.class})
public class ScreenShooterTest {

    private ScreenShooter screenShooter;
    private ActivityToTestScreenshoot activityToTestScreenshoot;

    @Before
    public void setUp() {
        screenShooter = new ScreenShooter();
        activityToTestScreenshoot = Robolectric.buildActivity(ActivityToTestScreenshoot.class).create().resume().start().get();
    }

    @Test
    public void shouldCreateScaledScreenShotOfActivityWithoutStatusBar() {
        //given
        ScrenShootedActivityShadow screnShootedActivityShadow
                = (ScrenShootedActivityShadow) shadowOf(activityToTestScreenshoot);

        int scaledWindowHeight = (int) (screnShootedActivityShadow.WINDOW_HEIGHT * FoggerConfig.getScreenshotScale());
        int scaledWindowWidth = (int) (screnShootedActivityShadow.WINDOW_WIDTH * FoggerConfig.getScreenshotScale());
        int scaledStatusBarHeight = extractScaledStatusBarScaledHeight();

        //when
        Bitmap screenShot = screenShooter.createScreenShot(activityToTestScreenshoot);

        //then
        assertThat(screenShot.getHeight()).isEqualTo(scaledWindowHeight - scaledStatusBarHeight);
        assertThat(screenShot.getWidth()).isEqualTo(scaledWindowWidth);
    }

    @Test
    public void shouldCreateScaledScreenshotOfView() {
        //given
        int viewHeight = 400;
        int viewWidth = 75;
        int scaledWindowHeight = (int) (viewHeight * FoggerConfig.getScreenshotScale());
        int scaledWindowWidth = (int) (viewWidth * FoggerConfig.getScreenshotScale());

        View view = createView(viewHeight, viewWidth);

        //when
        Bitmap screenShot = screenShooter.createScreenShot(view);

        //then
        assertThat(screenShot.getHeight()).isEqualTo(scaledWindowHeight);
        assertThat(screenShot.getWidth()).isEqualTo(scaledWindowWidth);
    }

    private View createView(int viewHeight, int viewWidth) {
        View viewMock = mock(View.class);
        given(viewMock.getHeight()).willReturn(viewHeight);
        given(viewMock.getWidth()).willReturn(viewWidth);

        given(viewMock.getContext()).willReturn(RuntimeEnvironment.application.getBaseContext());
        return viewMock;
    }

    private int extractScaledStatusBarScaledHeight() {
        int resourceId = RuntimeEnvironment.application.getResources().getIdentifier("status_bar_height", "dimen", "android");
        int systemStatusBarHeight = RuntimeEnvironment.application.getResources().getDimensionPixelSize(resourceId);
        return (int) (systemStatusBarHeight * FoggerConfig.getScreenshotScale());
    }
}