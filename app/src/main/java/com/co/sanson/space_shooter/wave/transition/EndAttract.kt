package com.co.sanson.space_shooter.wave.transition

import android.graphics.Canvas
import com.co.sanson.space_shooter.Game
import com.co.sanson.space_shooter.craft.Ship
import com.co.sanson.space_shooter.craft.Targets
import com.co.sanson.space_shooter.utils.Repeat
import com.co.sanson.space_shooter.wave.StarField
import com.co.sanson.space_shooter.wave.WaveWithTargets
import com.co.sanson.space_shooter.wave.Waves
import kotlin.random.Random

class EndAttract(
    private val game: Game,
    private val starField: StarField,
    targets: Targets,
    private val fromWave: Int
): WaveWithTargets(targets) {
    override val ship: Ship? = null
    private val exploders = Repeat(120 / targets.size, { explodeOneTarget() })

    init {
        game.start(fromWave)
        targets.onEliminated {
            game.nextWave(
                Waves.from(game, fromWave, starField)
            )
        }
    }

    /////
    override fun update(frameRateScale: Float) {
        updateTargets(frameRateScale)
        exploders.tick(frameRateScale)
    } // update

    override fun draw(canvas: Canvas) {
        starField.draw(canvas)
        drawTargets(canvas)
    } // draw

    /////
    private fun explodeOneTarget() {
        val t = targets[Random.nextInt(targets.size)]
        t.explode()
        targets.remove(t)
    } // explodeOneTarget
} // EndAttract