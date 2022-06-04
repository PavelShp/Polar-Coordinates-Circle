package com.polarcircleshp.colorcircleshp.touchListener;


import android.view.MotionEvent;
import android.view.View;

import com.polarcircleshp.colorcircleshp.calculators.position.PositionByTouchCalculator;
import com.polarcircleshp.colorcircleshp.util.TouchReleaseHandler;

public class CirclePickerOnTouchListener<T, Q> implements View.OnTouchListener {

    private final TouchReleaseHandler<T, Q> touchReleaseHandler;
    PositionByTouchCalculator calculatorPositionByTouch = null;

    public CirclePickerOnTouchListener(PositionByTouchCalculator calculatorPositionByTouch,
                                       TouchReleaseHandler<T, Q> touchReleaseHandler) {
        this.calculatorPositionByTouch = calculatorPositionByTouch;
        this.touchReleaseHandler = touchReleaseHandler;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {

        int action = event.getActionMasked();

        int[] location = new int[2];
        v.getLocationOnScreen(location);
        float screenX = event.getRawX();
        float screenY = event.getRawY();
        float viewX = screenX - location[0];
        float viewY = screenY - location[1];

        switch (action) {

            case MotionEvent.ACTION_UP:
                touchReleaseHandler.callCallbacks();
                break;

            case MotionEvent.ACTION_CANCEL:

            case MotionEvent.ACTION_OUTSIDE:
                break;

        }

        calculatorPositionByTouch.calculatePositionParameters((int) viewX, (int) viewY);

        return true;

    }


}

