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

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricGradleTestRunner;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(RobolectricGradleTestRunner.class)
public class ObligatoryDrawerListenerTest {

    private ObligatoryDrawerListener obligatoryDrawerListener;
    private DrawerBackgroundView drawerBackgroundView;
    private DrawerBackgroundAdapter drawerBackgroundAdapter;

    @Before
    public void setUp() {
        drawerBackgroundView = mock(DrawerBackgroundView.class);
        drawerBackgroundAdapter = mock(DrawerBackgroundAdapter.class);
        obligatoryDrawerListener = new ObligatoryDrawerListener(drawerBackgroundAdapter,
                drawerBackgroundView);
    }

    @Test
    public void shouldInvokeAddtionalListenerOnDrawerSlideWhenListenerSet() {
        //given
        DrawerLayout.DrawerListener userDrawerListener = mock(DrawerLayout.DrawerListener.class);
        obligatoryDrawerListener.setUserDrawerListener(userDrawerListener);
        View view = mock(View.class);
        float slideOffset = 0.4f;

        //when
        obligatoryDrawerListener.onDrawerSlide(view, slideOffset);

        //then
        verify(userDrawerListener, times(1)).onDrawerSlide(view, slideOffset);
    }

    @Test
    public void shouldInvokeAddtionalListenerOnDrawerStateChangedWhenListenerSet() {
        //given
        DrawerLayout.DrawerListener userDrawerListener = mock(DrawerLayout.DrawerListener.class);
        obligatoryDrawerListener.setUserDrawerListener(userDrawerListener);
        int drawerState = 2;

        //when
        obligatoryDrawerListener.onDrawerStateChanged(drawerState);

        //then
        verify(userDrawerListener, times(1)).onDrawerStateChanged(drawerState);
    }

    @Test
    public void shouldInvokeAddtionalListenerOnDrawerOpenedWhenListenerSet() {
        //given
        DrawerLayout.DrawerListener userDrawerListener = mock(DrawerLayout.DrawerListener.class);
        obligatoryDrawerListener.setUserDrawerListener(userDrawerListener);
        View view = mock(View.class);

        //when
        obligatoryDrawerListener.onDrawerOpened(view);

        //then
        verify(userDrawerListener, times(1)).onDrawerOpened(view);
    }

    @Test
    public void shouldInvokeAddtionalListenerOnDrawerClosedWhenListenerSet() {
        //given
        DrawerLayout.DrawerListener userDrawerListener = mock(DrawerLayout.DrawerListener.class);
        obligatoryDrawerListener.setUserDrawerListener(userDrawerListener);
        View view = mock(View.class);

        //when
        obligatoryDrawerListener.onDrawerClosed(view);

        //then
        verify(userDrawerListener, times(1)).onDrawerClosed(view);
    }

    @Ignore("Need implementation")
    @Test
    public void onDrawerSlideShouldEnableBlurredLayersIfTaskNotRunningYet() {
        //given

        //when

        //then
    }

    @Ignore("Need implementation")
    @Test
    public void onDrawerSlideShouldNotEnableBlurredLayersIfTaskAlreadyRunning() {
        //given

        //when

        //then
    }

    @Ignore("Need implementation")
    @Test
    public void onDrawerSlideShouldStartPreparingBlurredBackgroundIfTaskNotRunningYet() {
        //given

        //when

        //then
    }

    @Ignore("Need implementation")
    @Test
    public void onDrawerSlideShouldNotStartPreparingBlurredBackgroundIfTaskAlreadyRunning() {
        //given

        //when

        //then
    }

    @Ignore("Need implementation")
    @Test
    public void onDrawerSlideShouldUpdateDrawerBackgroundAlphaLevel() {
        //given

        //when

        //then
    }

    @Ignore("Need implementation")
    @Test
    public void onDrawerStateChangedShouldEnableBlurredLayersIfTaskNotRunningYetAndStateDragging() {
        //given

        //when

        //then
    }

    @Ignore("Need implementation")
    @Test
    public void onDrawerStateChangedShouldNotEnableBlurredLayersIfTaskAlreadyRunningAndStateDragging()
            {
        //given

        //when

        //then
    }

    @Ignore("Need implementation")
    @Test
    public void onDrawerStateChangedShouldStartPreparingBlurredBackgroundIfTaskNotRunningYetAndStateDragging()
            {
        //given

        //when

        //then
    }

    @Ignore("Need implementation")
    @Test
    public void onDrawerStateChangedShouldNotStartPreparingBlurredBackgroundIfTaskAlreadyRunningAndStateDragging()
            {
        //given

        //when

        //then
    }

    @Ignore("Need implementation")
    @Test
    public void onDrawerStateChangedShouldDisabledBlurredViewWhenStateIdleAndZeroOffset()
            {
        //given

        //when

        //then
    }

    @Ignore("Need implementation")
    @Test
    public void onDrawerOpenedShouldStartPreparingBlurredBackgroundIfTaskNotRunningYet() {
        //given

        //when

        //then
    }

    @Ignore("Need implementation")
    @Test
    public void onDrawerOpenedShouldNotStartPreparingBlurredBackgroundIfTaskAlreadyRunning() {
        //given

        //when

        //then
    }

    @Ignore("Need implementation")
    @Test
    public void onDrawerOpenedShouldUpdateDrawerBackgroundAlphaLevel() {
        //given

        //when

        //then
    }

    @Ignore("Need implementation")
    @Test
    public void onDrawerClosedChangedShouldDisabledBlurredView()
            {
        //given

        //when

        //then
    }
}