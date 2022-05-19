package com.mauzerov.mobileplatform.items

import android.graphics.Canvas

interface ItemDrawable {
    fun drawMySelf(canvas: Canvas)
    var isShowed: Boolean
}