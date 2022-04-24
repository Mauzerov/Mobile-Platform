package com.mauzerov.mobileplatform.game.entity.human

import com.mauzerov.mobileplatform.game.entity.LivingEntity
import com.mauzerov.mobileplatform.game.save.PlayerSaveData
import com.mauzerov.mobileplatform.items.Equipment
import com.mauzerov.mobileplatform.items.ItemBase
import com.mauzerov.mobileplatform.items.utils.Phone

// TODO: Make Eq Savable 
class Player(x: Int, y: Int, w: Int, h: Int) : LivingEntity() {
    override val leapTimeMillis: Long
        get() = 300
    override val leapStrength: Float
        get() = 2F
    override val MAX_HEALTH: Int
        get() = 20
    private var money = 0L
    var items: Equipment = Equipment()
    var selectedItem: ItemBase? = null

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
}