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

package pl.allegro.fogger.ui.context;


import android.app.Dialog;
import android.app.Fragment;
import android.view.View;
import android.widget.TextView;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowView;

import pl.allegro.fogger.BuildConfig;
import pl.allegro.fogger.shadow.IdleShadowMenuInflater;
import pl.allegro.fogger.ui.dialog.DialogWithBlurredBackgroundLauncher;

import static org.fest.assertions.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.robolectric.Shadows.shadowOf;

@Config(shadows = IdleShadowMenuInflater.class, constants = BuildConfig.class)
@RunWith(RobolectricGradleTestRunner.class)
public class ActivityWithContextMenuTest {

    ActivityWithContextMenu activityWithContextMenu;
    DialogWithBlurredBackgroundLauncher dialogWithBlurredBackgroundLauncher;

    @Before
    public void setUp() {
        activityWithContextMenu = Robolectric
                .buildActivity(ExampleActivityWithContextMenu.class)
                .create()
                .start()
                .get();
        dialogWithBlurredBackgroundLauncher
                = mock(DialogWithBlurredBackgroundLauncher.class);
        activityWithContextMenu.dialogWithBlurredBackgroundLauncher = dialogWithBlurredBackgroundLauncher;

    }

    @Test
    public void shouldLaunchDialogWhenLongClickOnViewRegisteredForContextMenu() {
        //given
        TextView view = new TextView(RuntimeEnvironment.application.getBaseContext());

        //when
        activityWithContextMenu.registerForContextMenu(view);
        view.performLongClick();

        //then
        verify(dialogWithBlurredBackgroundLauncher)
                .showDialog(any(Dialog.class));
    }

    @Test
    public void shouldUnregisteredLongClickListenerWhenContextMenuUnregisteredOnView() {
        //given
        TextView view = new TextView(RuntimeEnvironment.application.getBaseContext());
        activityWithContextMenu.registerForContextMenu(view);
        ShadowView shadowView = shadowOf(view);

        //expected
        assertThat(shadowView.getOnLongClickListener()).isNotNull();

        //when
        activityWithContextMenu.unregisterForContextMenu(view);

        //then
        assertThat(shadowView.getOnLongClickListener()).isNull();
    }

    @Test
    public void shouldRememberAttachedFragments() {
        //given
        Fragment fragment = new Fragment();

        //when
        activityWithContextMenu.onAttachFragment(fragment);

        //then
        assertThat(activityWithContextMenu.fragments).hasSize(1);
        Object rememberedFragment = activityWithContextMenu.fragments.get(0).get();
        assertThat(rememberedFragment).isEqualTo(fragment);
    }

    public static class ExampleActivityWithContextMenu extends ActivityWithContextMenu {

        @Override
        protected int getContextMenuResId(View view) {
            return 12;
        }
    }
}