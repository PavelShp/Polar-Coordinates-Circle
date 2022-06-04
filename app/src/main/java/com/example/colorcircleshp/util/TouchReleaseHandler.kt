package com.example.colorcircleshp.util

import com.example.colorcircleshp.interfaces.listenerBothValues

class TouchReleaseHandler<T, Q>(val funcProvider: (() -> ValHolder<T, Q>)) {

    private val callbackBothValues: MutableList<listenerBothValues<T, Q>>

    fun addCallbackActionUp(calback: listenerBothValues<T, Q>): MutableList<listenerBothValues<T, Q>> {
        callbackBothValues.add(calback)
        return callbackBothValues
    }

    fun callCallbacks() {

        val pair = funcProvider.invoke()
        for (callback in callbackBothValues) {
            pair.let {
                callback.sendResult(pair.firstVal, pair.secondVal)
            }
        }

    }

    init {
        callbackBothValues = ArrayList()
    }
}