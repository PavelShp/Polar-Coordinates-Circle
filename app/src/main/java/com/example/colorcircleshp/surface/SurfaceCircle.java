package com.example.colorcircleshp.surface;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.View;

import com.example.colorcircleshp.drawables.CircleDrawable;
import com.example.colorcircleshp.drawables.Pointer;
import com.example.colorcircleshp.interfaces.ViewSizeInit;

public class SurfaceCircle extends View {

    private CircleDrawable circleOutside;
    private Pointer pointer;
    private int width, height;

    ViewSizeInit initOperation;

    public SurfaceCircle(Context context) {
        super(context);
        setAccelerationType();
    }


    public SurfaceCircle(Context context, AttributeSet attrs) {
        super(context, attrs);
        setAccelerationType();
    }

    public SurfaceCircle(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setAccelerationType();
    }

    public SurfaceCircle(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        setAccelerationType();
    }

    private void setAccelerationType() {
        this.setLayerType(View.LAYER_TYPE_HARDWARE, null);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        this.width = w;
        this.height = h;
        if (initOperation != null)
            initOperation.init(width, height);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawObjects(canvas);
    }

    public void drawObjects(Canvas canvas) {

        if (canvas == null) return;

        canvas.drawColor(Color.WHITE);

        if (circleOutside != null)
            circleOutside.draw(canvas);

        if (pointer != null)
            pointer.draw(canvas);

    }

    public void initObjects() {
        if (initOperation != null && width != 0 && height != 0)
            initOperation.init(width, height);
    }

    public SurfaceCircle setInitOperation(ViewSizeInit init) {
        this.initOperation = init;
        return this;
    }

    public SurfaceCircle setCircleOutside(CircleDrawable circleOutside) {
        this.circleOutside = circleOutside;
        return this;
    }

    public SurfaceCircle setSlider(Pointer pointer) {
        this.pointer = pointer;
        return this;
    }


}

