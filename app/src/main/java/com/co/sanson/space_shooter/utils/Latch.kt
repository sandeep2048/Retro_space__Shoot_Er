package com.co.sanson.space_shooter.utils

import kotlin.math.max

class Latch(
    startFrom: Int,
    private val action: () -> Unit
) {
    var count: Float = startFrom.toFloat()
    val done get() = count <= 0f
    val running get() = !done

    fun tick(frameRateScale: Float): Int {
        if (done) return 0

        count -= frameRateScale
        if (done) action()
        return max(count.toInt(), 0)
    } // tick
} // class Latch