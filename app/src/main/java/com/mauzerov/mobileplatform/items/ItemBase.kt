package com.mauzerov.mobileplatform.items

import android.annotation.SuppressLint
import android.content.res.Resources
import android.graphics.Bitmap
import android.view.MotionEvent
import android.view.View
import androidx.core.graphics.drawable.toBitmap
import com.mauzerov.mobileplatform.MainActivity
import com.mauzerov.mobileplatform.game.canvas.GameMap
import com.mauzerov.mobileplatform.layout.DropdownEq

abstract class ItemBase(private var resources: Resources) {
    abstract val resourceIconId: Int
    abstract fun specialActivity(event: MotionEvent?, gameMap: GameMap)

    @SuppressLint("UseCompatLoadingForDrawables")
    fun getIcon() : Bitmap {
        return resources.getDrawable(resourceIconId, Resources.getSystem().newTheme()).toBitmap()
    }

    fun removeFromEq(gameMap: GameMap) {
        ((gameMap.context as MainActivity).eqMenu as DropdownEq).refill(gameMap)
    }
}