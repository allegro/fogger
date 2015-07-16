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

package pl.allegro.foggerexample.config.dagger.module;

import dagger.Module;
import dagger.Provides;
import pl.allegro.fogger.utils.TaskRunner;
import pl.allegro.fogger.utils.TaskRunnerImpl;
import pl.allegro.foggerexample.config.FoggerExampleApplication;
import pl.allegro.foggerexample.config.application.ApplicationRunConfiguration;
import pl.allegro.foggerexample.ui.ComponentsActivity;
import pl.allegro.foggerexample.ui.ComponentsFragment;
import pl.allegro.foggerexample.utils.Ln;

@Module(
    includes = {
        AndroidModule.class
    },
    injects = {
            FoggerExampleApplication.class,
            TaskRunner.class,
            ApplicationRunConfiguration.class,
            RootModule.class,
            ComponentsActivity.class,
            ComponentsFragment.class
    },
    staticInjections = {
            Ln.class,
    },
    library = true,
    complete = false
)
public class RootModule {

    @Provides
    TaskRunner provideTaskRunner() {
        return new TaskRunnerImpl();
    }

}