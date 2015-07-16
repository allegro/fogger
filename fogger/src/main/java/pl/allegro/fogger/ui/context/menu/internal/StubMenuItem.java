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

package pl.allegro.fogger.ui.context.menu.internal; //NOSONAR

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.view.ActionProvider;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import pl.allegro.fogger.ui.context.menu.StubMenuItemData;

public class StubMenuItem implements MenuItem {

    private static final String OPERATION_NOT_SUPPORTED_BY_FOGGER = "Operation not supported by Fogger";
    private int itemId;
    private CharSequence title;
    private boolean enabled = true;
    private boolean checked = false;
    private boolean checkable = false;
    private boolean visible = true;
    private Intent intent;

    private Context context;
    private int order;
    private int groupId;

    public static MenuItem create(Context context, StubMenuItemData stubMenuItemData) {
        return new StubMenuItem(context)
                .setTitle(stubMenuItemData.getTitle())
                .setItemId(stubMenuItemData.getItemId())
                .setOrder(stubMenuItemData.getOrder())
                .setGroupId(stubMenuItemData.getGroupId());
    }

    public StubMenuItem(Context context) {
        this.context = context;
    }

    public StubMenuItem(int itemId) {
        this.itemId = itemId;
    }

    public StubMenuItem setItemId(int itemId) {
        this.itemId = itemId;
        return this;
    }

    @Override
    public int getItemId() {
        return itemId;
    }

    @Override
    public int getGroupId() {
        return groupId;
    }

    @Override
    public int getOrder() {
        return order;
    }

    @Override
    public StubMenuItem setTitle(CharSequence title) {
        this.title = title;
        return this;
    }

    @Override
    public StubMenuItem setTitle(int titleId) {
        String title = context.getResources().getString(titleId);
        return setTitle(title);
    }

    @Override
    public CharSequence getTitle() {
        return title;
    }

    @Override
    public StubMenuItem setTitleCondensed(CharSequence title) {
        return this;
    }

    @Override
    public CharSequence getTitleCondensed() {
        throw new UnsupportedOperationException(OPERATION_NOT_SUPPORTED_BY_FOGGER);
    }

    @Override
    public StubMenuItem setIcon(Drawable icon) {
        throw new UnsupportedOperationException(OPERATION_NOT_SUPPORTED_BY_FOGGER);
    }

    @Override
    public StubMenuItem setIcon(int iconRes) {
        return this;
    }

    @Override
    public Drawable getIcon() {
        throw new UnsupportedOperationException(OPERATION_NOT_SUPPORTED_BY_FOGGER);
    }

    @Override
    public StubMenuItem setIntent(Intent intent) {
        this.intent = intent;
        return this;
    }

    @Override
    public Intent getIntent() {
        return this.intent;
    }

    @Override
    public StubMenuItem setShortcut(char numericChar, char alphaChar) {
        throw new UnsupportedOperationException(OPERATION_NOT_SUPPORTED_BY_FOGGER);
    }

    @Override
    public StubMenuItem setNumericShortcut(char numericChar) {
        return this;
    }

    @Override
    public char getNumericShortcut() {
        throw new UnsupportedOperationException(OPERATION_NOT_SUPPORTED_BY_FOGGER);
    }

    @Override
    public StubMenuItem setAlphabeticShortcut(char alphaChar) {
        return this;
    }

    @Override
    public char getAlphabeticShortcut() {
        throw new UnsupportedOperationException(OPERATION_NOT_SUPPORTED_BY_FOGGER);
    }

    @Override
    public StubMenuItem setCheckable(boolean checkable) {
        this.checkable = checkable;
        return this;
    }

    @Override
    public boolean isCheckable() {
        return checkable;
    }

    @Override
    public StubMenuItem setChecked(boolean checked) {
        this.checked = checked;
        return this;
    }

    @Override
    public boolean isChecked() {
        return checked;
    }

    @Override
    public StubMenuItem setVisible(boolean visible) {
        this.visible = visible;
        return this;
    }

    @Override
    public boolean isVisible() {
        return visible;
    }

    @Override
    public StubMenuItem setEnabled(boolean enabled) {
        this.enabled = enabled;
        return this;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    @Override
    public boolean hasSubMenu() {
        throw new UnsupportedOperationException(OPERATION_NOT_SUPPORTED_BY_FOGGER);
    }

    @Override
    public SubMenu getSubMenu() {
        throw new UnsupportedOperationException(OPERATION_NOT_SUPPORTED_BY_FOGGER);
    }

    @Override
    public StubMenuItem setOnMenuItemClickListener(OnMenuItemClickListener menuItemClickListener) {
        return throwUnsupportedException();
    }

    private StubMenuItem throwUnsupportedException() {
        throw new UnsupportedOperationException(OPERATION_NOT_SUPPORTED_BY_FOGGER);
    }

    @Override
    public ContextMenu.ContextMenuInfo getMenuInfo() {
        throw new UnsupportedOperationException(OPERATION_NOT_SUPPORTED_BY_FOGGER);
    }

    @Override
    public void setShowAsAction(int actionEnum) {
        throw new UnsupportedOperationException(OPERATION_NOT_SUPPORTED_BY_FOGGER);
    }

    @Override
    public StubMenuItem setShowAsActionFlags(int actionEnum) {
        throw new UnsupportedOperationException(OPERATION_NOT_SUPPORTED_BY_FOGGER);
    }

    @Override
    public StubMenuItem setActionView(View view) {
        throw new UnsupportedOperationException(OPERATION_NOT_SUPPORTED_BY_FOGGER);
    }

    @Override
    public StubMenuItem setActionView(int resId) {
        throw new UnsupportedOperationException(OPERATION_NOT_SUPPORTED_BY_FOGGER);
    }

    @Override
    public View getActionView() {
        throw new UnsupportedOperationException(OPERATION_NOT_SUPPORTED_BY_FOGGER);
    }

    @Override
    public StubMenuItem setActionProvider(ActionProvider actionProvider) {
        throw new UnsupportedOperationException(OPERATION_NOT_SUPPORTED_BY_FOGGER);
    }

    @Override
    public ActionProvider getActionProvider() {
        throw new UnsupportedOperationException(OPERATION_NOT_SUPPORTED_BY_FOGGER);
    }

    @Override
    public boolean expandActionView() {
        throw new UnsupportedOperationException(OPERATION_NOT_SUPPORTED_BY_FOGGER);
    }

    @Override
    public boolean collapseActionView() {
        throw new UnsupportedOperationException(OPERATION_NOT_SUPPORTED_BY_FOGGER);
    }

    @Override
    public boolean isActionViewExpanded() {
        throw new UnsupportedOperationException(OPERATION_NOT_SUPPORTED_BY_FOGGER);
    }

    @Override
    public StubMenuItem setOnActionExpandListener(OnActionExpandListener listener) {
        throw new UnsupportedOperationException(OPERATION_NOT_SUPPORTED_BY_FOGGER);
    }

    public StubMenuItem setOrder(int order) {
        this.order = order;
        return this;
    }

    public StubMenuItem setGroupId(int groupId) {
        this.groupId = groupId;
        return this;
    }
}