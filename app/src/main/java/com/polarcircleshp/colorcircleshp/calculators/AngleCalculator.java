package com.polarcircleshp.colorcircleshp.calculators;

import com.polarcircleshp.colorcircleshp.receivers.PercentageReceiver;
import com.polarcircleshp.colorcircleshp.util.PositionHolder;

import java.util.ArrayList;
import java.util.List;

public class AngleCalculator<T> extends AngleCalculatorBasedOnXY {

    private final int xCenter;
    private final int yCenter;
    private final int radius;
    private float curAngle;

    List<PercentageReceiver<T>> receivers = new ArrayList<>();

    public AngleCalculator(int xCenter, int yCenter, int radius) {
        this.xCenter = xCenter;
        this.yCenter = yCenter;
        this.radius = radius;
    }

    public PositionHolder getXYfromCenter(int xInput, int yInput) {
        int x = xInput - xCenter;
        int y = yInput - yCenter;
        return new PositionHolder(x, y);
    }

    @Override
    public void calculateAngleBasedOnPosition(PositionHolder vector) {
        float temp = getAngle(getXYfromCenter(xCenter + radius, yCenter), getXYfromCenter(vector.getX(), vector.getY()));
        float angle = (float) (temp / (2 * Math.PI));
        if (angle < 0) angle = 1 + angle;
        curAngle = angle;
        notifyListeners(curAngle);
    }


    @Override
    public void notifyListeners(float percentage) {
        if (receivers != null) {
            for (PercentageReceiver<T> receiver : receivers)
                receiver.receivePercentageOfCircle(percentage);
        }
    }


    private float getAngle(PositionHolder vector1, PositionHolder vector2) {

        return (float) Math.atan2(vector1.getX() * vector2.getY() - vector1.getY() * vector2.getX(), vector1.getX() * vector2.getX() + vector1.getY() * vector2.getY());

    }

    public List<PercentageReceiver<T>> getReceivers() {
        return receivers;
    }

    public void addReceiver(PercentageReceiver<T> receiver) {
        receivers.add(receiver);
    }

}

