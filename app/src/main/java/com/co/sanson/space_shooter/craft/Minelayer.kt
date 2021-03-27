package com.co.sanson.space_shooter.craft

import android.graphics.Canvas
import android.graphics.Paint
import com.co.sanson.space_shooter.Game
import com.co.sanson.space_shooter.R
import com.co.sanson.space_shooter.geometry.Point
import com.co.sanson.space_shooter.geometry.Vector
import com.co.sanson.space_shooter.utils.RestartableLatch
import com.co.sanson.space_shooter.wave.Wave
import kotlin.random.Random

class Minelayer(
    private val game: Game,
    private val wave: Wave,
    private val onDestroyed: () -> Unit,
    traverse: Array<Point> = game.extent.randomTraverse(),
    private val alwaysDrop: Boolean = false
): Target {
    override val position = traverse[0]
    private val velocity = Vector(4.0, position.angleTo(traverse[1]))
    private val shipBrush = Paint()
    private val shieldBrush = Paint()
    private var shieldRadius = 75f
    private var dropAt = Point(position)
    private var trigger = RestartableLatch(25, ::dropMine)
    private val minefield = game.extent.inflated(-30f)
    private var dropping = Random.nextBoolean()

    init {
        trigger.start()

        game.sound(R.raw.minelayeralarm, position)
        wave.addTarget(this)

        shipBrush.setARGB(225, 60, 255, 255)
        shipBrush.strokeWidth = 8f
        shipBrush.strokeCap = Paint.Cap.ROUND
        shipBrush.style = Paint.Style.STROKE

        shieldBrush.setARGB(80, 180, 0, 180)
        shieldBrush.strokeWidth = 16f
        shieldBrush.strokeCap = Paint.Cap.ROUND
        shieldBrush.style = Paint.Style.STROKE
    } // init

    private fun dropMine() {
        if (Random.nextFloat() < 0.2) dropping = !dropping

        if (minefield.within(dropAt) && (dropping || alwaysDrop))
            Mine(game, wave, dropAt)
        dropAt = Point(position)
        trigger.start()
    } // dropMine

    override val killDist get() = shieldRadius

    override fun update(frameRateScale: Float) {
        trigger.tick(frameRateScale)

        if (!position.moveNoWrap(velocity, frameRateScale, game.extent, killDist))
            destroyed()
    } // update

    override fun draw(canvas: Canvas) {
        canvas.save()

        position.translate(canvas)
        canvas.rotate(velocity.angle.toFloat())
        canvas.drawLines(shape, shipBrush)
        if (shieldRadius >= 50f)
            canvas.drawCircle(0f, 0f, shieldRadius, shieldBrush)

        canvas.restore()
    } // draw

    override fun shot(): Target.Impact {
        shieldRadius -= 7f

        if (shieldRadius < 42f) {
            game.scored(500)
            explode()
        } else {
            game.sound(R.raw.minelayershieldhit, position)
        }
        return Target.Impact.HARD
    } // shot

    override fun explode() {
        destroyed()
        game.sound(R.raw.minelayerexplosion, position)
        Puff(wave, position)
    } // explode

    private fun destroyed() {
        wave.removeTarget(this)
        onDestroyed()
    } // destroyed

    override fun shipCollision(ship: Ship) = ship.hit()

    companion object {
        val shape = floatArrayOf(
            45f, 0f, -35f, 30f,
            45f, 0f, -35f, -30f,
            -35f, 30f, -35f, -30f,
            -35f, 10f, -50f, 25f,
            -35f, -10f, -50f, -25f
        )
    }
} // class Minelayer