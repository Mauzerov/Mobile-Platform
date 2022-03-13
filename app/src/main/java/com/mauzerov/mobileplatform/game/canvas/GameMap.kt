package com.mauzerov.mobileplatform.game.canvas

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.os.SystemClock
import android.util.Log
import android.view.MotionEvent
import android.view.SurfaceHolder
import android.view.SurfaceView
import android.widget.Toast
import com.mauzerov.mobileplatform.between
import com.mauzerov.mobileplatform.engine.assets.Textures
import com.mauzerov.mobileplatform.engine.drawing.AlphaColor
import com.mauzerov.mobileplatform.engine.drawing.DisplayRect
import com.mauzerov.mobileplatform.game.entity.human.Player
import com.mauzerov.mobileplatform.game.save.GameSaveData
import com.mauzerov.mobileplatform.game.save.PlayerSaveData
import com.mauzerov.mobileplatform.sizes.Position
import com.mauzerov.mobileplatform.sizes.Size
import java.io.*
import kotlin.math.roundToInt
import com.mauzerov.mobileplatform.engine.drawing.DrawFunctions as drawing
import com.mauzerov.mobileplatform.game.canvas.GameConstants.biomeMap
import com.mauzerov.mobileplatform.game.canvas.GameConstants.doubleTileHeight
import com.mauzerov.mobileplatform.game.canvas.GameConstants.doubleTileWidth
import com.mauzerov.mobileplatform.game.canvas.GameConstants.fileName
import com.mauzerov.mobileplatform.game.canvas.GameConstants.mapSize
import com.mauzerov.mobileplatform.game.canvas.GameConstants.oceanDepth
import com.mauzerov.mobileplatform.game.canvas.GameConstants.tileSize
import com.mauzerov.mobileplatform.game.canvas.GameConstants.heightMap
import com.mauzerov.mobileplatform.game.canvas.values.Biome
import com.mauzerov.mobileplatform.game.canvas.values.Height


@SuppressLint("ViewConstructor")
class GameMap(context: Context, val saveDestination: String) : SurfaceView(context) {
    private var textures: Textures = Textures(resources)

    var player: Player = Player(0, 0, 24, (0.75 * tileSize.height).toInt())

    //var heightMap: MutableList<Height> = Array(200) { Height(0, 0) }.toMutableList()
    private var thread: GameDrawThread = GameDrawThread(this)

    var heightTypeChange: Int = 0

    private fun adjustYPosition(pos: Position, size: Size) {
        val mapX = ((pos.x) / tileSize.width)
        // Log.d("POS", "${pos.x} :: $offset :: $mapX :: ${offset in (tileSize.width - size.width).. tileSize.width}")
        if (mapX in 0 until mapSize && pos.x.between(0, mapSize * tileSize.width)) {
//            mapX += (
//                offset in (tileSize.width - size.width) .. tileSize.width &&
//                heightMap.elementAtOrNull(mapX + 1)
//                    .let { it != null && it > heightMap[mapX] }
//            ).toInt()
            pos.y = heightMap[mapX].ground * tileSize.height
        }
        else
            pos.y = -oceanDepth * 4
    }

    init {
        //assert(heightMap.size == mapSize && mapSize == GameConstants.heightMap.size) { Log.d("ASSERT", "$mapSize != ${heightMap.size}"); "" }
        //readSaveFile(fileName)
        holder.addCallback(object : SurfaceHolder.Callback {
            override fun surfaceChanged(
                holder: SurfaceHolder,
                format: Int,
                width: Int,
                height: Int
            ) {
                if (thread.state == Thread.State.TERMINATED) {
                    thread = GameDrawThread(this@GameMap)
                }
                thread.isRunning = true
                thread.start()
            }

            override fun surfaceCreated(holder: SurfaceHolder) {

            }

            override fun surfaceDestroyed(holder: SurfaceHolder) {
                var retry = true
                thread.isRunning = false
                saveSaveFile()
                while (retry) {
                    try {
                        thread.join()
                        retry = false
                    } catch (ignored : InterruptedException) {}
                }
            }
        })
    }


    // Draw Allocations
    private var blackAlphaColor = AlphaColor(0x000000)
    private var lastTime = SystemClock.elapsedRealtime()
    //@SuppressWarnings("DrawAllocation")
    public override fun onDraw(g: Canvas?) {
        lastTime = SystemClock.elapsedRealtime()
        val tiles = (width / doubleTileWidth)
        val noTile = (width % tileSize.width)
        //Log.d("TILES", "$tiles, $noTile")
        var drawX = (noTile shr 1) - tileSize.width
        g?.let {

            drawing.Bitmap(g, textures.sky, 0, 0)
            for (i in player.position.x / tileSize.width - tiles - 1..
                      player.position.x / tileSize.width + tiles + 1)
            {
                if (i !in 0 until mapSize || (biomeMap[i] == Biome.Ocean)) {
                    // Generate Ocean
                    drawOcean(g, drawX)
                } else {
                    if (heightMap[i].isTunnel()) {
                        for (j in -1 .. heightMap[i].ground) {
                            // Generate Ground
                            drawing.Bitmap(g, textures.ground.dirt,
                                drawX - (player.position.x % tileSize.width),
                                height - j * tileSize.height - doubleTileHeight)
                        }
                        for (j in heightMap[i].ground + 1 .. heightMap[i].ground + Height.tunnelHeight) {
                            // Generate Further Dirt Wall
                            drawing.Bitmap(g, textures.ground.wall,
                                drawX - (player.position.x % tileSize.width),
                                height - j  * tileSize.height - doubleTileHeight)
                        }
                        for (j in 1 + heightMap[i].ground + Height.tunnelHeight .. heightMap[i].actual) {
                            // Generate Ground
                            drawing.Bitmap(g, textures.ground.dirt,
                                drawX - (player.position.x % tileSize.width),
                                height - j * tileSize.height - doubleTileHeight)
                        }
                        drawing.Bitmap(g, textures.ground.concrete,
                            drawX - (player.position.x % tileSize.width),
                            height - heightMap[i].ground * tileSize.height - doubleTileHeight)

                        // Draw Grass
                        drawing.Bitmap(g, textures.ground.grass,
                            drawX - (player.position.x % tileSize.width),
                            height - heightMap[i].actual * tileSize.height - doubleTileHeight)

                    } else if (!heightMap[i].isSame()) {
                        for (j in -1 .. heightMap[i].ground) {
                            // Generate Ground
                            drawing.Bitmap(g, textures.ground.dirt,
                                drawX - (player.position.x % tileSize.width),
                                height - j * tileSize.height - doubleTileHeight)
                        }
                        for (j in 1 + heightMap[i].ground .. heightMap[i].actual) {
                            // Generate Further Dirt Wall
                            drawing.Bitmap(g, textures.ground.dirt2,
                                drawX - (player.position.x % tileSize.width),
                                height - j  * tileSize.height - doubleTileHeight)
                        }

                        drawing.Bitmap(g,textures.ground.concrete,
                            drawX - (player.position.x % tileSize.width),
                            height - heightMap[i].ground * tileSize.height - doubleTileHeight)

                        // Draw Grass
                        drawing.Bitmap(g, textures.ground.grass3,
                            drawX - (player.position.x % tileSize.width),
                            height - heightMap[i].actual * tileSize.height - doubleTileHeight)

                    } else {
                        for (j in -1 .. heightMap[i].actual) {
                            // Generate Ground
                            drawing.Bitmap(g, textures.ground.dirt,
                                drawX - (player.position.x % tileSize.width),
                                height - j * tileSize.height - doubleTileHeight)
                        }

                        // Draw Grass
                        when (biomeMap[i]) {
                            Biome.City, Biome.Airport, Biome.Docs ->
                                drawing.Bitmap(g, textures.ground.concrete,
                                    drawX - (player.position.x % tileSize.width),
                                    height - heightMap[i].actual * tileSize.height - doubleTileHeight)
                            else ->
                                drawing.Bitmap(g, textures.ground.grass,
                                    drawX - (player.position.x % tileSize.width),
                                    height - heightMap[i].actual * tileSize.height - doubleTileHeight)
                        }

                    }

                    if (biomeMap[i] == Biome.Forest) {
                        // Draw Tree
                        drawing.Bitmap(g, textures.tree.spruce,
                            drawX - (player.position.x % tileSize.width),
                            height - (heightMap[i].actual * tileSize.height) - doubleTileHeight - 128)

                        if (biomeMap.elementAtOrNull(i + 1) == Biome.Forest) {
                            if (heightMap[i] == heightMap[i + 1]) {
                                // Draw More Tree
                                drawing.Bitmap(g, textures.tree.spruce,
                                    drawX - (player.position.x % tileSize.width) + (tileSize.width shr 1),
                                    height - (heightMap[i].actual * tileSize.height) - doubleTileHeight - 128)
                            }
                        }
                    }

                    drawing.Text(g, i.toString(),
                        drawX - (player.position.x % tileSize.width),
                        height-(heightMap[i].actual * tileSize.height)- doubleTileHeight,
                        blackAlphaColor,
                    40f
                    )
                    drawing.Text(g, biomeMap[i].toString(),
                        drawX - (player.position.x % tileSize.width),
                        height-(heightMap[i].actual * tileSize.height)- doubleTileHeight - tileSize.height,
                        blackAlphaColor,
                        20f
                    )

                }
                drawX += tileSize.width
            }
            drawPlayer(g)
            val timeElapsed = (1000F / (SystemClock.elapsedRealtime() - lastTime) * 10).roundToInt().toFloat() / 10

            drawing.Text(g, timeElapsed.toString(), 10, 200, blackAlphaColor, 40f)
            //drawing.RectBorderless(g, DisplayRect(width / 2 - 2,0, 4, height), AlphaColor(0xffc0c0))
        }
    }

    private fun drawOcean(g: Canvas, x: Int) {
        drawing.RectBorderless(g,
            DisplayRect(
                x - (player.position.x % tileSize.width),
                height - doubleTileHeight + oceanDepth,
                tileSize.width,
                doubleTileHeight),
            AlphaColor(Color.CYAN)
        )
    }
    private fun drawPlayer(g: Canvas) {
        adjustYPosition(player.position, player.size)
        drawing.RectBorderless(g, DisplayRect((width / 2) - (player.size.width / 2),
            height - (player.position.y + doubleTileHeight) - player.size.height,
            player.size.width,
            player.size.height), AlphaColor(Color.MAGENTA))
    }

    fun saveSaveFile() {
        try {
            Log.d("FileLocation", File(context.filesDir, saveDestination).toString())
            val fos = FileOutputStream(File(context.filesDir, saveDestination))
            val bos = BufferedOutputStream(fos)
            val oos = ObjectOutputStream(bos)

            val storage: GameSaveData = GameSaveData()

            storage.player = PlayerSaveData(player)

            oos.writeObject(storage)
            oos.close()
            Toast.makeText(context, "Should be saved!", Toast.LENGTH_SHORT).show()
        } catch (ignored: Exception) {
            ignored.printStackTrace()
        }
    }

    fun readSaveFile() {
        try {
            Log.d("FileLocation", File(context.filesDir, saveDestination).toString())
            val fis = FileInputStream(File(context.filesDir, saveDestination))
            val bis = BufferedInputStream(fis)
            val ois = ObjectInputStream(bis)

            val storage: GameSaveData = ois.readObject() as GameSaveData
            player.setFromSaveData(storage.player)
            ois.close()
            Toast.makeText(context, "Should be loaded!", Toast.LENGTH_SHORT).show()
        } catch (ignored: Exception) {
            ignored.printStackTrace()
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(e: MotionEvent?): Boolean {
        Log.i("Touched", "now")
        e?.let {
            val quarterWidth = width / 4
            val offsetToCenter = e.x.toInt() - (width / 2 - (player.size.width / 2))

            if (!e.x.toInt().between(quarterWidth, width - quarterWidth))
                return true
            val pressedX = player.position.x + offsetToCenter
            val mapIndex = pressedX / tileSize.width - 1
            Log.d("OFFSET", "index=$mapIndex, $offsetToCenter, (${player.position})")
        }
        return super.onTouchEvent(e)
    }
}