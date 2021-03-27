package com.co.sanson.space_shooter.wave.transition

import android.graphics.Canvas
import com.co.sanson.space_shooter.Game
import com.co.sanson.space_shooter.craft.Projectiles
import com.co.sanson.space_shooter.craft.Ship
import com.co.sanson.space_shooter.utils.Latch
import com.co.sanson.space_shooter.wave.StarField
import com.co.sanson.space_shooter.wave.Wave

class LevelTransition(
    private val game: Game,
    private val starField: StarField,
    private val newStarField: StarField,
    override val ship: Ship,
    private val projectiles: Projectiles?,
    private val nextWave: Wave,
    private val waveIndex: Int
): Wave {
    private var transition = Latch(180, { startNextWave() })
    private var currentStarField = starField

    override fun update(frameRateScale: Float) {
        ship.update(frameRateScale)
        projectiles?.update(frameRateScale)

        when (transition.tick(frameRateScale)) {
            120 -> ship.rezOut()
            40, 60, 100 -> currentStarField = newStarField
            50, 75 -> currentStarField = starField
        }
    } // update

    override fun draw(canvas: Canvas) {
        currentStarField.draw(canvas)
        if (currentStarField == starField)
          projectiles?.draw(canvas)

        ship.draw(canvas)
    } // draw

    private fun startNextWave() {
        game.lifeGained()
        game.checkpointScore(waveIndex)
        game.nextWave(nextWave)
    } // startNextWave
} // Emptiness