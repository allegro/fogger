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

package pl.allegro.foggerexample.content;

import android.content.SharedPreferences;

public class SharedPreferencesAdapter {

    private final SharedPreferences sharedPreferences;

    private SharedPreferencesAdapter(SharedPreferences sharedPreferences) {
        this.sharedPreferences = sharedPreferences;
    }

    public void persistenceAdd(String key, int value) {
        sharedPreferences.edit().putInt(key, value).commit();
    }

    public void persistenceAdd(String key, String value) {
        sharedPreferences.edit().putString(key, value).commit();
    }

    public int get(String key, int defaultValue) {
        return sharedPreferences.getInt(key, defaultValue);
    }

    public String get(String key, String defaultValue) {
        return sharedPreferences.getString(key, defaultValue);
    }

    public static SharedPreferencesAdapter create(SharedPreferences sharedPreferences) {
        return new SharedPreferencesAdapter(sharedPreferences);
    }
}
