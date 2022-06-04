package com.polarcircleshp.colorcircleshp.util

import kotlin.math.abs
import kotlin.math.pow
import kotlin.math.sqrt

class MathHelper {
    companion object {
        fun doubleDistanceFromBetweenPoints(xPosition: Int, secondPosition: Int): Int {
            return abs(2 * (xPosition - secondPosition))
        }

        fun getRatio(legY: Float, hypotenuse: Float): Float {
            return legY / hypotenuse
        }

        fun getLeg(position: Int, centerPosition: Int): Int {
            return abs(position - centerPosition)
        }

        fun calculateR(legX: Int, legY: Int): Float {
            return sqrt(
                legX.toDouble().pow(2.0) +
                        legY.toDouble().pow(2.0)
            ).toFloat()
        }
    }
}