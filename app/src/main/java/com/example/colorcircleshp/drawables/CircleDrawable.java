package com.example.colorcircleshp.drawables;


public abstract class CircleDrawable extends AbstractCirclePickerDrawable {

    public int radius;

    public int getRadius() {
        return radius;
    }

    abstract public void init();

}