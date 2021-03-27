package com.co.sanson.space_shooter.wave

import com.co.sanson.space_shooter.Game
import com.co.sanson.space_shooter.craft.Gun
import com.co.sanson.space_shooter.craft.Minelayer
import com.co.sanson.space_shooter.craft.asteroid.IronAsteroid
import com.co.sanson.space_shooter.craft.asteroid.StonyAsteroid
import com.co.sanson.space_shooter.geometry.Point
import com.co.sanson.space_shooter.utils.Latch

class TholianWeb(
    game: Game,
    starField: StarField,
    g: Gun?
): WaveWithShip(game, starField, g) {
    private val nothing = { }

    private val latches = arrayListOf(
        Latch(50, ::verticalTraverse),
        Latch(250, ::horizontalTraverse),
        Latch(500, ::cornerTraverse),
        Latch(750, ::hereComeTheAsteroids),
        Latch(2000, ::letsGoIron)
    )

    private fun verticalTraverse() {
        val offsetX = game.extent.width / 6.0;
        val traverses = arrayOf(
            arrayOf(Point(offsetX, game.extent.top), Point(offsetX, game.extent.bottom)),
            arrayOf(Point(-offsetX, game.extent.bottom), Point(-offsetX, game.extent.top))
        )
        traverses.forEach { t -> Minelayer(game, this, nothing, t, true) }
    } // verticalTraverse

    private fun horizontalTraverse() {
        val offsetY = game.extent.height / 6.0;
        val traverses = arrayOf(
            arrayOf(Point(game.extent.left, offsetY), Point(game.extent.right, offsetY)),
            arrayOf(Point(game.extent.right, -offsetY), Point(game.extent.top, -offsetY))
        )
        traverses.forEach { t -> Minelayer(game, this, nothing, t, true) }
    } // horizontalTraverse

    private fun cornerTraverse() {
        val offsetX = game.extent.width / 6.0;
        val offsetY = game.extent.height / 6.0;
        val traverses = arrayOf(
            arrayOf(Point(game.extent.left, -offsetY), Point(-offsetX, game.extent.top)),
            arrayOf(Point(game.extent.left, offsetY), Point(-offsetX, game.extent.bottom)),
            arrayOf(Point(game.extent.right, -offsetY), Point(offsetX, game.extent.top)),
            arrayOf(Point(game.extent.right, offsetY), Point(offsetX, game.extent.bottom))
        )
        traverses.forEach { t -> Minelayer(game, this, nothing, t, true) }
    } // cornerTraverse

    private fun hereComeTheAsteroids() {
        StonyAsteroid(game, this, Point(0.0, game.extent.top))
        StonyAsteroid(game, this, Point(0.0, game.extent.bottom))
        StonyAsteroid(game, this, Point(game.extent.left, 0.0))
        StonyAsteroid(game, this, Point(game.extent.right, 0.0))

        targets.onEliminated { endOfLevel() }
    } // hereComeTheAsteroids

    private fun letsGoIron() {
        IronAsteroid(game, this, Point(game.extent.left, game.extent.top))
        IronAsteroid(game, this, Point(game.extent.left, game.extent.bottom))
        IronAsteroid(game, this, Point(game.extent.right, game.extent.top))
        IronAsteroid(game, this, Point(game.extent.right, game.extent.bottom))
    } // letsGoIron

    /////
    override fun update(frameRateScale: Float) {
        latches.forEach { it.tick(frameRateScale) }

        super.update(frameRateScale)
    } // update
} // Emptiness