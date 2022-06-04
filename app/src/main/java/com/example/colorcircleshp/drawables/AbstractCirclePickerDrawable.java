package com.example.colorcircleshp.drawables;


import android.graphics.Canvas;

public abstract class AbstractCirclePickerDrawable {

    public int xCenter;
    public int yCenter;

    public abstract void draw(Canvas canvas);

}