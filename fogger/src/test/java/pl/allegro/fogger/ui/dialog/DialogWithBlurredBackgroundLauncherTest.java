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

package pl.allegro.fogger.ui.dialog;

import android.app.Activity;
import android.app.AlertDialog;
import android.graphics.Bitmap;
import android.view.View;
import android.widget.ImageView;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.Shadows;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowAlertDialog;
import org.robolectric.shadows.ShadowImageView;

import pl.allegro.fogger.BuildConfig;
import pl.allegro.fogger.R;
import pl.allegro.fogger.ui.BlurredBackgroundAdapter;

import static org.fest.assertions.Assertions.assertThat;
import static org.mockito.Mockito.mock;

@RunWith(RobolectricGradleTestRunner.class)
public class DialogWithBlurredBackgroundLauncherTest {

    private Activity exampleActivity;
    private AlertDialog dialog;
    private DialogWithBlurredBackgroundLauncher mDialogWithBlurredBackgroundLauncher;

    @Before
    public void setUp() {
        exampleActivity = Robolectric.buildActivity(Activity.class).create().start().resume().get();
        dialog = new AlertDialog.Builder(exampleActivity).create();

        mDialogWithBlurredBackgroundLauncher = new DialogWithBlurredBackgroundLauncher(exampleActivity);
        mDialogWithBlurredBackgroundLauncher.blurredBackgroundAdapter =
                mock(BlurredBackgroundAdapter.class);
    }

    @Test
    public void shouldShowGivenDialog() {
        //when
        mDialogWithBlurredBackgroundLauncher.showDialog(dialog);

        //then
        AlertDialog alert = ShadowAlertDialog.getLatestAlertDialog();
        assertThat(dialog).isEqualTo(alert);
    }

    @Test
    public void shouldAddBlurredLayerToActivity() {
        //when
        mDialogWithBlurredBackgroundLauncher.showDialog(dialog);

        //then
        View blurLayer = exampleActivity.findViewById(R.id.blurLayer);
        assertThat(blurLayer).isNotNull();
    }

    @Test
    public void shouldSetGivenBitmapWhenBlurredBackgroundIsReady() {
        //given
        mDialogWithBlurredBackgroundLauncher.showDialog(dialog);
        Bitmap bitmap = mock(Bitmap.class);

        //when
        mDialogWithBlurredBackgroundLauncher.onBlurringFinish(bitmap);

        //then
        ImageView blurLayer = (ImageView) exampleActivity.findViewById(R.id.blurLayer);
        ShadowImageView shadowImageView = Shadows.shadowOf(blurLayer);
        assertThat(shadowImageView.getImageBitmap()).isEqualTo(bitmap);
    }

    @Test
    public void shouldRemovedBlurredLayerFromActivity() {
        //given
        mDialogWithBlurredBackgroundLauncher.showDialog(dialog);

        //when
        dialog.dismiss();

        //then
        View blurLayer = exampleActivity.findViewById(R.id.blurLayer);
        assertThat(blurLayer).isNull();
    }
}