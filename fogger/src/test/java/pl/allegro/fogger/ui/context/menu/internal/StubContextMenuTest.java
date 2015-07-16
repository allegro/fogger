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

package pl.allegro.fogger.ui.context.menu.internal;

import com.googlecode.catchexception.CatchException;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

import pl.allegro.fogger.BuildConfig;
import pl.allegro.fogger.R;
import pl.allegro.fogger.utils.ClosureImitation;

import static com.googlecode.catchexception.CatchException.catchException;
import static org.fest.assertions.Assertions.assertThat;

@Config(constants = BuildConfig.class)
@RunWith(RobolectricGradleTestRunner.class)
public class StubContextMenuTest {

    private StubContextMenu stubContextMenu;

    @Before
    public void setUp() {
        stubContextMenu = new StubContextMenu(RuntimeEnvironment.application.getBaseContext());
    }

    @Test
    public void shouldReturnNewStubMenuItemWithGivenTitle() {
        //given
        String title = "test";

        //when
        StubMenuItem stubMenuItem = (StubMenuItem) stubContextMenu.add(title);

        //then
        assertThat(stubMenuItem.getTitle()).isEqualTo(title);
    }

    @Test
    public void shouldReturnNewStubMenuItemWithGivenTitleById() {
        //given
        String title = RuntimeEnvironment.application.getResources().getString(R.string.app_name);

        //when
        StubMenuItem stubMenuItem = (StubMenuItem) stubContextMenu.add(R.string.app_name);

        //then
        assertThat(stubMenuItem.getTitle()).isEqualTo(title);
    }

    @Test
    public void shouldAddAndReturnStubMenuItem() {
        //given
        int groupId = 53;
        int itemId = 95;
        int order = 85;
        String title = "0495";

        //when
        StubMenuItem stubMenuItem = (StubMenuItem) stubContextMenu.add(groupId, itemId, order, title);

        //then
        verifyStubMenuItemData(groupId, itemId, order, title, stubMenuItem);
        assertThat(stubContextMenu.menuItems.get(0)).isEqualTo(stubMenuItem);
    }

    @Test
    public void shouldAddAndReturnStubMenuItemWithTitleById() {
        //given
        int groupId = 53;
        int itemId = 95;
        int order = 85;
        String title =  RuntimeEnvironment.application.getResources().getString(R.string.app_name);

        //when
        StubMenuItem stubMenuItem = (StubMenuItem) stubContextMenu.add(groupId, itemId, order, R.string.app_name);

        //then
        verifyStubMenuItemData(groupId, itemId, order, title, stubMenuItem);
        assertThat(stubContextMenu.menuItems.get(0)).isEqualTo(stubMenuItem);
    }

    @Test
    public void shouldReturnMenuItemForGivenIndex() {
        //given
        String title = "0495";
        int groupId = 53;
        int itemId = 95;
        int order = 85;
        StubMenuItem givenStubMenuItem = (StubMenuItem) stubContextMenu.add(groupId, itemId, order, title);

        //when
        StubMenuItem stubMenuItem = (StubMenuItem) stubContextMenu.getItem(0);

        //then
        assertThat(stubMenuItem).isEqualTo(givenStubMenuItem);
    }

    @Test
    public void shouldReturnAllTitles() {
        //given
        int groupId = 53;
        int itemId = 95;
        int order = 85;
        String title1 = "test";
        String title2 = "test2";
        stubContextMenu.add(groupId, itemId, order, title1);
        stubContextMenu.add(groupId, itemId, order, title2);

        //when
        CharSequence[] titles = stubContextMenu.getTitlesArray();

        //then
        assertThat(titles).isEqualTo(new CharSequence[]{title1, title2});
    }

    @Test
    public void shouldName() {
        //when
        catchException(stubContextMenu).addSubMenu("sadf");

        //then
        assertThat(CatchException.<Exception>caughtException()).isInstanceOf(UnsupportedOperationException.class);
    }

    @Test
    public void shouldThrowUnsupportedException() {
        //it looks nice in InteliJ
        //verifyIfIsUnsupported(() -> { stubContextMenu.addSubMenu("sdf"); });

        verifyIfIsUnsupported(new ClosureImitation(){
            @Override
            public void call() {
                stubContextMenu.addSubMenu(12, 12, 12, 54);
            }
        });
        verifyIfIsUnsupported(new ClosureImitation(){
            @Override
            public void call() {
                stubContextMenu.addSubMenu(12, 12, 12, "sdfd");
            }
        });
        verifyIfIsUnsupported(new ClosureImitation(){
            @Override
            public void call() { stubContextMenu.addSubMenu("sdf");
            }
        });
        verifyIfIsUnsupported(new ClosureImitation(){
            @Override
            public void call() {
                stubContextMenu.addSubMenu(34);
            }
        });
        verifyIfIsUnsupported(new ClosureImitation(){
            @Override
            public void call() {
                stubContextMenu.removeItem(34);
            }
        });
        verifyIfIsUnsupported(new ClosureImitation(){
            @Override
            public void call() {
                stubContextMenu.removeGroup(34);
            }
        });
        verifyIfIsUnsupported(new ClosureImitation(){
            @Override
            public void call() {
                stubContextMenu.clear();
            }
        });
        verifyIfIsUnsupported(new ClosureImitation(){
            @Override
            public void call() {
                stubContextMenu.setGroupCheckable(12, true, true);
            }
        });
        verifyIfIsUnsupported(new ClosureImitation(){
            @Override
            public void call() {
                stubContextMenu.setGroupVisible(12, true);
            }
        });
        verifyIfIsUnsupported(new ClosureImitation(){
            @Override
            public void call() {
                stubContextMenu.setGroupEnabled(12, true);
            }
        });
        verifyIfIsUnsupported(new ClosureImitation(){
            @Override
            public void call() {
                stubContextMenu.hasVisibleItems();
            }
        });
        verifyIfIsUnsupported(new ClosureImitation(){
            @Override
            public void call() {
                stubContextMenu.findItem(32);
            }
        });
        verifyIfIsUnsupported(new ClosureImitation(){
            @Override
            public void call() {
                stubContextMenu.size();
            }
        });
        verifyIfIsUnsupported(new ClosureImitation(){
            @Override
            public void call() {
                stubContextMenu.performShortcut(1, null, 1);
            }
        });
        verifyIfIsUnsupported(new ClosureImitation(){
            @Override
            public void call() {
                stubContextMenu.isShortcutKey(1, null);
            }
        });
        verifyIfIsUnsupported(new ClosureImitation(){
            @Override
            public void call() {
                stubContextMenu.performIdentifierAction(1, 1);
            }
        });
        verifyIfIsUnsupported(new ClosureImitation(){
            @Override
            public void call() {
                stubContextMenu.setQwertyMode(true);
            }
        });
        verifyIfIsUnsupported(new ClosureImitation(){
            @Override
            public void call() {
                stubContextMenu.setHeaderTitle(1);
            }
        });
        verifyIfIsUnsupported(new ClosureImitation(){
            @Override
            public void call() {
                stubContextMenu.setHeaderTitle("sadf");
            }
        });
        verifyIfIsUnsupported(new ClosureImitation(){
            @Override
            public void call() {
                stubContextMenu.setHeaderIcon(1);
            }
        });
        verifyIfIsUnsupported(new ClosureImitation(){
            @Override
            public void call() {
                stubContextMenu.setHeaderIcon(null);
            }
        });
        verifyIfIsUnsupported(new ClosureImitation(){
            @Override
            public void call() {
                stubContextMenu.setHeaderView(null);
            }
        });
        verifyIfIsUnsupported(new ClosureImitation(){
            @Override
            public void call() {
                stubContextMenu.clearHeader();
            }
        });
        verifyIfIsUnsupported(new ClosureImitation(){
            @Override
            public void call() {
                stubContextMenu.close();
            }
        });
        verifyIfIsUnsupported(new ClosureImitation(){
            @Override
            public void call() { stubContextMenu.addIntentOptions(34, 1, 1, null, null, null, 1, null);
            }
        });

    }

    public void verifyIfIsUnsupported(ClosureImitation closure) {
        catchException(closure).call();

        //then
        assertThat(CatchException.<Exception>caughtException()).isInstanceOf(UnsupportedOperationException.class);
    }

    private void verifyStubMenuItemData(int groupId, int itemId, int order, String title, StubMenuItem stubMenuItem) {
        assertThat(stubMenuItem.getTitle()).isEqualTo(title);
        assertThat(stubMenuItem.getItemId()).isEqualTo(itemId);
        assertThat(stubMenuItem.getOrder()).isEqualTo(order);
        assertThat(stubMenuItem.getGroupId()).isEqualTo(groupId);
    }
}