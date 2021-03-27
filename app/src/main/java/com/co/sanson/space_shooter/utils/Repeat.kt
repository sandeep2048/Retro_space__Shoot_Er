package com.co.sanson.space_shooter.utils

import kotlin.math.max

class Repeat(
    private var start: Int,
    private val action: () -> Unit
) {
    private var count = start.toFloat()

    fun tick(frameRateScale: Float): Int {
        count -= frameRateScale
        val dec = max(count.toInt(), 0)
        if (dec == 0) {
            action()
            count = start.toFloat()
        }
        return dec
    } // tick

    fun reset(newStart: Int) {
        start = newStart
    } // reset
} // class Repeat