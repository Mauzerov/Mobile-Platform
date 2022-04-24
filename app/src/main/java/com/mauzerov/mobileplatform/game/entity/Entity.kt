package com.mauzerov.mobileplatform.game.entity

import com.mauzerov.mobileplatform.game.canvas.GameConstants
import com.mauzerov.mobileplatform.sizes.Position
import com.mauzerov.mobileplatform.sizes.Size
import java.io.Externalizable
import java.io.ObjectInput
import java.io.ObjectOutput
import java.io.Serializable

abstract class Entity: Externalizable {
    val position: Position = Position(0,0)
    val size: Size = Size(0, 0);

    open fun move() {
        position.move()
    }
    fun setVelocity(dx: Int?, dy: Int?) = position.setVelocity(dx, dy)

    override fun readExternal(`in`: ObjectInput?) {
        `in`?.let {
            val newPosition = it.readObject() as Position
            position.set(newPosition.x, newPosition.y)
        }
    }
    override fun writeExternal(out: ObjectOutput?) {
        out?.writeObject(position)
    }
}