package com.co.sanson.space_shooter.craft

import android.graphics.Canvas
import com.co.sanson.space_shooter.geometry.Point

class Nothing(): Target {
    override val position: Point = Point.Hyperspace
    override val killDist: Float = 0f

    override fun update(frameRateScale: Float) = Unit
    override fun draw(canvas: Canvas) = Unit

    override fun shipCollision(ship: Ship) = Unit
    override fun shot(): Target.Impact = Target.Impact.NONE
    override fun explode() = Unit
}