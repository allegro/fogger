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

package pl.allegro.fogger;


public final class FoggerConfig {

    public static final int DIALOG_BACKGROUND_FADE_IN_ANIMATION_DURATION = 160;
    public static final String BLURRED_SCREENSHOT_FILE_NAME = "blurred_background.png";
    public static final int DRAWER_INITIAL_BLUR_ANIMATION_DURATION = 150;

    private static final int DEFAULT_DRAWER_BACKGROUND_ANIMATION_SPEED = 15;
    private static final float DEFAULT_SCREENSHOT_SCALE = 0.15f;
    private static final float DEFAULT_BACKGROUND_ANIMATION_FIRST_ALPHA_LEVEL = 0.2f;
    private static final int DEFAULT_BACKGROUND_BLUR_RADIUS = 4;

    private static int backgroundColorResourceId = -1;
    private static int backgroundBlurRadius = DEFAULT_BACKGROUND_BLUR_RADIUS;
    private static long drawerBackgroundAnimationSpeed = DEFAULT_DRAWER_BACKGROUND_ANIMATION_SPEED;
    private static float backgroundAnimationFirstAlphaLevel = DEFAULT_BACKGROUND_ANIMATION_FIRST_ALPHA_LEVEL;
    private static float screenshotScale = DEFAULT_SCREENSHOT_SCALE;

    private FoggerConfig() {
    }

    public static int getBackgroundColorResourceId() {
        return backgroundColorResourceId;
    }

    public static void setBackgroundColorResourceId(int backgroundColorResourceId) {
        FoggerConfig.backgroundColorResourceId = backgroundColorResourceId;
    }

    public static long getDrawerBackgroundAnimationSpeed() {
        return drawerBackgroundAnimationSpeed;
    }

    public static int getBackgroundBlurRadius() {
        return backgroundBlurRadius;
    }

    public static float getScreenshotScale() {
        return screenshotScale;
    }

    public static float getBackgroundAnimationFirstAlphaLevel() {
        return backgroundAnimationFirstAlphaLevel;
    }
}
