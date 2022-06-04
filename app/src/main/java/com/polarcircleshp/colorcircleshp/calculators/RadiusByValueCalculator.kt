package com.polarcircleshp.colorcircleshp.calculators

abstract class RadiusByValueCalculator<T> {
    abstract fun calculateRadius(value: T): Float
}