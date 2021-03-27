package com.co.sanson.space_shooter.craft.spaceman

import com.co.sanson.space_shooter.Game
import com.co.sanson.space_shooter.craft.Ship
import com.co.sanson.space_shooter.geometry.Point
import com.co.sanson.space_shooter.wave.Wave
import com.co.sanson.space_shooter.R

class BlueSpaceman(
    private val game: Game,
    private val wave: Wave,
    pos: Point
) : Spaceman(game, wave, pos, R.drawable.bluespaceman) {
    private val savedSound = { game.sound(R.raw.spacemansaved, position) }

    /////
    override fun shipCollision(ship: Ship) {
        game.scored(1500)
        wave.upgrade()

        super.shipCollision(ship)
    } // shipCollision
} // OrangeSpaceman