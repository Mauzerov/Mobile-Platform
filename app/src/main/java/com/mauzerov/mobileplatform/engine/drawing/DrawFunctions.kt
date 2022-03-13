package com.mauzerov.mobileplatform.engine.drawing

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.PorterDuff
import android.util.Log

object DrawFunctions {
    fun fillCanvas(canvas: Canvas, color: AlphaColor) {
        fillCanvas(canvas, color.color)
    }

    fun fillCanvas(canvas: Canvas, color: Int) {
        canvas.drawColor(color, PorterDuff.Mode.CLEAR)
        Log.d("Filled", "Canvas ${canvas.width} ${canvas.height}")
    }

    /** Rectangle Borderless  */
    fun RectBorderless(canvas: Canvas, rect: DisplayRect) {
        RectBorderless(canvas, rect, AlphaColor())
    }

    fun RectBorderless(canvas: Canvas, rect: DisplayRect, color: AlphaColor) {
        val paint: Paint = color.paint
        canvas.drawRect(rect.asRect(), paint)
    }

    /** Rectangle Borderless End
     *
     * Rectangle With Border    */
    fun Rect(canvas: Canvas, rect: DisplayRect?) {
        Rect(canvas, rect, AlphaColor())
    }

    fun Rect(canvas: Canvas, rect: DisplayRect?, color: AlphaColor?) {
        Rect(canvas, rect, color, AlphaColor())
    }

    fun Rect(canvas: Canvas, rect: DisplayRect?, color: AlphaColor?, border: AlphaColor?) {}

    /** Rectangle With Border End
     *
     * Line  */
    fun Line(canvas: Canvas, rect: DisplayRect) {
        Line(canvas, rect, AlphaColor())
    }

    fun Line(canvas: Canvas, rect: DisplayRect, color: AlphaColor) {
        canvas.drawLines(rect.asFloatList(), color.paint)
    }

    /** Line End
     *
     * Text  */
    @JvmOverloads
    fun Text(canvas: Canvas, string: String?, x: Int, y: Int, color: AlphaColor = AlphaColor()) {
        canvas.drawText(string!!, x.toFloat(), y.toFloat(), color.paint)
    }

    fun Text(canvas: Canvas, string: String?, x: Int, y: Int, color: AlphaColor, fontSize: Float) {
        val p = Paint()
        p.set(color.paint)
        p.textSize = fontSize
        canvas.drawText(string!!, x.toFloat(), y.toFloat(), p)
    }

    /** Text End
     *
     * Bitmap  */
    fun Bitmap(canvas: Canvas, bitmap: Bitmap, x: Int, y: Int) {
        canvas.drawBitmap(bitmap, x.toFloat(), y.toFloat(), Paint())
    }

    fun Bitmap(canvas: Canvas, bitmap: Bitmap, x: Int, y: Int, paint: Paint) {
        canvas.drawBitmap(bitmap, x.toFloat(), y.toFloat(), paint)
    }
}