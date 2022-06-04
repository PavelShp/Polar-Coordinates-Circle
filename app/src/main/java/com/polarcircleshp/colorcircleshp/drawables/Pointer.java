package com.polarcircleshp.colorcircleshp.drawables;


import android.graphics.Bitmap;
import android.graphics.Canvas;

public class Pointer extends AbstractCirclePickerDrawable {

    private final Bitmap bitmapSlider;
    int xPosition;
    int yPosition;

    public void setXPosition(int x) {
        xPosition = x;
    }

    public void setYPosition(int y) {
        yPosition = y;
    }

    private int widthSlider;
    private int heightSlider;
    private final int radius;



    public Pointer(Bitmap drawable,
                   int xCenter,
                   int yCenter,
                   int radius) {

        bitmapSlider = drawable;
        this.xCenter = xCenter;
        this.yCenter = yCenter;
        this.radius = radius;

        widthSlider = drawable.getWidth();
        heightSlider = drawable.getHeight();

        init();
    }

    private void init() {
        xPosition = xCenter + radius;
        yPosition = yCenter;
    }

    public void draw(Canvas canvas) {
        int topY = yPosition - (int) heightSlider;
        int topX = xPosition - (int) (widthSlider / 2f);
        canvas.drawBitmap(bitmapSlider, topX, topY, null);
    }


}
