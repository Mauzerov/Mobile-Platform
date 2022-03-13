package com.mauzerov.mobileplatform.engine.drawing

import android.graphics.Rect

class DisplayRect(var x: Int, var y: Int, var w: Int, var h: Int) {
    fun asRect(): Rect {
        return Rect(x, y, x + w, y + h)
    }

    fun asFloatList(): FloatArray {
        return floatArrayOf(x.toFloat(), y.toFloat(), (x + w).toFloat(), (y + h).toFloat())
    }
}