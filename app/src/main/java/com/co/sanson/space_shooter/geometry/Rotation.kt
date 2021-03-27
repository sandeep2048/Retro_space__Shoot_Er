package com.co.sanson.space_shooter.geometry

import android.graphics.Canvas
import kotlin.random.Random

class Rotation(
    initial: Double
) {
    var angle: Double = wraparound(initial)

    fun reset(a: Double) {
        angle = a
    } // reset

    operator fun plusAssign(delta: Double) {
        angle += delta

        angle = wraparound(angle + delta)
    } // plusAssign

    operator fun minus(rhs: Rotation): Rotation {
        return Rotation(angle - rhs.angle)
    } // minus

    fun clone() = Rotation(angle)

    fun rotate(canvas: Canvas) =
        canvas.rotate(angle.toFloat())
    fun unrotate(canvas: Canvas) =
        canvas.rotate(-angle.toFloat())

    private fun wraparound(a: Double): Double {
        if (a > 180) return a-360
        if (a < -180) return a+360
        return a
    } // wraparound

    companion object {
        fun random() = Rotation(Random.nextDouble(360.0))
        fun none() = Rotation(0.0)
    } // companion object
} // Rotation