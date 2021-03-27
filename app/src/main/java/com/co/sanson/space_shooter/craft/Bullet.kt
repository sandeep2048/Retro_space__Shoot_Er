package com.co.sanson.space_shooter.craft

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import com.co.sanson.space_shooter.Game
import com.co.sanson.space_shooter.geometry.Point
import com.co.sanson.space_shooter.geometry.Rotation
import com.co.sanson.space_shooter.geometry.Vector
import com.co.sanson.space_shooter.wave.Wave

//////////////////////
class Bullet(
    private val game: Game,
    private val wave: Wave,
    pos: Point,
    orientation: Rotation,
    initVel: Vector,
    private val maxAge: Int
): Projectile {
    override val position = pos.copy()
    override val killDist = 1f

    private val velocity = initVel.copy()
    private var age = 0

    init {
        velocity.maximum = 30.0
        velocity += Vector(10.0, orientation, 30.0)

        wave.addProjectile(this)
    } // init

    override fun update(frameRateScale: Float) {
        position.move(velocity, frameRateScale, game.extent, Ship.Radius)
        if (++age > maxAge) wave.removeProjectile(this)
    } // update

    override fun draw(canvas: Canvas) {
        position.draw(canvas, brush)
    } // draw

    override fun hit(impact: Target.Impact) {
        when (impact) {
            Target.Impact.HARD -> age = 100
            Target.Impact.SOFT -> age += 20
            Target.Impact.NONE -> Unit
        }
    } // hit

    companion object {
        val brush = Paint()

        init {
            brush.color = Color.MAGENTA
            brush.alpha = 255
            brush.strokeWidth = 10f
            brush.strokeCap = Paint.Cap.ROUND
        }
    } // companion object
} // Bullet