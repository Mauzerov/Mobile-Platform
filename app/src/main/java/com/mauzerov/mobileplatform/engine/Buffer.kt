package com.mauzerov.mobileplatform.engine

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet
import android.view.SurfaceHolder
import android.view.SurfaceView
import androidx.core.content.ContextCompat
import com.mauzerov.mobileplatform.R
import com.mauzerov.mobileplatform.engine.drawing.*
import com.mauzerov.mobileplatform.engine.drawing.AlphaColor
import com.mauzerov.mobileplatform.engine.drawing.Bitmaps
import com.mauzerov.mobileplatform.engine.drawing.DisplayRect
import com.mauzerov.mobileplatform.engine.drawing.DrawFunctions as draw

class Buffer(context: Context) : SurfaceView(context) {

    private var thread : BufferThread? = null;
    private var x: Int = 10

    init {
        initialize(context)
    }

    constructor(context: Context, attr: AttributeSet) : this(context)

    constructor(context: Context, attr: AttributeSet, unused : Int) : this(context)

    private fun initialize(context: Context) {
        if (thread == null)
            thread = BufferThread(this);

        holder.addCallback(object : SurfaceHolder.Callback {
            override fun surfaceChanged(
                holder: SurfaceHolder,
                format: Int,
                width: Int,
                height: Int
            ) {

            }

            override fun surfaceCreated(holder: SurfaceHolder) {
                thread!!.isRunning = true;
                thread!!.start()
            }

            override fun surfaceDestroyed(holder: SurfaceHolder) {
                var retry = true;
                thread!!.isRunning = false

                while (retry) {
                    try {
                        thread!!.join()
                        retry = false;
                    } catch (ignored : InterruptedException) {}
                }
            }

        })

    }

    @SuppressLint("DrawAllocation")
    public override fun onDraw(canvas: Canvas?) {
        try { canvas!! } catch (e : NullPointerException) { return }
        x ++;

        draw.fillCanvas(canvas, ContextCompat.getColor( context, R.color.purple_700 ))
        draw.RectBorderless(canvas, DisplayRect(x, 10, 100, 10), AlphaColor(0xFFF000))
        draw.Text(canvas, x.toString(), 100, 100, AlphaColor(0xFFFFFF), 50F)

        draw.Bitmap(canvas,
            Bitmaps.Crop(
                Bitmaps.FromResource(resources, R.drawable.test_img_draw_canvas),
                32, 32, 32, 32
            ),
            200, 200);
    }

}

