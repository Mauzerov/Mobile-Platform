package com.mauzerov.mobileplatform.game.canvas

import android.annotation.SuppressLint
import android.graphics.Canvas
import android.util.Log

class GameTopBarDrawThread(private val view: GameTopBar) : Thread() {
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
                synchronized(view.holder) { view.onDraw(canvas) }
                val duration = System.currentTimeMillis() - startTime
                sleep(0L.coerceAtLeast(RefreshInterval - duration))
            } finally {
                if (canvas != null) view.holder.unlockCanvasAndPost(canvas)
            }
        }
    }
}