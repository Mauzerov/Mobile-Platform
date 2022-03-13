package com.mauzerov.mobileplatform.game.save

import java.io.Serializable

class JoyStickPositionSaveData {
    /**
     * 0 = LEFT_OFFSET,  DEFAULT
     * 1 = RIGHT_OFFSET, INVERTED
     * 2 =               CUSTOM
     * **/
    var locationMode = 0
    var offsetMode = 0
    var horizontalOffset = 0
    var verticalOffset = 0
}

class SettingsSaveData: Serializable {
    var musicOn = true
    var soundsOn = true
    var joyStickPosition = JoyStickPositionSaveData()
}