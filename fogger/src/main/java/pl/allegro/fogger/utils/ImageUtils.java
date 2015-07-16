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

package pl.allegro.fogger.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import pl.allegro.fogger.exception.FoggerException;

import java.io.*;

public class ImageUtils {

    private static final String TAG = ImageUtils.class.getName();

    private static final int PNG_QUALITY_VALUE = 1;

    public Bitmap createBitmapFromFile(String pathToFile) {
        return BitmapFactory.decodeFile(pathToFile);
    }

    public static void storeImageAsPNG(Bitmap image, File pictureFile, boolean failOnError) {
        checkPictureDestinationFile(pictureFile);
        FileOutputStream fileOutputStream = null;
        try {
            fileOutputStream = new FileOutputStream(pictureFile);
            image.compress(Bitmap.CompressFormat.PNG, PNG_QUALITY_VALUE, fileOutputStream);
        } catch (FileNotFoundException e) {
            Log.e(TAG, "Destination file not found because of " + e.getMessage());
            if (failOnError) {
                throw new FoggerException("Cannot save file: " + pictureFile.getPath());
            }
        } finally {
            closeStream(fileOutputStream);
        }
    }

    private static void checkPictureDestinationFile(File pictureFile) {
        if (pictureFile == null) {
            Log.e(TAG, "You did not provide file to save bitmap.");
            throw new FoggerException("You did not provide file to save bitmap.");
        }
    }

    private static void closeStream(OutputStream stream) {
        if (stream != null) {
            try {
                stream.close();
            } catch (IOException e) {
                Log.e(TAG, "Problem during stream closing", e);
            }
        }
    }
}
