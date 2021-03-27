package com.co.sanson.space_shooter.craft

import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Path
import com.co.sanson.space_shooter.Game
import com.co.sanson.space_shooter.R
import com.co.sanson.space_shooter.geometry.Point
import com.co.sanson.space_shooter.geometry.Rotation
import com.co.sanson.space_shooter.geometry.Vector
import com.co.sanson.space_shooter.geometry.angleFromOffsets
import com.co.sanson.space_shooter.wave.Wave
import kotlin.random.Random

class Comet(
    private val game: Game,
    private val wave: Wave
): Target {
    override val position = game.extent.randomPointOnEdge()
    private val extentWithTail = game.extent.inflated(500f)
    private val velocity = CometVector(position)
    private val orientation = Rotation.random()
    private val rotation = Random.nextDouble(-5.0, 5.0)
    private val slap = { game.sound(R.raw.cometslap, position) }

    init {
        wave.addTarget(this)
    } // init

    override val killDist get() = 50f

    override fun update(frameRateScale: Float) {
        if (!position.moveNoWrap(velocity, frameRateScale, extentWithTail, killDist))
            wave.removeTarget(this)
        orientation += (rotation * frameRateScale)
    } // update

    override fun draw(canvas: Canvas) {
        canvas.save()

        position.translate(canvas)

        // canvas.drawCircle(0f, 0f, killRadius, brush)
        orientation.rotate(canvas)
        canvas.drawPath(cometPath, brush)
        orientation.unrotate(canvas)

        // comet tail
        canvas.rotate(velocity.angle.toFloat())
        for (y in -50 until 50 step 10)
            canvas.drawLine(
                -35f + Random.nextInt(0, 20),
                y.toFloat(),
                -500f + Random.nextInt(-50, 100),
                (y*Random.nextDouble(1.0, 1.75)).toFloat(),
                brush
            )

        canvas.restore()
    } // draw

    override fun shot(): Target.Impact {
        slap()
        return Target.Impact.HARD
    } // shot
    override fun explode() { }

    override fun shipCollision(ship: Ship) = ship.hit()

    companion object {
        fun CometVector(position: Point) =
            Vector(Random.nextDouble(4.0, 7.0), angleFromOffsets(position.x, position.y))

        val brush = Paint()
        private val shape = floatArrayOf(
            0f, 25f, 25f, 50f,
            25f, 50f, 50f, 25f,
            50f, 25f, 37.5f, 0f,
            37.5f, 0f, 50f, -25f,
            50f, -25f, 12.5f, -50f,
            12.5f, -50f, -25f, -50f,
            -25f, -50f, -50f, -25f,
            -50f, -25f, -50f, 25f,
            -50f, 25f, -25f, 50f,
            -25f, 50f, 0f, 25f
        )
        val cometPath = Path()

        init {
            brush.setARGB(255, 160, 160, 225)
            brush.strokeWidth = 3f
            brush.strokeCap = Paint.Cap.ROUND
            brush.strokeJoin = Paint.Join.ROUND
            brush.style = Paint.Style.FILL_AND_STROKE

            cometPath.moveTo(shape[0], shape[1])
            for (i in shape.indices step 2)
                cometPath.lineTo(shape[i], shape[i+1])
            cometPath.close()
        } // init
    } // companion object
} // Comet