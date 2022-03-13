package com.mauzerov.mobileplatform.game.save

import com.mauzerov.mobileplatform.sizes.Position
import java.io.Serializable

open class EntitySaveData : Serializable {
    var position: Position = Position(0, 0)
}