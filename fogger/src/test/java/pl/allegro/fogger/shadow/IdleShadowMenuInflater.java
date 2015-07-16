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

package pl.allegro.fogger.shadow;

import android.content.Context;
import android.view.Menu;
import android.view.MenuInflater;
import org.robolectric.annotation.Implementation;
import org.robolectric.annotation.Implements;
import org.robolectric.res.ResourceLoader;

import static org.robolectric.Shadows.shadowOf;

@Implements(MenuInflater.class)
public class IdleShadowMenuInflater {

    private Context context;
    private ResourceLoader resourceLoader;
    private boolean strictI18n;

    public void __constructor__(Context context) {
        this.context = context;
        resourceLoader = shadowOf(context).getResourceLoader();
        strictI18n = shadowOf(context).isStrictI18n();
    }

    @Implementation
    public void inflate(int resource, Menu root) {

    }
}
