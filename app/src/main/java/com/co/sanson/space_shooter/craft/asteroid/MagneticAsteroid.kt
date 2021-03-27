package com.co.sanson.space_shooter.craft.asteroid

import android.graphics.Canvas
import android.graphics.Paint
import com.co.sanson.space_shooter.Game
import com.co.sanson.space_shooter.craft.Ship
import com.co.sanson.space_shooter.craft.spaceman.OrangeSpaceman
import com.co.sanson.space_shooter.craft.Target
import com.co.sanson.space_shooter.craft.spaceman.BlueSpaceman
import com.co.sanson.space_shooter.geometry.Point
import com.co.sanson.space_shooter.geometry.Vector
import com.co.sanson.space_shooter.wave.Wave
import kotlin.random.Random

class MagneticAsteroid(
    game: Game,
    wave: Wave,
    pos: Point,
    scale: Float = Big
): Asteroid(game, wave, pos, scale) {
    override fun update(frameRateScale: Float) {
        wave.ship?.let {
            val shipDistance = position.distance(it.position)
            val shipAngle = position.angleTo(it.position)

            val deflectionMagnitude = (2e3 * scale) / (shipDistance * shipDistance)
            val deflectionVector = Vector(deflectionMagnitude, shipAngle)

            velocity += deflectionVector
        }
        super.update(frameRateScale)
    } // update

    override fun drawAsteroid(canvas: Canvas) {
        canvas.drawPath(path, brush)
        canvas.drawLines(shape, outline)
    } // drawAsteroid

    override fun shot(): Target.Impact {
        game.scored(600 / size.toInt())
        split()
        return Target.Impact.HARD
    } // shot

    override fun shipCollision(ship: Ship) {
        ship.hit()
    } // shipCollision

    private fun split() {
        val spacemanPops = Random.nextFloat()
        if (spacemanPops < 0.12f) {
            if (spacemanPops < 0.05f)
                BlueSpaceman(game, wave, position)
            else
                OrangeSpaceman(game, wave, position)
        }
        explode()
        if (size != Small) {
            scale /= 2
            velocity = AsteroidVector(scale)
            MagneticAsteroid(
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
        val outline = Paint()

        init {
            brush.setARGB(255, 153, 204, 255)
            brush.strokeWidth = 3f
            brush.strokeCap = Paint.Cap.ROUND
            brush.strokeJoin = Paint.Join.ROUND
            brush.style = Paint.Style.FILL

            outline.setARGB(255, 51, 153, 255)
            outline.strokeWidth = 3f
            outline.strokeCap = Paint.Cap.ROUND
            outline.strokeJoin = Paint.Join.ROUND
            outline.style = Paint.Style.FILL
        }

        fun field(
            game: Game,
            wave: Wave,
            big: Int
        ) {
            field(
                ::MagneticAsteroid,
                game,
                wave,
                big
            )
        } // field

    } // companion object
} // Asteroid