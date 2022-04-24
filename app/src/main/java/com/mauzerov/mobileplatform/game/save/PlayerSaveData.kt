package com.mauzerov.mobileplatform.game.save

import com.mauzerov.mobileplatform.game.entity.human.Player

class PlayerSaveData() : EntitySaveData() {
    constructor(player: Player) : this() {
        heath = player.health
        position = player.position
    }
    var heath: Int = 0
}