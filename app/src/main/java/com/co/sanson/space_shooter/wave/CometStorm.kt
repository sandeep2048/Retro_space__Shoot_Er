package com.co.sanson.space_shooter.wave

import com.co.sanson.space_shooter.Game
import com.co.sanson.space_shooter.craft.*
import com.co.sanson.space_shooter.utils.Latch

class CometStorm(
    game: Game,
    starField: StarField
) : WaveWithShip(game, starField, null, false) {
    private var comets = 0
    private var survivalBonus = true

    private var cometGun: Latch = Latch(150, { launchComet() })

    init {
        ship.onLifeLost { survivalBonus = false }
    } // init

    private fun launchComet() {
        Comet(game, this)
        ++comets
        if (comets != 15)
            cometGun = Latch(70, { launchComet() })
        else
            targets.onEliminated { endOfLevel() }
    } // launchComet

    /////
    override fun update(frameRateScale: Float) {
        cometGun.tick(frameRateScale)

        super.update(frameRateScale)
    } // update

    /////
    override fun endOfLevel() {
        if (survivalBonus) game.scored(5000)

        super.endOfLevel()
    } // endOfLevel
} // FlyAround