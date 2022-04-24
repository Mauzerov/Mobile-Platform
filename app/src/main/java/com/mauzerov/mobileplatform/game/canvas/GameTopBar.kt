package com.mauzerov.mobileplatform.game.canvas

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.util.Log
import android.view.MotionEvent
import android.view.SurfaceHolder
import android.view.SurfaceView
import com.mauzerov.mobileplatform.MainActivity
import com.mauzerov.mobileplatform.between
import com.mauzerov.mobileplatform.engine.drawing.AlphaColor
import com.mauzerov.mobileplatform.engine.drawing.DisplayRect
import com.mauzerov.mobileplatform.game.entity.human.Player
import com.mauzerov.mobileplatform.layout.DropdownEq
import com.mauzerov.mobileplatform.engine.drawing.DrawFunctions as drawing


class GameTopBar(context: Context, var game: GameMap) : SurfaceView(context), SurfaceHolder.Callback {
    private val main: MainActivity = (context as MainActivity)
    private var thread: GameTopBarDrawThread = GameTopBarDrawThread(this)

    init {
        Log.d("HEIGHT", "Created")
        holder.addCallback(this)
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(e: MotionEvent?): Boolean {
        e?.let {
//            Log.d("HEIGHT", height.toString() + " " + e.x.toString())
//            Log.d("HEIGHT", e.action.toString())
            when (e.action) {
                MotionEvent.ACTION_DOWN -> {
                    if (e.x.between(0f, height.toFloat())) {
                        main.toggleSettingsMenu()
                        return true
                    }

                    if (e.x.between((width - height - height).toFloat(), width.toFloat())) {
                        main.toggleEqMenu()
                        (main.eqMenu as DropdownEq).refill(game)
                        return true
                    }
                }
            }

        }
        return super.onTouchEvent(e)
    }

    override fun surfaceCreated(holder: SurfaceHolder) {
        Log.d("HEIGHT", "surfaceCreated")
    }

    override fun surfaceChanged(holder: SurfaceHolder, format: Int, width: Int, height: Int) {
        Log.d("HEIGHT", "surfaceChanged")
        if (thread.state == Thread.State.TERMINATED) {
            thread = GameTopBarDrawThread(this)
        }
        thread.isRunning = true
        thread.start()
    }

    override fun surfaceDestroyed(holder: SurfaceHolder) {
        var retry = true
        thread.isRunning = false
        while (retry) {
            try {
                thread.join()
                retry = false
            } catch (ignored : InterruptedException) {}
        }
    }
    public override fun onDraw(canvas: Canvas?) {
        canvas?.let {
            canvas.drawColor((0xFF000000).toInt())
            // Settings Button
            drawing.RectBorderless(canvas, DisplayRect(0, 0, height, height), AlphaColor(0xFF0000))

            // Eq Button
            drawing.RectBorderless(canvas, DisplayRect(width - height - height, 0, height shl 1, height), AlphaColor(0x00FF00))

            var offset = (height shr 1) + height
            // Player Hearts
            val heartsPercent = game.player.health.toFloat() / game.player.MAX_HEALTH
            // Display Non-Colored

            var possibleHearts = HEART_DISPLAY_AMOUNT
            var possibleIndex = -1
            while (let { possibleHearts--; possibleIndex++; possibleHearts } >= 0) {
                drawing.RectBorderless(canvas, DisplayRect(offset + (height * possibleIndex),
                    0, height shr 1, height), HEART_LEFT_NON_COLOR)
                drawing.RectBorderless(canvas, DisplayRect(offset + (height * possibleIndex) + (height shr 1),
                    0, height shr 1, height), HEART_RIGHT_NON_COLOR)
            }

            // Display Colored
            var hearts = heartsPercent * HEART_DISPLAY_AMOUNT

            var index = -1
            //Log.d("HEIGHT1", hearts.toString())
            while (let { hearts--; index++; hearts } >= 0) {
                //Log.d("HEIGHT", hearts.toString())
                drawing.RectBorderless(canvas, DisplayRect(offset + (height * index),
                    0, height shr 1, height), HEART_LEFT_COLOR)
                drawing.RectBorderless(canvas, DisplayRect(offset + (height * index) + (height shr 1),
                    0, height shr 1, height), HEART_RIGHT_COLOR)
            }
            if (hearts >= -.5) {
                drawing.RectBorderless(canvas, DisplayRect(offset + (height * index),
                    0, height shr 1, height), HEART_LEFT_COLOR)
            }

            //Log.d("HEIGHT2", hearts.toString())

            offset += HEART_DISPLAY_AMOUNT * height

            drawing.Text(canvas, "${game.player.moneyString} €", offset + (height shr 1), height - 5, MONEY_COLOR, height.toFloat())

            //Log.d("HEIGHT", "Drawn $height $width")
        }
    }

    companion object {
        const val HEART_DISPLAY_AMOUNT = 5
        val HEART_LEFT_COLOR = AlphaColor(0xc0392b)
        val HEART_RIGHT_COLOR = AlphaColor(0xe74c3c)
        val HEART_LEFT_NON_COLOR = AlphaColor(0x3d3d3d)
        val HEART_RIGHT_NON_COLOR = AlphaColor(0x4b4b4b)
        val MONEY_COLOR = AlphaColor(0x6ab04c)
    }
}