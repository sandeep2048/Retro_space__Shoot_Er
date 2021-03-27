package com.co.sanson.space_shooter.craft.asteroid

import android.graphics.Canvas
import android.graphics.Paint
import com.co.sanson.space_shooter.Game
import com.co.sanson.space_shooter.craft.Ship
import com.co.sanson.space_shooter.craft.spaceman.OrangeSpaceman
import com.co.sanson.space_shooter.craft.Target
import com.co.sanson.space_shooter.geometry.Point
import com.co.sanson.space_shooter.wave.Wave
import kotlin.random.Random

class StonyAsteroid(
    game: Game,
    wave: Wave,
    pos: Point,
    scale: Float = Big
): Asteroid(game, wave, pos, scale) {
   override fun drawAsteroid(canvas: Canvas) {
       canvas.drawLines(
           shape,
           brush
       )
   } // drawAsteroid

    override fun shot(): Target.Impact {
        game.scored(400/size.toInt())
        split()
        return Target.Impact.SOFT
    } // shot

    override fun shipCollision(ship: Ship) {
        split()
        ship.hit()
    } // shipCollision

    private fun split() {
        if (Random.nextFloat() < 0.1f)
            OrangeSpaceman(game, wave, position)
        explode()

        if (size != Small) {
            scale /= 2
            velocity = AsteroidVector(scale)
            StonyAsteroid(
                game,
                wave,
                position,
                scale
            )
        } else {
            wave.removeTarget(this)
        }
    } // split

    companion object {
        val brush = Paint()

        init {
            brush.setARGB(255, 160, 160, 160)
            brush.strokeWidth = 3f
            brush.strokeCap = Paint.Cap.ROUND
            brush.strokeJoin = Paint.Join.ROUND
            brush.style = Paint.Style.STROKE
        }

        fun field(
            game: Game,
            wave: Wave,
            big: Int,
            medium: Int = 0,
            small: Int = 0,
            originFn: () -> Point = { game.extent.randomPointOnEdge() }
        ) {
            field(
                ::StonyAsteroid,
                game,
                wave,
                big,
                medium,
                small,
                originFn
            )
        } // field
    } // companion object
} // Asteroid