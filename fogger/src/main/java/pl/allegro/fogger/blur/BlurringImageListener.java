package pl.allegro.fogger.blur;

import android.graphics.Bitmap;

public interface BlurringImageListener {
    void onBlurringFinish(Bitmap blurredImage);
}
