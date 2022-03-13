package com.mauzerov.mobileplatform.game.canvas.values

import java.io.Serializable

data class Height(var actual: Short, var ground: Short) : Serializable {
    fun isSame() = actual == ground
    fun isTunnel() = actual - ground  > tunnelHeight

    override fun toString(): String {
        return "H($actual, $ground)"
    }

    companion object {
        const val tunnelHeight = 3
    }
}