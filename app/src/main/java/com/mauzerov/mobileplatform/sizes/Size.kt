package com.mauzerov.mobileplatform.sizes

import android.app.Activity
import android.content.Context
import android.os.Build
import android.util.DisplayMetrics
import java.io.Serializable

class Size(
    var width: Int,
    var height: Int,
) : Serializable {
    fun set(w: Int, h: Int): Size {
        this.width = w; this.height = h
        return this
    }

    companion object {
        fun getScreenSize(activity: Activity) : Size {
            val screenWidth : Int
            val screenHeight : Int

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                screenWidth = activity.windowManager.currentWindowMetrics.bounds.width()
                screenHeight = activity.windowManager.currentWindowMetrics.bounds.height()
            } else {
                val displayMetrics = DisplayMetrics()
                activity.windowManager.defaultDisplay.getRealMetrics(displayMetrics)
                screenWidth = displayMetrics.widthPixels
                screenHeight = displayMetrics.heightPixels
            }

            return Size(screenWidth, screenHeight)
        }

        fun getScreenWidth(activity: Activity) : Int {
            return getScreenSize(activity).width
        }
        fun getScreenHeight(activity: Activity) : Int {
            return getScreenSize(activity).height
        }
    }
}
