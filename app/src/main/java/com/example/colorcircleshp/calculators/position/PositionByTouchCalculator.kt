package com.example.colorcircleshp.calculators.position

import com.example.colorcircleshp.interfaces.ResultListener
import com.example.colorcircleshp.util.PositionHolder
import kotlin.math.abs
import com.example.colorcircleshp.util.MathHelper.Companion.calculateR
import com.example.colorcircleshp.util.MathHelper.Companion.doubleDistanceFromBetweenPoints
import com.example.colorcircleshp.util.MathHelper.Companion.getLeg
import com.example.colorcircleshp.util.MathHelper.Companion.getRatio
import kotlin.math.cos
import kotlin.math.sin

class PositionByTouchCalculator(val xCenter: Int, val yCenter: Int, val radius: Int) {

    private var x = 0
    private var y = 0
    var rTemp = 0f

    private val listenersR: ArrayList<ResultListener<Float>> = ArrayList()
    private val listenersXY: ArrayList<ResultListener<PositionHolder>> = ArrayList()

    fun calculatePositionParameters(xP: Int, yP: Int) {

        val legX = getLeg(xP, xCenter)
        val legY = getLeg(yP, yCenter)

        if (legY == 0) {
            y = yCenter
            x = if (xP > xCenter) xCenter + radius else xCenter - radius
        } else if (legX == 0) {
            x = xCenter
            y = if (yP > yCenter) yCenter + radius else yCenter - radius
        } else if (legX != 0 && legY != 0) {
            rTemp = calculateR(legX, legY)
            if (rTemp > 0) {
                x = xCenter + (getRatio(legX.toFloat(), rTemp) * radius).toInt()
                y = yCenter + (getRatio(legY.toFloat(), rTemp) * radius).toInt()
            }
        } else if (legX == 0 && legY == 0) {
            x = xP
            y = yP
        }

        if (xP > xCenter && yP < yCenter) {
            y -= doubleDistanceFromBetweenPoints(y, yCenter)
        } else if (xP < xCenter && yP < yCenter) {
            y -= doubleDistanceFromBetweenPoints(y,yCenter)
            x -= doubleDistanceFromBetweenPoints(x, xCenter)
        } else if (xP < xCenter && yP > yCenter) {
            x -= doubleDistanceFromBetweenPoints(x, xCenter)
        }

        if (abs(xP - xCenter) <= abs(x - xCenter)) {
            x = xP
        }

        if (abs(yP - yCenter) <= abs(y - yCenter)) {
            y = yP
        }

        if (rTemp == 0f) {
            if (legX == 0)
                rTemp = (y - yCenter).toFloat()
            else if (legY == 0)
                rTemp = (x - yCenter).toFloat()
        }

        rTemp = abs(rTemp)
        if (rTemp > radius) rTemp = radius.toFloat()
        val res: Float = (rTemp / radius)

        notifyListenersXY()
        notifyListenersR(res)

    }

    fun setInitPosition(calculatePercentage: Float, saturation: Float) {
        val cos = cos(calculatePercentage * 2 * Math.PI)
        val sin = sin(calculatePercentage * 2 * Math.PI)
        val xVal = (radius * saturation * cos).toInt()
        val yVal = (radius * saturation * sin).toInt()
        x = xCenter + xVal
        y = yCenter + yVal

        notifyListenersXY()
    }

    fun addListenerR(listener: ResultListener<Float>): PositionByTouchCalculator {
        listenersR.add(listener)
        return this
    }

    fun addListenerXY(listener: ResultListener<PositionHolder>): PositionByTouchCalculator {
        listenersXY.add(listener)
        return this
    }

    private fun notifyListenersR(curHypoten: Float) {
        for (curLis in listenersR) {
            curLis.accept(curHypoten)
        }
    }

    private fun notifyListenersXY() {
        for (curLis in listenersXY) {
            curLis.accept(PositionHolder(x, y))
        }
    }


}