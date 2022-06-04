package com.example.colorcircleshp.util;

public class Rounder {

    public static double roundToTwoDigitalsLessThanOne(double val) {
        return (Math.rint(val * 100d) / 100);
    }

}

