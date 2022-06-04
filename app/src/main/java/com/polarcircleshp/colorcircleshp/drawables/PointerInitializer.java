package com.polarcircleshp.colorcircleshp.drawables;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class PointerInitializer {

    public Bitmap getBitmapSlider(Context context, int mipmapId, int widthOfSlider, int heightOfSlider) {
        Bitmap bm = BitmapFactory.decodeResource(
                context.getResources(),
                mipmapId
        );

        return Bitmap.createScaledBitmap(
                bm, widthOfSlider, heightOfSlider, true);
    }

}
