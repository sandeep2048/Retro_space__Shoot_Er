package com.co.sanson.space_shooter.craft.spaceman

import android.graphics.Canvas
import android.graphics.Matrix
import com.co.sanson.space_shooter.Game
import com.co.sanson.space_shooter.R
import com.co.sanson.space_shooter.craft.Ship
import com.co.sanson.space_shooter.craft.Target
import com.co.sanson.space_shooter.geometry.Point
import com.co.sanson.space_shooter.geometry.Rotation
import com.co.sanson.space_shooter.geometry.Vector
import com.co.sanson.space_shooter.wave.Wave
import kotlin.random.Random

abstract class Spaceman(
    private val game: Game,
    private val wave: Wave,
    pos: Point,
    bitmapId: Int
) : Target {
    override val position = Point(pos)
    private val velocity = SpacemanVector()
    private val orientation = Rotation.random()
    private val rotation = Random.nextDouble(-1.5, 1.5)
    private val spaceman = game.loadBitmap(bitmapId).bitmap
    private val matrix = Matrix()
    private val problemSound = { game.sound(R.raw.spaceman, position) }
    private val fallenSound = { game.sound(R.raw.spacemanfallen, position) }
    private val savedSound = { game.sound(R.raw.spacemansaved, position) }
    override val killDist = spaceman.width / 2f
    private var age = 0f
    private var falling = false

    init {
        wave.addTarget(this)

        problemSound()

        matrix.postTranslate(-spaceman.width / 2f, -spaceman.height / 2f)
        matrix.postRotate(orientation.angle.toFloat())
        matrix.postTranslate(0f, 0f)
    } // init

    /////
    override fun update(frameRateScale: Float) {
        position.move(velocity, frameRateScale, game.extent, killDist)
        matrix.postRotate(rotation.toFloat())

        age += frameRateScale
        if (age > 500)
            falling()
    } // update

    private fun falling() {
        if (!falling) fallenSound()

        matrix.postRotate(rotation.toFloat() * 3)
        matrix.postScale(0.98f, 0.98f)

        if (age > 575)
            wave.removeTarget(this)
        falling = true
    } // falling

    override fun draw(canvas: Canvas) {
        canvas.save()

        position.translate(canvas)

        // canvas.drawCircle(0f, 0f, 50f, Asteroid.brush)
        canvas.drawBitmap(spaceman, matrix, null)

        canvas.restore()
    } // draw

    /////
    override fun shipCollision(ship: Ship) {
        savedSound()
        game.scored(1000)
        wave.upgrade()
        wave.removeTarget(this)
    } // shipCollision

    override fun shot(): Target.Impact = Target.Impact.NONE
    override fun explode() = Unit

    companion object {
        fun SpacemanVector() =
            Vector(Random.nextDouble(2.0), Random.nextDouble(360.0))
    } // companion object
} // Spaceman