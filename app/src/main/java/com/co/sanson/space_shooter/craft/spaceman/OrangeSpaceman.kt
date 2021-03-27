package com.co.sanson.space_shooter.craft.spaceman

import com.co.sanson.space_shooter.Game
import com.co.sanson.space_shooter.geometry.Point
import com.co.sanson.space_shooter.wave.Wave
import com.co.sanson.space_shooter.R

class OrangeSpaceman(
    private val game: Game,
    private val wave: Wave,
    pos: Point
) : Spaceman(game, wave, pos, R.drawable.orangespaceman) {
} // OrangeSpaceman