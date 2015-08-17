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

package pl.allegro.fogger.ui;

import android.app.Activity;
import android.graphics.Bitmap;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatcher;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;

import pl.allegro.fogger.blur.BlurringImageListener;
import pl.allegro.fogger.blur.BlurringMachine;
import pl.allegro.fogger.blur.BlurringMachineFactory;
import pl.allegro.fogger.utils.ImageUtils;
import pl.allegro.fogger.utils.ScreenShooter;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Matchers.argThat;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(RobolectricTestRunner.class)
public class BlurredBackgroundAdapterTest {

    private BlurredBackgroundAdapter blurredBackgroundAdapter;
    private ScreenShooter screenShooterMock = mock(ScreenShooter.class);
    private ImageUtils imageUtils = mock(ImageUtils.class);
    private BlurringImageListener listenerMock;

    @Before
    public void setUp() {
        blurredBackgroundAdapter
                = BlurredBackgroundAdapter.getInstance(RuntimeEnvironment.application);
        listenerMock = mock(BlurringImageListener.class);
    }

    @Test
    public void shouldProvideImageToListenerIfPrepareWasInvokedBefore() {
        //given
        Bitmap bitmapMock = mock(Bitmap.class);
        prepareStartedAdapter(bitmapMock);
        blurredBackgroundAdapter.resetBlurringListener(listenerMock);

        //expected
        verify(listenerMock, never()).onBlurringFinish(any(Bitmap.class));

        //when
        blurredBackgroundAdapter.onBlurringFinish(bitmapMock);

        //then
        verifyReturnedBitmap();
    }

    @Test
    public void shouldNotCallListenerIfAdapterWasResettedBeforeReceiveBlurredImage() {
        //given
        Bitmap bitmapMock = mock(Bitmap.class);
        prepareStartedAdapter(bitmapMock);
        blurredBackgroundAdapter.onBlurringFinish(bitmapMock);
        blurredBackgroundAdapter.reset();

        //when
        blurredBackgroundAdapter.resetBlurringListener(listenerMock);

        //then
        verify(listenerMock, never()).onBlurringFinish(any(Bitmap.class));
    }

    @Test
    public void ifBlurredImageIsReadyShouldCallListenerIfOnlyIsSet() {
        //given
        Bitmap bitmapMock = mock(Bitmap.class);
        blurredBackgroundAdapter.onBlurringFinish(bitmapMock);

        //when
        blurredBackgroundAdapter.resetBlurringListener(listenerMock);

        //then
        verifyReturnedBitmap();
    }

    @Test
    public void ifAdapterIsWaitingForBlurredImageShouldNotCallListenerIfOnlyIsSet() {
        //given
        Bitmap bitmapMock = mock(Bitmap.class);
        prepareStartedAdapter(bitmapMock);

        //when
        blurredBackgroundAdapter.resetBlurringListener(listenerMock);

        //then
        verify(listenerMock, never()).onBlurringFinish(any(Bitmap.class));
    }

    @Test
    public void isAdapterHasNotReadyImageAndIsNotWorkingThenReadImageFromStorage() {
        //given
        blurredBackgroundAdapter.imageUtils = imageUtils;
        blurredBackgroundAdapter.state = BlurredBackgroundAdapter.BlurredBackgroundAdapterState.READY;

        //when
        blurredBackgroundAdapter.resetBlurringListener(listenerMock);

        //then
        verify(imageUtils, times(1)).createBitmapFromFile(any(String.class));
    }

    private void verifyReturnedBitmap() {
        verify(listenerMock, times(1)).onBlurringFinish(argThat(new ArgumentMatcher<Bitmap>() {
            @Override
            public boolean matches(Object objectToMatch) {
                Bitmap blurredBitmap = (Bitmap) objectToMatch;
                assertThat(blurredBitmap, equalTo(blurredBitmap));
                return true;
            }
        }));
    }

    private void prepareStartedAdapter(Bitmap bitmapMock) {
        Activity activityMock = mock(Activity.class);
        when(screenShooterMock.createScreenShot(activityMock)).thenReturn(bitmapMock);
        blurredBackgroundAdapter.screenShooter = screenShooterMock;
        blurredBackgroundAdapter.prepareBlurredBackgroundForActivity(activityMock);
    }

    private void mockTask() {
        BlurringMachineFactory taskFactoryMock = mock(BlurringMachineFactory.class);
        BlurringMachine blurringMachine = mock(BlurringMachine.class);
        when(taskFactoryMock.create(any(Activity.class))).thenReturn(blurringMachine);
    }
}
