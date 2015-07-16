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

package pl.allegro.foggerexample.config;

import android.app.Application;
import android.app.Instrumentation;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import pl.allegro.foggerexample.config.application.ApplicationRunConfiguration;
import pl.allegro.foggerexample.config.dagger.Injector;
import pl.allegro.foggerexample.config.dagger.module.RootModule;

public class FoggerExampleApplication extends Application {

    private static FoggerExampleApplication instance;

    private ApplicationRunConfiguration applicationRunConfiguration;

    public FoggerExampleApplication() {
    }

    public FoggerExampleApplication(final Context context) {
        super();
        attachBaseContext(context);
        setInstance(this);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        initDaggerOnApplicationCreationStep();
        Injector.inject(this);
    }

    private void initDaggerOnApplicationCreationStep() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        applicationRunConfiguration = ApplicationRunConfiguration.create(sharedPreferences);

        Object[] modules = new Object[]{new RootModule()};
        Injector.init(modules);
        Injector.injectStatics();
    }

    private static void setInstance(FoggerExampleApplication foggerExampleApplication) {
        instance = foggerExampleApplication;
    }

    public FoggerExampleApplication(final Instrumentation instrumentation) {
        super();
        attachBaseContext(instrumentation.getTargetContext());
    }

    public static FoggerExampleApplication getInstance() {
        return instance;
    }

}
