package com.co.sanson.space_shooter.wave.transition

import android.graphics.Canvas
import com.co.sanson.space_shooter.craft.Ship
import com.co.sanson.space_shooter.wave.Wave

class Emptiness: Wave {
    override val ship: Ship? = null
    override fun update(frameRateScale: Float) = Unit
    override fun draw(canvas: Canvas) = Unit
} // Emptiness