package com.co.sanson.space_shooter.wave

import com.co.sanson.space_shooter.Game
import com.co.sanson.space_shooter.craft.*
import com.co.sanson.space_shooter.craft.asteroid.StonyAsteroid
import com.co.sanson.space_shooter.utils.RepeatN

class TumblersAttack(
    game: Game,
    starField: StarField,
    initialAsteroids: Int,
    g: Gun?
) : WaveWithShip(game, starField, g) {
    private val tumblerLauncher = RepeatN(
        700 - (initialAsteroids * 50),
        initialAsteroids - 1
    ) { Tumbler(game, this) }

    init {
        StonyAsteroid.field(game, this, initialAsteroids)

        targets.onEliminated { endOfLevel() }
    } // init

    override fun update(frameRateScale: Float) {
        super.update(frameRateScale)

        tumblerLauncher.tick(frameRateScale)
    } // update
} // class SaucerAttack