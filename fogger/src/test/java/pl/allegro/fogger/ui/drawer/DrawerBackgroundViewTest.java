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
import android.view.View;
import android.widget.ImageView;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

import pl.allegro.fogger.BuildConfig;

import static org.fest.assertions.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@RunWith(RobolectricGradleTestRunner.class)
@Config(shadows = IdleShadowLayoutInflater.class, constants = BuildConfig.class)
public class DrawerBackgroundViewTest {

    private DrawerBackgroundView drawerBackgroundView;
    private DrawerLayoutWithBlurredBackground drawerLayoutWithBlurredBackground;

    @Before
    public void setUp() {
        drawerLayoutWithBlurredBackground = mock(DrawerLayoutWithBlurredBackground.class);
        given(drawerLayoutWithBlurredBackground.getContext())
                .willReturn(RuntimeEnvironment.application.getBaseContext());
        drawerBackgroundView
                = new DrawerBackgroundView(drawerLayoutWithBlurredBackground);
        drawerBackgroundView.blurBackground = new ImageView(RuntimeEnvironment.application);
        drawerBackgroundView.orginalBackground = new ImageView(RuntimeEnvironment.application);

    }

    @Test
    public void shouldShowBlurredBacgrondWithOnEnabling() {
        //given
        drawerBackgroundView.blurBackground.setVisibility(View.GONE);
        drawerBackgroundView.orginalBackground.setVisibility(View.GONE);

        //when
        drawerBackgroundView.enableBlurredLayers();

        //then
        assertThat(drawerBackgroundView.blurBackground.getVisibility()).isEqualTo(View.VISIBLE);
        assertThat(drawerBackgroundView.orginalBackground.getVisibility())
                .isEqualTo(View.VISIBLE);
    }

    @Test
    public void shouldHideBlurredBackgroundOnDisabling() {
        //given
        drawerBackgroundView.blurBackground.setVisibility(View.VISIBLE);
        drawerBackgroundView.orginalBackground.setVisibility(View.VISIBLE);

        //when
        drawerBackgroundView.disableBlurredLayers();

        //then
        assertThat(drawerBackgroundView.blurBackground.getVisibility()).isEqualTo(View.GONE);
        assertThat(drawerBackgroundView.orginalBackground.getVisibility())
                .isEqualTo(View.GONE);
    }

    @Test
    public void shouldSetAlphaOnBackgroundWhenBackgroundIsReadyToSetAlphaDirectly() {
        //given
        drawerBackgroundView.setImageBitmap(mock(Bitmap.class));
        float alphaLevel = 0.43f;

        //when
        drawerBackgroundView.setAlpha(alphaLevel);

        //then
        assertThat(drawerBackgroundView.blurBackground.getAlpha()).isEqualTo(alphaLevel);
    }

    @Test
    public void shouldAddAlphaValueToQueueWhenBackgroundIsNotReadyToSetAlphaDirectly() {
        //given
        float initAlphaLevel = drawerBackgroundView.blurBackground.getAlpha();

        //when
        drawerBackgroundView.setAlpha(0.43f);

        //then
        assertThat(drawerBackgroundView.blurBackground.getAlpha()).isEqualTo(initAlphaLevel);
    }

    @Test
    public void shouldCleanBackgroundAndAlphaQueueOnReset() {
        //given
        ImageView imageView = mock(ImageView.class);
        drawerBackgroundView.blurBackground = imageView;
        drawerBackgroundView.blockingQueue.add(1f);

        //when
        drawerBackgroundView.reset();

        //then
        verify(imageView).setImageBitmap(null);
        verify(imageView).refreshDrawableState();
        assertThat(drawerBackgroundView.blockingQueue).isEmpty();
    }

    @Test
    public void shouldSetGivenImageAsBackgroundOnSettingImageInvoking() {
        //given
        Bitmap imageBitmap = mock(Bitmap.class);
        ImageView imageView = mock(ImageView.class);
        drawerBackgroundView.blurBackground = imageView;
        drawerBackgroundView.orginalBackground = new ImageView(RuntimeEnvironment.application);

        //when
        drawerBackgroundView.setImageBitmap(imageBitmap);

        //then
        verify(imageView).setImageBitmap(imageBitmap);
    }

    @Test
    public void shouldEnabledBackgroundLayersOnSettingImageInvoking() {
        //given
        Bitmap imageBitmap = mock(Bitmap.class);

        //when
        drawerBackgroundView.setImageBitmap(imageBitmap);

        //then
        assertThat(drawerBackgroundView.blurBackground.getVisibility()).isEqualTo(View.VISIBLE);
        assertThat(drawerBackgroundView.orginalBackground.getVisibility())
                .isEqualTo(View.VISIBLE);
    }

    @Test
    public void shouldAddBlurredLayerAtTheTopOfDrawerLayout() {
        //given
        int childsCount = 4;
        given(drawerLayoutWithBlurredBackground.getChildCount()).willReturn(childsCount);
        int lastViewIndexBeforeDrawer = childsCount - 1;

        //when
        drawerBackgroundView.addBlurredLayer();

        //then
        verify(drawerLayoutWithBlurredBackground ).addView((View) any(), eq(lastViewIndexBeforeDrawer));
    }


}