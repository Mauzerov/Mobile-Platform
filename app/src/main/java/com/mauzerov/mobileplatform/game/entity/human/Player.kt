package com.mauzerov.mobileplatform.game.entity.human

import com.mauzerov.mobileplatform.game.entity.LivingEntity
import com.mauzerov.mobileplatform.game.save.PlayerSaveData

class Player(x: Int, y: Int, w: Int, h: Int) : LivingEntity() {
    private var money = 0L

    val moneyString: String
        get() = money.toString()

    init {
        position.set(x, y)
        size.set(w, h)
    }

    fun setFromSaveData(saveData: PlayerSaveData) {
        position.set(saveData.position.x, saveData.position.y)
        health = saveData.heath
    }

    companion object {
        const val MAX_HEALTH = 20
    }
}