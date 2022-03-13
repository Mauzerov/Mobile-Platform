package com.mauzerov.mobileplatform.game.canvas

import kotlin.AssertionError
import kotlin.jvm.Throws
import com.mauzerov.mobileplatform.game.canvas.values.Building

@Throws(AssertionError::class)
fun loadFromHex(hexArray: String) : List<Building> {
    val returnList = mutableListOf<Building>()

    assert (hexArray.length and 1 == 0) { println("Length Must Be Even") }
    for (i in hexArray.indices step 2) {
        val line = hexArray.substring(i, i + 2)
        val a = line[0].toString().toInt(radix=16)
        val b = line[1].toString().toInt(radix=16)

        returnList.add(Building(a, b))
    }

    return returnList
}

fun createHexMap() : String {
    val width = GameConstants.mapSize
    var returningString: String = "";
    for (i in 0 until width) {

    }
    return returningString
};