package com.polarcircleshp.colorcircleshp.receivers

import com.polarcircleshp.colorcircleshp.interfaces.ResultListener

abstract class ReceiverRadius<T> {

    abstract fun receiveRadius(radius: Float) : T

    val listeners : ArrayList<ResultListener<T>> = ArrayList()

    fun addResultListener( listener : ResultListener<T>){
        listeners.add(listener)
    }

}