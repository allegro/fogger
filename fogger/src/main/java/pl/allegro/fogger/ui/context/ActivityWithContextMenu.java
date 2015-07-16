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

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import pl.allegro.fogger.ui.context.menu.StubMenuItemData;
import pl.allegro.fogger.ui.context.menu.internal.StubContextMenu;
import pl.allegro.fogger.ui.context.menu.internal.StubMenuItem;
import pl.allegro.fogger.ui.dialog.DialogWithBlurredBackgroundLauncher;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

public abstract class ActivityWithContextMenu extends Activity {

    protected static final int MENU_RES_ID_MISSING_VALUE = -1;

    List<WeakReference<Object>> fragments = new ArrayList<WeakReference<Object>>();
    DialogWithBlurredBackgroundLauncher dialogWithBlurredBackgroundLauncher;

    int menuResId;
    protected abstract int getContextMenuResId(View view);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dialogWithBlurredBackgroundLauncher = new DialogWithBlurredBackgroundLauncher(this);
    }

    @Override
    public void registerForContextMenu(final View view) {
        view.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                menuResId = ActivityWithContextMenu.this.getContextMenuResId(view);
                verifyMenuResId();
                dialogWithBlurredBackgroundLauncher.showDialog(createDialog());
                return true;
            }
        });
    }

    @Override
    public void onAttachFragment(android.app.Fragment fragment) {
        super.onAttachFragment(fragment);
        fragments.add(new WeakReference(fragment));
    }

    @Override
    public void unregisterForContextMenu(View view) {
        super.unregisterForContextMenu(view);
        view.setOnLongClickListener(null);
    }

    private void handleMenuItemClick(StubMenuItemData stubMenuItemData) {
        MenuItem menuItem = StubMenuItem.create(this, stubMenuItemData);
        if (!ActivityWithContextMenu.this.onContextItemSelected(menuItem)) {
            invokeOnContextItemSelectedOnFragments(menuItem);
        }
    }

    protected Dialog createDialog() {
        final StubContextMenu stubContextMenu = new StubContextMenu(this);
        getMenuInflater().inflate(menuResId, stubContextMenu);

        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setItems(stubContextMenu.getTitlesArray(), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                MenuItem menuItem = stubContextMenu.getItem(which);
                StubMenuItemData stubMenuItemData = StubMenuItemData.create(menuItem);
                handleMenuItemClick(stubMenuItemData);
            }
        });
        return builder.create();
    }

    private void invokeOnContextItemSelectedOnFragments(MenuItem menuItem) {
        if (fragments.isEmpty()) {
            return;
        }

        for (WeakReference<Object> fragmentWeakReference : fragments) {
            if (invokeOnContextItemSelected(menuItem, fragmentWeakReference.get())) {
                return;
            }
        }
    }

    private boolean invokeOnContextItemSelected(MenuItem menuItem, Object fragment) {
        if (fragment instanceof android.app.Fragment) {
            android.app.Fragment androidFragment = (android.app.Fragment) fragment;
            return androidFragment.onContextItemSelected(menuItem);
        } else if (fragment instanceof android.support.v4.app.Fragment) {
            android.support.v4.app.Fragment supportFragment = (android.support.v4.app.Fragment) fragment;
            return supportFragment.onContextItemSelected(menuItem);
        }
        return false;
    }

    private void verifyMenuResId() {
        if (menuResId == MENU_RES_ID_MISSING_VALUE) {
            throw new IllegalArgumentException("Menu resource Id must be specified");
        }
    }
}
