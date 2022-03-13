package com.mauzerov.mobileplatform.game.entity

import com.mauzerov.mobileplatform.sizes.Position
import java.io.ObjectInput
import java.io.ObjectOutput
import java.io.Serializable

abstract class LivingEntity : Entity(), Serializable {
    var health: Int = MAX_HEALTH

    fun hit(healthPoints: Int): Boolean {
        return run {
            health -= healthPoints
            health
        } < 0
    }

    fun heal(healthPoints: Int): Boolean {
        if (!canHeal()) return false
        health = MAX_HEALTH.coerceAtMost(health + healthPoints)
        return true
    }
    fun canHeal(): Boolean {
        return health < MAX_HEALTH
    }

    override fun readExternal(`in`: ObjectInput?) {
        super.readExternal(`in`)
        `in`?.let {
            health = it.readObject() as Int
        }
    }
    override fun writeExternal(out: ObjectOutput?) {
        super.writeExternal(out)
        out?.writeObject(health)
    }

    companion object {
        const val MAX_HEALTH: Int = 10
    }
}