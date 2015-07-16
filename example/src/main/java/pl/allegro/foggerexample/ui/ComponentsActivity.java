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

package pl.allegro.foggerexample.ui;

import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;
import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import pl.allegro.fogger.ui.context.ActivityWithContextMenu;
import pl.allegro.fogger.ui.dialog.DialogWithBlurredBackgroundLauncher;
import pl.allegro.foggerexample.R;
import pl.allegro.foggerexample.config.dagger.Injector;
import pl.allegro.foggerexample.utils.Ln;

public class ComponentsActivity extends ActivityWithContextMenu {

    @InjectView(R.id.drawer_layout)
    public DrawerLayout drawerLayout;

    ActionBarDrawerToggle actionBarDrawerToggle;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.action_bar_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected int getContextMenuResId(View view) {
        return R.menu.context_menu;
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        return false;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.components_view);
        Injector.inject(this);
        ButterKnife.inject(this);

        if (savedInstanceState == null) {
            getFragmentManager().beginTransaction()
                    .add(R.id.fragmentPlaceholder, new ComponentsFragment(), "blur").commit();
        }

        drawerLayout.setDrawerShadow(R.drawable.drawer_shadow, Gravity.START);

        actionBarDrawerToggle = new ActionBarDrawerToggle(
                this,                  /* host Activity */
                drawerLayout,         /* DrawerLayout object */
                R.drawable.ic_drawer,  /* nav drawer icon to replace 'Up' caret */
                R.string.drawer_open,  /* "open drawer" description */
                R.string.drawer_close  /* "close drawer" description */
        ) {

            public void onDrawerClosed(View view) {
                getActionBar().setTitle(ComponentsActivity.this.getTitle());
            }

            public void onDrawerOpened(View drawerView) {
                getActionBar().setTitle(ComponentsActivity.this.getTitle());
            }
        };

        drawerLayout.setDrawerListener(actionBarDrawerToggle);
        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setHomeButtonEnabled(true);
        actionBarDrawerToggle.syncState();
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        actionBarDrawerToggle.syncState();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        } else if (item.getItemId() == R.id.actionDialogWithFog) {
            showDialog();
            return true;
        }
        return false;
    }

    @OnClick(R.id.twitter)
    protected void onTwitterClick() {
        try {
            Intent twitterIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.twitter_url)));
            startActivity(Intent.createChooser(twitterIntent, getString(R.string.open_twitter_title)));
        } catch (ActivityNotFoundException e) {
            Toast.makeText(this, getString(R.string.twitter_open_fail), Toast.LENGTH_LONG).show();
            Ln.e(e);
        }
    }

    @OnClick(R.id.github)
    protected void onGithubClick() {
        try {
            Intent githubIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.github_url)));
            startActivity(Intent.createChooser(githubIntent, getString(R.string.open_github_title)));
        } catch (ActivityNotFoundException e) {
            Toast.makeText(this, getString(R.string.github_open_fail), Toast.LENGTH_LONG).show();
            Ln.e(e);
        }
    }

    @OnClick(R.id.mail)
    protected void onMailClick() {
        try {
            Intent mailIntent = new Intent(Intent.ACTION_SEND)
                                .setType(getString(R.string.email_message_type))
                                .putExtra(Intent.EXTRA_EMAIL, new String[]{getString(R.string.e_mail)})
                                .putExtra(Intent.EXTRA_SUBJECT, getString(R.string.email_topic_prefix));
            startActivity(Intent.createChooser(mailIntent, getString(R.string.mail_me_title)));
        } catch (ActivityNotFoundException e) {
            Toast.makeText(this, getString(R.string.email_open_fail), Toast.LENGTH_LONG).show();
            Ln.e(e);
        }
    }

    private void showDialog() {
        Dialog dialog = new Dialog(this);
        dialog.setTitle(R.string.example_dialog_title);
        DialogWithBlurredBackgroundLauncher.showDialog(this, dialog);
    }
}
