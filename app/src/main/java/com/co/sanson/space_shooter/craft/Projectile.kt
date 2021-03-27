package com.co.sanson.space_shooter.craft

interface Projectile: Craft {
    fun hit(impact: Target.Impact)
} // Projectile