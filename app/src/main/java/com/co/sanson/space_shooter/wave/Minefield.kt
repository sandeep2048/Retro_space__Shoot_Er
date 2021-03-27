package com.co.sanson.space_shooter.wave

import com.co.sanson.space_shooter.Game
import com.co.sanson.space_shooter.craft.Gun
import com.co.sanson.space_shooter.craft.Minelayer
import com.co.sanson.space_shooter.craft.asteroid.StonyAsteroid
import com.co.sanson.space_shooter.utils.RestartableLatch

class Minefield(
    game: Game,
    starField: StarField,
    minelayerDelay: Int,
    private val minelayerCount: Int,
    g: Gun?
): WaveWithShip(game, starField, g) {
    private val launcher = RestartableLatch(minelayerDelay, ::launchMinelayer)
    private var layerCount = 0

    init {
        StonyAsteroid.field(game, this, 6)

        launcher.start()
        targets.onEliminated { endOfLevel() }
    } // init

    private fun launchMinelayer() {
        ++layerCount
        Minelayer(game, this, { if (layerCount < minelayerCount) launcher.start() })
    } // launchMinelayer

    /////
    override fun update(frameRateScale: Float) {
        launcher.tick(frameRateScale)

        super.update(frameRateScale)
    } // update
} // Minefield