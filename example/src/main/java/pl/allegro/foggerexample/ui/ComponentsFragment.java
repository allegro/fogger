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

import android.app.Fragment;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;
import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import pl.allegro.foggerexample.R;
import pl.allegro.foggerexample.config.dagger.Injector;

public class ComponentsFragment extends Fragment {

    @InjectView(R.id.image)
    protected ImageView imageView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Injector.inject(this);
        View viewRoot = inflater.inflate(R.layout.components_fragment, container, false);
        ButterKnife.inject(this, viewRoot);
        getActivity().registerForContextMenu(imageView);

        return viewRoot;
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        Toast.makeText(getActivity(), "You picked: " + item.getTitle(), Toast.LENGTH_SHORT).show();
        return true;
    }

    @Override
    public void onDestroyView() {
        ButterKnife.reset(this);
        super.onDestroyView();
    }

    @OnClick(R.id.openDrawerButton)
    protected void onOpenDrawerButtonClick() {
        DrawerLayout drawerLayout = ((ComponentsActivity) getActivity()).drawerLayout;
        drawerLayout.openDrawer(Gravity.LEFT);
    }
}
