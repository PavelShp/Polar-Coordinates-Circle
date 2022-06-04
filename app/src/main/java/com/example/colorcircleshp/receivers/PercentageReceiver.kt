package com.example.colorcircleshp.receivers

import com.example.colorcircleshp.interfaces.ResultListener

abstract class PercentageReceiver<T> {

    abstract fun receivePercentageOfCircle(angle: Float) : T
    val listeners : ArrayList<ResultListener<T>> = ArrayList()

    fun addResultListener( listener : ResultListener<T>){
        listeners.add(listener)
    }
}
