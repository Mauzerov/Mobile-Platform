package com.mauzerov.mobileplatform.controller

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.drawable.Drawable
import android.text.TextPaint
import android.util.AttributeSet
import android.view.View
import android.widget.ImageButton
import android.widget.Toast
import com.mauzerov.mobileplatform.R

/**
 * TODO: document your custom view class.
 */
class SpecialActivityButton : androidx.appcompat.widget.AppCompatImageButton, View.OnClickListener {

    private var onClickFunction = {}

    constructor(context: Context) : super(context) {
        init(null, 0)
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init(attrs, 0)
    }

    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(
        context,
        attrs,
        defStyle
    ) {
        init(attrs, defStyle)
    }

    private fun init(attrs: AttributeSet?, defStyle: Int) {
        this.setOnClickListener(this)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        this.bringToFront()
    }

    override fun onClick(v: View?) {
        Toast.makeText(context, "Clicked", Toast.LENGTH_SHORT).show()
        this.onClickFunction()
    }

    fun setOnClick(function : () -> Unit) {
        this.onClickFunction = function;
    }
}