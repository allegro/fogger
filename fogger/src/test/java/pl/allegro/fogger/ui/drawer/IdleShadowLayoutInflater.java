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

package pl.allegro.fogger.ui.drawer;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import org.robolectric.annotation.Implementation;
import org.robolectric.annotation.Implements;
import org.robolectric.annotation.RealObject;
import org.robolectric.shadows.RoboLayoutInflater;

import static org.mockito.Mockito.mock;

@Implements(LayoutInflater.class)
public class IdleShadowLayoutInflater {

    @RealObject
    LayoutInflater layoutInflater;

    public void __constructor__(Context context) {
    }

    public void __constructor__(LayoutInflater original, Context newContext) {
    }

    @Implementation
    public static LayoutInflater from(Context context) {
        return new RoboLayoutInflater(context);
    }

    @Implementation
    public View inflate(int resource, ViewGroup root, boolean attachToRoot) {
        return mock(View.class);
    }
}
