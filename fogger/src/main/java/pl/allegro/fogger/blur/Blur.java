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

package pl.allegro.fogger.blur;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Build;
import android.support.v8.renderscript.Allocation;
import android.support.v8.renderscript.Element;
import android.support.v8.renderscript.RenderScript;
import android.support.v8.renderscript.ScriptIntrinsicBlur;
import android.util.Log;


public class Blur {

    private static final String TAG = Blur.class.getName();

    static {
        try {
            System.loadLibrary("RSSupport");
            System.loadLibrary("rsjni");
        } catch (UnsatisfiedLinkError e) {
            if ("Dalvik".equals(System.getProperty("java.vm.name"))
                || "Art".equals(System.getProperty("java.vm.name"))) {
                throw e;
            }
        }
    }

    public Bitmap fastblur(Context context, Bitmap bitmapToBlur, int radius) {
        Log.i(TAG, "Current build version sdk " + Build.VERSION.SDK_INT);
        Bitmap bitmap = bitmapToBlur.copy(bitmapToBlur.getConfig(), true);

        final RenderScript renderScript = RenderScript.create(context, Build.VERSION.SDK_INT);
        final Allocation input = Allocation.createFromBitmap(renderScript, bitmapToBlur,
                                                                Allocation.MipmapControl.MIPMAP_NONE,
                                                                Allocation.USAGE_SCRIPT);
        final Allocation output = Allocation.createTyped(renderScript, input.getType());
        try {
            final ScriptIntrinsicBlur script = createBlurringScript(radius, renderScript, input);
            script.forEach(output);
            renderScript.finish();
            output.copyTo(bitmap);
        } finally {
            input.destroy();
            output.destroy();
            bitmapToBlur.recycle();
            renderScript.destroy();
        }
        return bitmap;
    }

    private ScriptIntrinsicBlur createBlurringScript(int radius, RenderScript rs, Allocation input) {
        final ScriptIntrinsicBlur script = ScriptIntrinsicBlur.create(rs, Element.U8_4(rs));
        script.setRadius(radius);
        script.setInput(input);
        return script;
    }

}
