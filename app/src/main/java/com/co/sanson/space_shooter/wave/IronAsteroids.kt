package com.co.sanson.space_shooter.wave

import com.co.sanson.space_shooter.Game
import com.co.sanson.space_shooter.craft.*
import com.co.sanson.space_shooter.craft.asteroid.IronAsteroid
import com.co.sanson.space_shooter.craft.asteroid.StonyAsteroid

class IronAsteroids (
    game: Game,
    starField: StarField,
    stonyAsteroids: Int = 5,
    ironAsteroids: Int = 1,
    g: Gun? = null
) : WaveWithShip(game, starField, g) {
    init {
        IronAsteroid.field(game, this, ironAsteroids)
        StonyAsteroid.field(game, this, stonyAsteroids)

        targets.onEliminated { endOfLevel() }
    } // init
} // IronAsteroids