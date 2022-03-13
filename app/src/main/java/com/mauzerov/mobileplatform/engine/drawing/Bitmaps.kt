package com.mauzerov.mobileplatform.engine.drawing

import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.BitmapFactory.Options
import androidx.core.graphics.scale

object Bitmaps {
    fun FromResource(resources: Resources, id: Int): Bitmap {
        val options = Options().apply {
            this.inScaled = false
        }
        return BitmapFactory.decodeResource(resources, id, options)
    }

    /** Crop Bitmap  */
    fun Crop(bitmap: Bitmap, x: Int, y: Int): Bitmap {
        return Bitmap.createBitmap(bitmap, x, y, bitmap.width - x, bitmap.height - y)
    }

    fun Crop(bitmap: Bitmap, x: Int, y: Int, w: Int, h: Int): Bitmap {
        return Bitmap.createBitmap(bitmap, x, y, w, h)
    }
}