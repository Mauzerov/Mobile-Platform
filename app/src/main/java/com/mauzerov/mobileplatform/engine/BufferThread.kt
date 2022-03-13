package com.mauzerov.mobileplatform.engine

import android.annotation.SuppressLint
import android.graphics.Canvas
import com.mauzerov.mobileplatform.engine.Buffer

class BufferThread(private val view: Buffer) : Thread() {
    var isRunning = false
    @SuppressLint("WrongCall")
    override fun run() {
        while (isRunning) {
            var canvas: Canvas? = null
            try {
                canvas = view.holder.lockCanvas()
                synchronized(view.holder) { view.onDraw(canvas) }
            } finally {
                if (canvas != null) view.holder.unlockCanvasAndPost(canvas)
            }
        }
    }
}