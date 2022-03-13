package com.mauzerov.mobileplatform.engine.drawing

import android.graphics.Paint

class AlphaColor @JvmOverloads constructor(var color: Int = 0x000000, var alpha: Int = 0xFF) {
    val paint: Paint
        get() {
            return Paint().apply {
                this.color = this@AlphaColor.color
                this.alpha = this@AlphaColor.alpha
            };
        }
}