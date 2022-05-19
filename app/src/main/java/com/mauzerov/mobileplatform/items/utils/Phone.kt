package com.mauzerov.mobileplatform.items.utils

import android.content.res.Resources
import android.graphics.Canvas
import android.graphics.Paint
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.widget.LinearLayout
import com.mauzerov.mobileplatform.MainActivity
import com.mauzerov.mobileplatform.R
import com.mauzerov.mobileplatform.game.canvas.GameMap
import com.mauzerov.mobileplatform.items.ItemBase
import com.mauzerov.mobileplatform.items.ItemDrawable
import com.mauzerov.mobileplatform.layout.DropdownEq

class Phone(resources: Resources) : ItemBase(resources), ItemDrawable {
    override var isShowed: Boolean = false
    var isUp = false
        private set
    override val resourceIconId: Int
        get() = R.drawable.test_img_draw_canvas

    override fun specialActivity(event: MotionEvent?, gameMap: GameMap) {
        gameMap.player.items.phone?.let {
            it.isUp = false
            //it.isShowed = false
            gameMap.player.selectedItem = null
        }?:run {}
    }

    override fun drawMySelf(canvas: Canvas) {
        getIcon().apply {
            canvas.drawBitmap(
                this,
                (canvas.width - width).toFloat(),
                (canvas.height - height).toFloat(),
                Paint()
            )
        }
    }
}