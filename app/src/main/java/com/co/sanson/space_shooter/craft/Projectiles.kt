package com.co.sanson.space_shooter.craft

import android.graphics.Canvas

class Projectiles {
    private val projectiles = ArrayList<Projectile>()

    operator fun iterator() = ArrayList(projectiles).iterator()
    val size get() = projectiles.size

    /////
    fun update(frameRateScale: Float) {
        iterator().forEach { p -> p.update(frameRateScale) }
    } // updateProjectiles

    fun draw(canvas: Canvas) {
        projectiles.forEach { p -> p.draw(canvas) }
    } // drawProjectiles

    /////
    fun add(projectile: Projectile) = projectiles.add(projectile)
    fun remove(projectile: Projectile) = projectiles.remove(projectile)

    /////
    fun collision(target: Target): Projectile? =
        projectiles.firstOrNull { p -> Craft.collision(p, target) }
} // Asteroids
