package com.example.colorcircleshp.interfaces

fun interface listenerBothValues<T, Q> {
    fun sendResult(first: T, second: Q)
}