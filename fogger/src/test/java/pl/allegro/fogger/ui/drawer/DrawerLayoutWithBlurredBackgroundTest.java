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

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.RuntimeEnvironment;

import pl.allegro.fogger.blur.BlurringMachine;

import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@RunWith(RobolectricGradleTestRunner.class)
public class DrawerLayoutWithBlurredBackgroundTest {

    @Test
    public void shouldPreinitRenderScriptOnAttachToWindow() {
        //given
        DrawerLayoutWithBlurredBackground drawerLayoutWithBlurredBackground
                = new DrawerLayoutWithBlurredBackground(RuntimeEnvironment.application);
        BlurringMachine blurringMachine = mock(BlurringMachine.class);
        drawerLayoutWithBlurredBackground.blurringMachine = blurringMachine;
        Bitmap bitmap = mock(Bitmap.class);
        given(blurringMachine.blur(any(Bitmap.class)))
            .willReturn(bitmap);

        //when
        drawerLayoutWithBlurredBackground.onAttachedToWindow();

        //then
        verify(drawerLayoutWithBlurredBackground.blurringMachine)
                .blur(any(Bitmap.class));
    }
}