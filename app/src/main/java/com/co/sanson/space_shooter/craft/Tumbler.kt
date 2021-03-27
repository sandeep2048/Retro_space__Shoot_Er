package com.co.sanson.space_shooter.craft

import android.graphics.Canvas
import android.graphics.Paint
import com.co.sanson.space_shooter.Game
import com.co.sanson.space_shooter.R
import com.co.sanson.space_shooter.geometry.Point
import com.co.sanson.space_shooter.geometry.Rotation
import com.co.sanson.space_shooter.geometry.Vector
import com.co.sanson.space_shooter.utils.Repeat
import com.co.sanson.space_shooter.wave.Wave
import kotlin.random.Random

class Tumbler(
    private val game: Game,
    private val wave: Wave,
    traverse: Array<Point> = game.extent.randomHorizontalTraverse()
): Target {
    override val position = Point(traverse[0])
    private val velocity = Vector(Random.nextDouble(2.0, 5.0), position.angleTo(traverse[1]))
    private val orientation = Rotation.random()
    private val rotation = initialRotation()
    private val shooter = Repeat(Random.nextInt(120, 180), { fire() })

    init {
        game.sound(R.raw.sauceralarm, position)
        wave.addTarget(this)
    } // init

    override val killDist get() = 50f

    override fun update(frameRateScale: Float) {
        position.move(velocity, frameRateScale, game.extent, killDist)
        orientation += rotation * frameRateScale
        shooter.tick(frameRateScale)
    } // update

    override fun draw(canvas: Canvas) {
        canvas.save()

        position.translate(canvas)
        orientation.rotate(canvas)

        canvas.drawLines(tumblerShape, shipBrush)

        canvas.restore()
    } // draw

    override fun shot(): Target.Impact {
        game.scored(1000)
        explode()
        return Target.Impact.HARD
    } // shot

    override fun explode() {
        game.sound(R.raw.saucerexplosion, position)
        BigPuff(wave, position)
        Explosion(
            wave, position, velocity, orientation, rotation,
            outerShape, shipBrush, 70
        )
        Explosion(
            wave, position, velocity, orientation, rotation,
            cutOuts, shipBrush, 50, expansion = 3f
        )
        wave.removeTarget(this)
    } // explode

    override fun shipCollision(ship: Ship) = ship.hit()

    private fun fire() {
        game.sound(R.raw.saucerfire, position)
        for (offset in 0..360 step 90) {
            val initialPosition = Point(position)
            val direction = orientation.angle + offset
            initialPosition.move(Vector(60.0, direction, 60.0), 1f, game.extent)
            val missileVelocity = velocity.copy()
            missileVelocity += Vector(7.0, direction)
            Missile(game, wave, initialPosition, missileVelocity, direction.toFloat())
        }
    } // fire

    companion object {
        private fun initialRotation(): Double {
            var rotation = Random.nextDouble(1.0, 2.5)
            if (Random.nextDouble() < 0.5)
                rotation = -rotation
            return rotation
        } // initialRotation

        private val outerShape = floatArrayOf(
            60f, -25f, 60f, 25f,
            60f, 25f, 25f, 60f,
            25f, 60f, -25f, 60f,
            -25f, 60f, -60f, 25f,
            -60f, 25f, -60f, -25f,
            -60f, -25f, -25f, -60f,
            -25f, -60f, 25f, -60f,
            25f, -60f, 60f, -25f
        )

        private val cutOuts = floatArrayOf(
        // upperRightCutout
            48f, -20f, 20f, -48f,
            20f, -48f, 10f, -10f,
            10f, -10f, 48f, -20f,
        // upperLeftCutout
            -48f, -20f, -20f, -48f,
            -20f, -48f, -10f, -10f,
            -10f, -10f, -48f, -20f,
        // lowerLeftCutout
            -48f, 20f, -20f, 48f,
            -20f, 48f, -10f, 10f,
            -10f, 10f, -48f, 20f,
        // lowerRightCutout
            48f, 20f, 20f, 48f,
            20f, 48f, 10f, 10f,
            10f, 10f, 48f, 20f
        )

        private val tumblerShape = outerShape + cutOuts

        val shipBrush = Paint()

        init {
            shipBrush.setARGB(255, 255, 102, 0)
            shipBrush.strokeWidth = 8f
            shipBrush.strokeCap = Paint.Cap.ROUND
            shipBrush.style = Paint.Style.STROKE
        } // init
    }
} // class Tumbler
