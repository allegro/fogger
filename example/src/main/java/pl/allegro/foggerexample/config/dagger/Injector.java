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

package pl.allegro.foggerexample.config.dagger;

import dagger.ObjectGraph;

public final class Injector {
    public static ObjectGraph sObjectGraph = null;
    private static volatile boolean sInitialized = false;


    public static synchronized void init(Object... rootModules) {
        sObjectGraph = sObjectGraph == null ? ObjectGraph.create(rootModules) : sObjectGraph.plus(rootModules);
    }

    public static void injectStatics() {
        sObjectGraph.injectStatics();
    }

    public static void inject(final Object target) {
        sObjectGraph.inject(target);
    }

    public static <T> T resolve(Class<T> type) {
        return sObjectGraph.get(type);
    }

    public static void add(Object... object) {
        sObjectGraph = ObjectGraph.create(object);
    }
}
