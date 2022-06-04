package com.polarcircleshp.colorcircleshp.interfaces

fun interface listenerBothValues<T, Q> {
    fun sendResult(first: T, second: Q)
}