package com.example.colorcircleshp.initializers;


import android.graphics.Bitmap;

import com.example.colorcircleshp.surface.SurfaceCircle;
import com.example.colorcircleshp.calculators.AngleCalculator;
import com.example.colorcircleshp.calculators.AngleCalculatorBasedOnXY;
import com.example.colorcircleshp.calculators.AngleByValueCalculator;

import com.example.colorcircleshp.calculators.RadiusByValueCalculator;
import com.example.colorcircleshp.calculators.position.PositionByTouchCalculator;
import com.example.colorcircleshp.drawables.CircleDrawable;
import com.example.colorcircleshp.drawables.Pointer;
import com.example.colorcircleshp.drawables.PointerInitializer;
import com.example.colorcircleshp.interfaces.ActionAngleReverseCalculate;
import com.example.colorcircleshp.interfaces.ActionCalculate;
import com.example.colorcircleshp.interfaces.listenerBothValues;
import com.example.colorcircleshp.interfaces.ResultListener;
import com.example.colorcircleshp.util.ValHolder;
import com.example.colorcircleshp.receivers.PercentageReceiver;
import com.example.colorcircleshp.receivers.ReceiverRadius;
import com.example.colorcircleshp.touchListener.CirclePickerOnTouchListener;
import com.example.colorcircleshp.util.TouchReleaseHandler;
import com.example.colorcircleshp.util.PositionHolder;

import java.util.ArrayList;
import java.util.List;

public class CirclePolarBuilder<T, Q>  {

    private final SurfaceCircle surfaceView;
    private T curValueDerivedByAngle;
    private Q curValueDerivedByRadius;
    private List<listenerBothValues<T, Q>> callbackBothValuesList = new ArrayList<>();

    private final TouchReleaseHandler<T, Q> touchReleaseHandler;

    private PercentageReceiver<T> calculatorValueAngle = null;
    private AngleByValueCalculator<T> reverseCalculatorValueAngle = null;

    private ReceiverRadius<Q> calculatorValueRadius = null;
    private RadiusByValueCalculator<Q> reverseCalculatorValueRadius = null;

    private PositionByTouchCalculator calculatorPositionByTouch = null;

    private AngleCalculatorBasedOnXY angleCalculator;


    private Integer mipmapId = null;
    private CircleDrawable circle = null;
    private int widthOfSlider = 72;
    private int heightOfSlider = 72;

    public CirclePolarBuilder(SurfaceCircle surfaceView,
                              T initialTransformedXY,
                              Q initValueDerivedByRadius // can calculate R based on XY though
    ) {
        this.surfaceView = surfaceView;
        this.curValueDerivedByAngle = initialTransformedXY;
        this.curValueDerivedByRadius = initValueDerivedByRadius;

        touchReleaseHandler = new TouchReleaseHandler<>(
                () -> new ValHolder<>(curValueDerivedByAngle, curValueDerivedByRadius)
        );

    }

    private void init(T initColor, Q initSaturation) {

        Bitmap pointer = new PointerInitializer().getBitmapSlider(surfaceView.getContext(), mipmapId, widthOfSlider, heightOfSlider);

        surfaceView.setInitOperation((width, height) -> {
            int minusHeight = 0;
            int minusWidth = 0;
            if (pointer != null) {
                minusHeight = pointer.getHeight();
                minusWidth = pointer.getWidth() / 2;
            }
            int radius =  (int) ((Math.min(width, height) - minusHeight - minusWidth) / 1.85f);

            calculatorValueAngle.addResultListener(this::setCurValueRerivedByAngle);
            calculatorValueAngle.addResultListener((value) -> {
                notifyListenerBothValue();
            });

            setCurValueDerivedFromRadius(initSaturation);

            calculatorPositionByTouch = new PositionByTouchCalculator(width / 2, height / 2, radius);
            calculatorValueRadius.addResultListener(this::setCurValueDerivedFromRadius);
            calculatorValueRadius.addResultListener((value) -> {
                notifyListenerBothValue();
            });

            calculatorPositionByTouch.addListenerR((rd) -> {
                Q res = calculatorValueRadius.receiveRadius(rd);
                ArrayList<ResultListener<Q>> list = calculatorValueRadius.getListeners();
                for (ResultListener<Q> curListener : list) {
                    curListener.accept(res);
                }
            });

            AngleCalculator<T> angleCalculatorLocal = new AngleCalculator<>(width / 2, height / 2, radius);
            angleCalculatorLocal.addReceiver(calculatorValueAngle);
            angleCalculator = angleCalculatorLocal;

            if (pointer != null) {
                Pointer slider = new Pointer(pointer, width / 2, height / 2, radius);

                calculatorPositionByTouch.addListenerXY((coords) -> {
                    slider.setXPosition(coords.getX());
                    slider.setYPosition(coords.getY());
                    angleCalculator.calculateAngleBasedOnPosition(
                            new PositionHolder(coords.getX(), coords.getY())
                    );
                    surfaceView.invalidate();
                });

                circle.radius = radius;
                circle.xCenter = width / 2;
                circle.yCenter = height / 2;
                circle.init();
                surfaceView.setCircleOutside(circle)
                        .setSlider(slider);
            }

            initValueAndPositionByDerivedFromAngle(initColor, curValueDerivedByRadius);
            setCurValueDerivedFromRadius(curValueDerivedByRadius);

            surfaceView.setOnTouchListener(new CirclePickerOnTouchListener<>(calculatorPositionByTouch, touchReleaseHandler));

        });

    }

    public void initValueAndPositionByDerivedFromAngle(T curValueDerivedByAngle, Q curValueDerivedByRadius) {

        if (reverseCalculatorValueAngle == null || angleCalculator == null)
            return;

        float percentage = reverseCalculatorValueAngle.calculateAngle(curValueDerivedByAngle);

        setInitPosition(
                reverseCalculatorValueAngle.calculateAngle(curValueDerivedByAngle),
                reverseCalculatorValueRadius.calculateRadius(curValueDerivedByRadius)
        );

        setCurValueRerivedByAngle(curValueDerivedByAngle);
        angleCalculator.notifyListeners(percentage);
    }

    public void setCurValueDerivedFromRadius(Q curSaturation) {
        curValueDerivedByRadius = curSaturation;
    }

    private void notifyListenerBothValue() {
        for (listenerBothValues<T, Q> curListener : callbackBothValuesList) {
            curListener.sendResult(curValueDerivedByAngle, curValueDerivedByRadius);
        }
    }

    public T getCurValueDerivedByXY() {
        return curValueDerivedByAngle;
    }

    public void setCurValueRerivedByAngle(T curvalue) {
        this.curValueDerivedByAngle = curvalue;
    }

    public void invalidateView() {
        surfaceView.initObjects();
        surfaceView.invalidate();
    }

    public SurfaceCircle getSurfaceView() {
        return surfaceView;
    }

    public CirclePolarBuilder<T, Q> addCalculatorValue(PercentageReceiver<T> receiver) {
        calculatorValueAngle = receiver;
        return this;
    }

    public CirclePolarBuilder<T, Q> addCalculatorValue(ActionCalculate<T> calculateFunction) {
        calculatorValueAngle = new PercentageReceiver<T>() {
            @Override
            public T receivePercentageOfCircle(float angle) {
                return calculateFunction.calculate(angle);
            }
        };
        return this;
    }

    public CirclePolarBuilder<T, Q> addReverseCalculatorValue(AngleByValueCalculator<T> calculator) {
        this.reverseCalculatorValueAngle = calculator;
        return this;
    }

    public CirclePolarBuilder<T, Q> addReverseCalculatorValue(ActionAngleReverseCalculate<T> calculator) {
        this.reverseCalculatorValueAngle = new AngleByValueCalculator<T>() {
            @Override
            public float calculateAngle(T val) {
                return calculator.getAngleFromValue(val);
            }
        };
        return this;
    }

    public CirclePolarBuilder<T, Q> build() {
        init(curValueDerivedByAngle, curValueDerivedByRadius);
        invalidateView();
        return this;
    }

    public void setInitPosition(float calculatePercentage, float saturation) {
        if (calculatorPositionByTouch != null)
            calculatorPositionByTouch.setInitPosition(calculatePercentage, saturation);

        surfaceView.invalidate();
    }

    public CirclePolarBuilder<T, Q> addCalculatorValueRadius(ReceiverRadius<Q> calculatorValueRadius) {
        this.calculatorValueRadius = calculatorValueRadius;
        return this;
    }

    public CirclePolarBuilder<T, Q> addCalculatorValueRadius(ActionCalculate<Q> calculatorValueRadius) {
        this.calculatorValueRadius = new ReceiverRadius<Q>() {
            @Override
            public Q receiveRadius(float radius) {
                return calculatorValueRadius.calculate(radius);
            }
        };
        return this;
    }

    public CirclePolarBuilder<T, Q> addReverseCalculatorValueRadius(RadiusByValueCalculator<Q> reverseCalculatorValueRadius) {
        this.reverseCalculatorValueRadius = reverseCalculatorValueRadius;
        return this;
    }

    public CirclePolarBuilder<T, Q> addActionUpCallback(listenerBothValues<T, Q> callback) {
        touchReleaseHandler.addCallbackActionUp(callback);
        return this;
    }


    public CirclePolarBuilder<T, Q> setPointerResourceId(int icPickerCircleColor) {
        this.mipmapId = icPickerCircleColor;
        return this;
    }


    public CirclePolarBuilder<T, Q> setCircleOutside(CircleDrawable circleOutsideColor) {
        this.circle = circleOutsideColor;
        return this;
    }


    public CirclePolarBuilder<T, Q> addListenerCurrentValueAngle(ResultListener<T> listener) {
        calculatorValueAngle.addResultListener(listener);
        return this;
    }

    public CirclePolarBuilder<T, Q> addListenerCurrentValueRadius(ResultListener<Q> listener) {
        calculatorValueRadius.addResultListener(listener);
        return this;
    }

    public CirclePolarBuilder<T, Q> addListenerBoth(listenerBothValues<T, Q> callback) {
        callbackBothValuesList.add(callback);
        return this;
    }

    public CirclePolarBuilder<T, Q> setPointerDimensions(int width, int height) {
        widthOfSlider = width;
        heightOfSlider = height;
        return this;
    }

}

