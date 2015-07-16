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

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.ContextMenu;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class StubContextMenu implements ContextMenu {

    public static final String TAG = StubContextMenu.class.getName();
    private static final String OPERATION_NOT_SUPPORTED_BY_FOGGER = "Operation not supported by Fogger";

    private Context context;
    protected List<MenuItem> menuItems = new ArrayList<MenuItem>();

    public StubContextMenu(Context context) {
        this.context = context;
    }

    @Override
    public MenuItem add(CharSequence title) {
        Log.i(TAG, "add(title) " + title);
        return new StubMenuItem(context).setTitle(title);
    }

    @Override
    public MenuItem add(int titleRes) {
        Log.i(TAG, "add(titleRes) " + titleRes);
        return new StubMenuItem(context).setTitle(titleRes);
    }

    @Override
    public MenuItem add(int groupId, int itemId, int order, CharSequence title) {
        Log.i(TAG, "add(int groupId, int itemId, int order, CharSequence title) title: " + title);
        MenuItem menuItem = new StubMenuItem(context)
                .setTitle(title)
                .setItemId(itemId)
                .setOrder(order)
                .setGroupId(groupId);
        menuItems.add(menuItem);
        return menuItem;
    }

    @Override
    public MenuItem add(int groupId, int itemId, int order, int titleRes) {
        Log.i(TAG, "add(int groupId, int itemId, int order, int titleRes) titleRes: " + titleRes);
        MenuItem menuItem = new StubMenuItem(context)
                .setTitle(titleRes)
                .setItemId(itemId)
                .setOrder(order)
                .setGroupId(groupId);
        menuItems.add(menuItem);
        return menuItem;
    }

    @Override
    public SubMenu addSubMenu(CharSequence title) {
        Log.i(TAG, "addSubMenu(CharSequence title) " + title);
        throw new UnsupportedOperationException(OPERATION_NOT_SUPPORTED_BY_FOGGER);
    }

    @Override
    public SubMenu addSubMenu(int titleRes) {
        Log.i(TAG, "addSubMenu(titleRes) " + titleRes);
        throw new UnsupportedOperationException(OPERATION_NOT_SUPPORTED_BY_FOGGER);
    }

    @Override
    public SubMenu addSubMenu(int groupId, int itemId, int order, CharSequence title) {
        Log.i(TAG, "addSubMenu(int groupId, int itemId, int order, CharSequence title) title: " + title);
        throw new UnsupportedOperationException(OPERATION_NOT_SUPPORTED_BY_FOGGER);
    }

    @Override
    public SubMenu addSubMenu(int groupId, int itemId, int order, int titleRes) {
        Log.i(TAG, "addSubMenu(int groupId, int itemId, int order, int titleRes) titleRes: " + titleRes);
        throw new UnsupportedOperationException(OPERATION_NOT_SUPPORTED_BY_FOGGER);
    }

    @Override
    public int addIntentOptions(int groupId, int itemId, int order, ComponentName caller, Intent[] specifics, //NOSONAR
                                Intent intent, int flags, MenuItem[] outSpecificItems) { //NOSONAR
        Log.i(TAG, "addIntentOptions(int groupId, int itemId, int order, ComponentName caller, Intent[] specifics,\n"
                + "Intent intent, int flags, MenuItem[] outSpecificItems)");
        throw new UnsupportedOperationException(OPERATION_NOT_SUPPORTED_BY_FOGGER);
    }

    @Override
    public void removeItem(int id) {
        Log.i(TAG, "removeItem(int id) " + id);
        throw new UnsupportedOperationException(OPERATION_NOT_SUPPORTED_BY_FOGGER);
    }

    @Override
    public void removeGroup(int groupId) {
        Log.i(TAG, "removeGroup(int groupId) " + groupId);
        throw new UnsupportedOperationException(OPERATION_NOT_SUPPORTED_BY_FOGGER);
    }

    @Override
    public void clear() {
        Log.i(TAG, "clear()");
        throw new UnsupportedOperationException(OPERATION_NOT_SUPPORTED_BY_FOGGER);
    }

    @Override
    public void setGroupCheckable(int group, boolean checkable, boolean exclusive) {
        Log.i(TAG, "setGroupCheckable(int group, boolean checkable, boolean exclusive)");
        throw new UnsupportedOperationException(OPERATION_NOT_SUPPORTED_BY_FOGGER);
    }

    @Override
    public void setGroupVisible(int group, boolean visible) {
        Log.i(TAG, "setGroupVisible(int group, boolean visible)");
        throw new UnsupportedOperationException(OPERATION_NOT_SUPPORTED_BY_FOGGER);
    }

    @Override
    public void setGroupEnabled(int group, boolean enabled) {
        Log.i(TAG, "setGroupEnabled(int group, boolean enabled)");
        throw new UnsupportedOperationException(OPERATION_NOT_SUPPORTED_BY_FOGGER);
    }

    @Override
    public boolean hasVisibleItems() {
        Log.i(TAG, "hasVisibleItems()");
        throw new UnsupportedOperationException(OPERATION_NOT_SUPPORTED_BY_FOGGER);
    }

    @Override
    public MenuItem findItem(int id) {
        Log.i(TAG, "findItem(int id) " + id);
        throw new UnsupportedOperationException(OPERATION_NOT_SUPPORTED_BY_FOGGER);
    }

    @Override
    public int size() {
        Log.i(TAG, "size()");
        throw new UnsupportedOperationException(OPERATION_NOT_SUPPORTED_BY_FOGGER);
    }

    @Override
    public MenuItem getItem(int index) {
        Log.i(TAG, "getItem(int index) " + index);
        return menuItems.get(index);
    }

    @Override
    public void close() {
        Log.i(TAG, "close()");
        throw new UnsupportedOperationException(OPERATION_NOT_SUPPORTED_BY_FOGGER);
    }

    @Override
    public boolean performShortcut(int keyCode, KeyEvent event, int flags) {
        throw new UnsupportedOperationException(OPERATION_NOT_SUPPORTED_BY_FOGGER);
    }

    @Override
    public boolean isShortcutKey(int keyCode, KeyEvent event) {
        throw new UnsupportedOperationException(OPERATION_NOT_SUPPORTED_BY_FOGGER);
    }

    @Override
    public boolean performIdentifierAction(int id, int flags) {
        throw new UnsupportedOperationException(OPERATION_NOT_SUPPORTED_BY_FOGGER);
    }

    @Override
    public void setQwertyMode(boolean isQwerty) {
        throw new UnsupportedOperationException(OPERATION_NOT_SUPPORTED_BY_FOGGER);
    }

    public CharSequence[] getTitlesArray() {
        List<CharSequence> titles = new ArrayList<CharSequence>();
        for (MenuItem menuItem : menuItems) {
            titles.add(menuItem.getTitle());
        }
        return titles.toArray(new CharSequence[menuItems.size()]);
    }

    @Override
    public ContextMenu setHeaderTitle(int titleRes) {
        throw new UnsupportedOperationException(OPERATION_NOT_SUPPORTED_BY_FOGGER);
    }

    @Override
    public ContextMenu setHeaderTitle(CharSequence title) {
        throw new UnsupportedOperationException(OPERATION_NOT_SUPPORTED_BY_FOGGER);
    }

    @Override
    public ContextMenu setHeaderIcon(int iconRes) {
        throw new UnsupportedOperationException(OPERATION_NOT_SUPPORTED_BY_FOGGER);
    }

    @Override
    public ContextMenu setHeaderIcon(Drawable icon) {
        throw new UnsupportedOperationException(OPERATION_NOT_SUPPORTED_BY_FOGGER);
    }

    @Override
    public ContextMenu setHeaderView(View view) {
        throw new UnsupportedOperationException(OPERATION_NOT_SUPPORTED_BY_FOGGER);
    }

    @Override
    public void clearHeader() {
        throw new UnsupportedOperationException(OPERATION_NOT_SUPPORTED_BY_FOGGER);
    }
}
