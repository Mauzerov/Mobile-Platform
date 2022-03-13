package com.mauzerov.mobileplatform.game.save

import com.mauzerov.mobileplatform.game.canvas.values.Height
import java.io.Serializable


class GameSaveData: Serializable {
    lateinit var player: PlayerSaveData
}
class _GameSaveData: Serializable {
    lateinit var terrain: List<Int>
    lateinit var player: PlayerSaveData
}