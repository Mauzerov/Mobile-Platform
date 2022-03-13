package com.mauzerov.mobileplatform.game

import android.content.Context
import android.graphics.Color
import android.graphics.PixelFormat
import android.graphics.PorterDuff
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.SurfaceHolder
import android.view.SurfaceView
import android.view.View
import kotlin.math.pow
import kotlin.math.sqrt

data class Dimensions(var x : Float, var y : Float) {
    constructor(value : Float) : this(value, value)

    operator fun plus(other : Dimensions) : Dimensions {
        return Dimensions(this.x + other.x, this.y + other.y)
    }
    operator fun minus(other : Dimensions) : Dimensions {
        return Dimensions(this.x - other.x, this.y - other.y)
    }
    operator fun times(other : Dimensions) : Dimensions {
        return Dimensions(this.x * other.x, this.y * other.y)
    }
    operator fun div(other : Dimensions) : Dimensions {
        return Dimensions(this.x / other.x, this.y / other.y)
    }
    fun pow(power : Int) : Dimensions =
        Dimensions(this.x.pow(power), this.y.pow(power))

    fun sum() : Float =
        this.x + this.y;

    fun angle() : Float =
        Math.toDegrees(angleRad().toDouble()).toFloat()

    private fun angleRad() : Float =
        kotlin.math.atan2(this.x, this.y)

    fun facingRight() : Boolean =
        angle() > 0

    fun facingLeft() : Boolean =
        angle() < 0

//    override fun toString(): String {
//        return "(${this.x}, ${this.y})"
//    }
}

class JoyStick :
    SurfaceView,
    SurfaceHolder.Callback,
    View.OnTouchListener
{
    constructor(context: Context) : super(context) {
        configure(context, null, 0)
    }

    constructor(context: Context, attributeSet: AttributeSet?, style: Int) : super(context, attributeSet, style) {
        configure(context, attributeSet, style)
    }

    constructor(context: Context, attributeSet: AttributeSet?) : super(context, attributeSet) {
      configure(context, attributeSet, 0)
    }

    private fun configure(context: Context, attributeSet: AttributeSet?, style: Int) {
        setZOrderOnTop(true);
        holder.addCallback(this)
        holder.setFormat(PixelFormat.TRANSPARENT);
        setOnTouchListener(this)
        if (context is JoystickListener)
            joystickCallback = context as JoystickListener
    }


    lateinit var joystickCallback: JoystickListener
    private var centerDimensions: Dimensions? = null
    private var baseRadius: Float? = null
    private var hatRadius: Float? = null
    private val ratio = 5

    private fun checkValuesNotNull() : Boolean {
        /** Returns {True} if some value is {null} **/
        return centerDimensions == null || baseRadius == null || hatRadius == null;
    }

    private fun setUpDimensions() {
        centerDimensions = Dimensions(width / 2F, height / 2F)
        baseRadius = width.coerceAtMost(height) / 3F
        hatRadius = width.coerceAtMost(height) / 6F
    }

    private fun drawJoyStick(dimensions: Dimensions?) {
        try { dimensions!! } catch (ignored: Exception) { return }

        if (!holder.surface.isValid || checkValuesNotNull())
            return

        val canvas = holder.lockCanvas()
        val paint = android.graphics.Paint()

        canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR)
        paint.setARGB(0xFF, 0x33, 0x33, 0x33)
        canvas.drawCircle(centerDimensions!!.x, centerDimensions!!.y, baseRadius!!, paint)
        paint.setARGB(0xFF, 0xdd, 0xdd, 0xdd)
        canvas.drawCircle(dimensions.x, dimensions.y, hatRadius!!, paint)

        holder.unlockCanvasAndPost(canvas)
    }

    override fun surfaceCreated(holder: SurfaceHolder) {
        setUpDimensions()
        drawJoyStick(centerDimensions)
    }

    override fun surfaceChanged(holder: SurfaceHolder, format: Int, width: Int, height: Int) { }

    override fun surfaceDestroyed(holder: SurfaceHolder) { }

    override fun onTouch(view: View, e : MotionEvent) : Boolean {
        if (view != this || checkValuesNotNull())
            return true
        val eventDimensions = Dimensions(e.x, e.y)
        if (e.action != MotionEvent.ACTION_UP) {
            val offset = sqrt((eventDimensions - centerDimensions!!).pow(2).sum())

            if (offset < baseRadius!!) {
                drawJoyStick(eventDimensions)
                joystickCallback.onJoystickMoved(
                    eventDimensions.minus(centerDimensions!!)
                        .div(Dimensions(baseRadius!!)), id)
            } else {
                val ratio = baseRadius!! / offset
                val correctedDimensions = centerDimensions!! +
                       (eventDimensions - centerDimensions!!) * Dimensions(ratio)

                drawJoyStick(correctedDimensions)
                joystickCallback.onJoystickMoved(
                    correctedDimensions.minus(centerDimensions!!)
                        .div(Dimensions(baseRadius!!)), id)
            }
        } else {
            drawJoyStick(centerDimensions)
            joystickCallback.onJoystickMoved(Dimensions(0F, 0F), id)
        }
        return true
    }

    interface JoystickListener {
        fun onJoystickMoved(percent : Dimensions, id: Int);
    }
}