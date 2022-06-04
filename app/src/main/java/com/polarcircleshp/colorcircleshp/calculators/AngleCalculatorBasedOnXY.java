package com.polarcircleshp.colorcircleshp.calculators;

import com.polarcircleshp.colorcircleshp.util.PositionHolder;

public abstract class AngleCalculatorBasedOnXY {

    public abstract void calculateAngleBasedOnPosition(PositionHolder positionHolder);

    public abstract void notifyListeners(float percentage);
}
