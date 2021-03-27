package com.co.sanson.space_shooter.craft

import android.graphics.Canvas
import android.graphics.Paint
import com.co.sanson.space_shooter.geometry.Point
import com.co.sanson.space_shooter.geometry.Rotation
import com.co.sanson.space_shooter.geometry.Vector
import com.co.sanson.space_shooter.wave.Wave

class Explosion(
    private val wave: Wave,
    pos: Point,
    private val velocity: Vector,
    initialOrientation: Rotation,
    private val rotation: Double,
    shape: FloatArray,
    brush: Paint,
    length: Int,
    useMid: Boolean = true,
    expansion: Float = 5f
) : Target {
    private val exploder = Exploder(
        { wave.removeTarget(this) },
        shape,
        brush,
        length,
        useMid,
        expansion
    )
    override val position = pos.copy()
    override val killDist = 0f
    private val orientation = initialOrientation.clone()

    init {
        wave.addTarget(this)
    }

    override fun update(frameRateScale: Float) {
        position.drift(velocity, frameRateScale)
        exploder.update(frameRateScale)
    } // update

    override fun draw(canvas: Canvas) {
        canvas.save()

        position.translate(canvas)
        orientation.rotate(canvas)

        exploder.draw(canvas)

        canvas.restore()
    } // draw

    override fun shipCollision(ship: Ship) { }
    override fun shot(): Target.Impact = Target.Impact.NONE
    override fun explode() {}
}