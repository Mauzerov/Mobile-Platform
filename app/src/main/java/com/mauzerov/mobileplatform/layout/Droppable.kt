package com.mauzerov.mobileplatform.layout

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import androidx.constraintlayout.widget.ConstraintLayout

@SuppressLint("ViewConstructor")
open class Droppable : ConstraintLayout {
    constructor(context: Context, closeEvent: () -> Unit) : super(context)
    constructor(context: Context, attrs: AttributeSet, closeEvent: () -> Unit) : super(context, attrs)
}