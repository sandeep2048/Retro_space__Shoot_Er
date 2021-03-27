package com.co.sanson.space_shooter.wave

import android.graphics.Canvas
import android.view.MotionEvent
import com.co.sanson.space_shooter.craft.Projectile
import com.co.sanson.space_shooter.craft.Ship
import com.co.sanson.space_shooter.craft.Target

interface Wave {
    val ship: Ship?

    fun onSingleTapUp(event: MotionEvent) = Unit
    fun onScroll(offsetX: Float, offsetY: Float) = Unit
    fun onLongPress() = Unit

    /////
    fun update(frameRateScale: Float)
    fun draw(canvas: Canvas)

    /////
    fun addTarget(target: Target) = Unit
    fun removeTarget(target: Target) = Unit

    /////
    fun addProjectile(projectile: Projectile) = Unit
    fun removeProjectile(projectile: Projectile) = Unit

    ////
    fun upgrade() = Unit
} // Wave
