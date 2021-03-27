package com.co.sanson.space_shooter.wave

import com.co.sanson.space_shooter.Game
import com.co.sanson.space_shooter.craft.Gun
import com.co.sanson.space_shooter.craft.Saucer
import com.co.sanson.space_shooter.craft.asteroid.StonyAsteroid
import com.co.sanson.space_shooter.utils.RepeatN
import kotlin.random.Random

class SaucerAttack(
    game: Game,
    starField: StarField,
    initialAsteroids: Int,
    saucerAggressiveness: Int,
    g: Gun?
) : WaveWithShip(game, starField, g) {
    private val saucerLaunch = RepeatN(500, saucerAggressiveness + 3, launchSaucer(saucerAggressiveness))

    init {
        StonyAsteroid.field(game, this, initialAsteroids)

        targets.onEliminated { endOfLevel() }
    } // init

    override fun update(frameRateScale: Float) {
        super.update(frameRateScale)

        saucerLaunch.tick(frameRateScale)
    } // update

    private fun launchSaucer(saucerAggressiveness: Int): () -> Unit {
        return {
            Saucer(game, this, saucerAggressiveness)
            saucerLaunch.reset(Random.nextInt(300, 700))
        }
    } // launcherSaucer
} // class SaucerAttack