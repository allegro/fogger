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

package pl.allegro.fogger.ui.context.menu;

import android.content.Intent;
import android.view.MenuItem;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

import pl.allegro.fogger.BuildConfig;

import static org.fest.assertions.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

@Config(constants = BuildConfig.class)
@RunWith(RobolectricGradleTestRunner.class)
public class StubMenuItemDataTest {

    private static final int GROUP_ID = 3454;
    private static final int ITEM_ID = 1212;
    private static final int ORDER = 6789;
    private static final String TITLE = "dfs";

    private MenuItem menuItem;

    @Before
    public void setUp() {
        menuItem = mock(MenuItem.class);
        given(menuItem.getGroupId()).willReturn(GROUP_ID);
        given(menuItem.getItemId()).willReturn(ITEM_ID);
        given(menuItem.getOrder()).willReturn(ORDER);
        given(menuItem.getTitle()).willReturn(TITLE);
    }

    @Test
    public void shouldCreateStubmenuItemFromMenuItem() {
        //when
        StubMenuItemData stubMenuItemData =  StubMenuItemData.create(menuItem);

        //then
        validate(stubMenuItemData);
    }

    @Test
    public void shouldWriteInAndReadFromParcel() {
        //given
        String key = "key";
        StubMenuItemData stubMenuItemData =  StubMenuItemData.create(menuItem);
        Intent intent = new Intent();

        //when
        intent.putExtra(key, stubMenuItemData);
        StubMenuItemData restoredStubMenuItemData = intent.getParcelableExtra(key);

        //then
        validate(restoredStubMenuItemData);
    }

    private void validate(StubMenuItemData stubMenuItemData) {
        assertThat(stubMenuItemData.getGroupId()).isEqualTo(GROUP_ID);
        assertThat(stubMenuItemData.getItemId()).isEqualTo(ITEM_ID);
        assertThat(stubMenuItemData.getOrder()).isEqualTo(ORDER);
        assertThat(stubMenuItemData.getTitle()).isEqualTo(TITLE);
    }
}