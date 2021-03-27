package com.co.sanson.space_shooter.wave

import com.co.sanson.space_shooter.Game
import com.co.sanson.space_shooter.craft.*
import com.co.sanson.space_shooter.craft.asteroid.MagneticAsteroid
import com.co.sanson.space_shooter.craft.asteroid.StonyAsteroid

class MagneticAsteroids (
    game: Game,
    starField: StarField,
    stonyAsteroids: Int = 5,
    magneticAsteroids: Int = 2,
    g: Gun? = null
) : WaveWithShip(game, starField, g) {
    init {
        MagneticAsteroid.field(game, this, magneticAsteroids)
        StonyAsteroid.field(game, this, stonyAsteroids)

        targets.onEliminated { endOfLevel() }
    } // init
} // IronAsteroids