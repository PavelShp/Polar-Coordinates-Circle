package com.example.colorcircleshp.calculators;

import com.example.colorcircleshp.util.PositionHolder;

public abstract class AngleCalculatorBasedOnXY {

    public abstract void calculateAngleBasedOnPosition(PositionHolder positionHolder);

    public abstract void notifyListeners(float percentage);
}
