package com.mauzerov.mobileplatform.items.consumables

import android.content.res.Resources
import android.util.Log
import android.view.MotionEvent
import com.mauzerov.mobileplatform.MainActivity
import com.mauzerov.mobileplatform.R
import com.mauzerov.mobileplatform.game.canvas.GameMap
import com.mauzerov.mobileplatform.game.entity.human.Player
import com.mauzerov.mobileplatform.items.ItemBase
import com.mauzerov.mobileplatform.layout.DropdownEq

abstract class Food(resources: Resources): ItemBase(resources) {
    protected abstract val healthPoints: Int
    override fun specialActivity(event: MotionEvent?, gameMap: GameMap) {
        gameMap.player.heal(healthPoints)
        gameMap.player.items.all.remove(this)
        removeFromEq(gameMap)
        Log.d("Nothing", "FOOD " + gameMap.player.health.toString() + " " + gameMap.player.MAX_HEALTH)
    }

}