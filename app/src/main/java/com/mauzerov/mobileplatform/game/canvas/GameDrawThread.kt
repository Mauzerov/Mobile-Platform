package com.mauzerov.mobileplatform.game.canvas

import android.annotation.SuppressLint
import android.graphics.Canvas
import com.mauzerov.mobileplatform.MainActivity
import com.mauzerov.mobileplatform.engine.Buffer

class GameDrawThread(private val view: GameMap) : Thread() {
    var isRunning = false

    companion object {
        const val RefreshInterval = 9L
    }

    @SuppressLint("WrongCall")
    override fun run() {
        while (isRunning) {
            var canvas: Canvas? = null
            try {
                val startTime = System.currentTimeMillis()
                canvas = view.holder.lockCanvas()
                view.player.move()
                synchronized(view.holder) { view.onDraw(canvas) }
                val duration = System.currentTimeMillis() - startTime

                sleep(0L.coerceAtLeast(RefreshInterval - duration))
            } finally {
                if (canvas != null) view.holder.unlockCanvasAndPost(canvas)
            }
        }
    }
}