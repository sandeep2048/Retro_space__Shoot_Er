package com.co.sanson.space_shooter.wave

import android.graphics.Canvas
import com.co.sanson.space_shooter.craft.Ship
import com.co.sanson.space_shooter.craft.Target
import com.co.sanson.space_shooter.craft.Targets

abstract class WaveWithTargets(
    protected val targets: Targets = Targets()
): Wave {
    protected fun updateTargets(frameRateScale: Float) {
        targets.update(frameRateScale)
    } // updateTargets

    protected fun checkShipCollision(ship: Ship) {
        targets.checkShipCollision(ship)
    } // checkShipCollision

    protected fun drawTargets(canvas: Canvas) {
        targets.draw(canvas)
    } // drawTargets

    override fun addTarget(target: Target) {
        targets.add(target)
    } // addTarget
    override fun removeTarget(target: Target) {
        targets.remove(target)
    } // removeTarget
} // WaveWithTargets