package com.co.sanson.space_shooter.craft

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import com.co.sanson.space_shooter.Game
import com.co.sanson.space_shooter.R
import com.co.sanson.space_shooter.geometry.Point
import com.co.sanson.space_shooter.utils.Repeat
import com.co.sanson.space_shooter.wave.Wave

class Mine(
    private val game: Game,
    private val wave: Wave,
    override val position: Point
): Target {
    private val brush = Paint()
    private val throbber = Repeat(11, ::throb)
    private var radius = 4f;

    init {
        wave.addTarget(this)
        game.sound(R.raw.minedrop, position)

        brush.color = Color.YELLOW
        brush.alpha = 180
        brush.strokeWidth = 5f
        brush.strokeCap = Paint.Cap.ROUND
        brush.style = Paint.Style.FILL_AND_STROKE
    } // init

    private fun throb() {
        radius = if (radius == 4f) 2f else 4f
    } // throb

    override val killDist get() = 20f

    override fun update(frameRateScale: Float) {
        throbber.tick(frameRateScale)
    } // update

    override fun draw(canvas: Canvas) {
        canvas.save()

        position.translate(canvas)
        val o = 12f - radius
        canvas.drawLine(-o, -o, o, o, brush)
        canvas.drawLine(-o, o, o, -o, brush)
        canvas.drawCircle(-o, -o, radius, brush)
        canvas.drawCircle(-o, o, radius, brush)
        canvas.drawCircle(o, -o, radius, brush)
        canvas.drawCircle(o, o, radius, brush)

        canvas.restore()
    } // draw

    override fun shot(): Target.Impact {
        game.scored(50)
        explode()
        return Target.Impact.HARD
    } // shot

    override fun explode() {
        game.sound(R.raw.mineexplosion, position)
        wave.removeTarget(this)
        Puff(wave, position)
    } // explode

    override fun shipCollision(ship: Ship) {
        explode()
        ship.hit()
    } // shipCollision
} // class Minelayer