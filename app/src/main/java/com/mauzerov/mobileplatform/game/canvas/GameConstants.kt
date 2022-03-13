package com.mauzerov.mobileplatform.game.canvas

import com.mauzerov.mobileplatform.game.canvas.values.Biome
import com.mauzerov.mobileplatform.game.canvas.values.Height
import com.mauzerov.mobileplatform.sizes.Size
typealias H = Height

object GameConstants {
    val heightMap = listOf(H(0, 0), H(1, 1), H(1, 1), H(2, 2), H(3, 3), H(4, 4), H(6, 4), H(7, 4), H(9, 4), H(11, 4), H(14, 4), H(13, 4), H(12, 4), H(10, 4), H(9, 4), H(9, 4), H(8, 4), H(7, 4), H(6, 4), H(6, 5), H(5, 5), H(5, 5), H(5, 5), H(5, 5), H(6, 6), H(7, 7), H(7, 7), H(7, 7), H(6, 6), H(5, 5), H(4, 4), H(3, 3), H(3, 3), H(3, 3), H(2, 2), H(1, 1), H(1, 1), H(1, 1), H(1, 1), H(1, 1), H(2, 2), H(2, 2), H(3, 3), H(4, 4), H(4, 4), H(5, 5), H(5, 5), H(5, 5), H(6, 6), H(6, 6), H(6, 6), H(7, 7), H(7, 7), H(7, 7), H(7, 7), H(6, 6), H(6, 6), H(5, 5), H(4, 4), H(4, 4), H(3, 3), H(2, 2), H(2, 2), H(1, 1), H(1, 1), H(0, 0), H(0, 0), H(0, 0), H(0, 0), H(1, 1), H(1, 1), H(1, 1), H(1, 1), H(2, 2), H(2, 2), H(2, 2), H(2, 2), H(2, 2), H(2, 2), H(2, 2), H(1, 1), H(1, 1), H(1, 1), H(1, 1), H(1, 1), H(1, 1), H(1, 1), H(0, 0), H(0, 0), H(0, 0), H(0, 0), H(0, 0), H(0, 0), H(0, 0), H(0, 0), H(0, 0), H(0, 0), H(0, 0), H(0, 0), H(0, 0), H(0, 0), H(0, 0), H(0, 0), H(0, 0), H(0, 0), H(1, 1), H(1, 1), H(1, 1), H(1, 1), H(1, 1), H(1, 1), H(1, 1), H(1, 1), H(0, 0), H(0, 0), H(0, 0), H(0, 0), H(0, 0), H(0, 0), H(0, 0), H(0, 0), H(0, 0), H(0, 0), H(0, 0), H(0, 0), H(0, 0), H(0, 0), H(1, 1), H(1, 1), H(1, 1), H(1, 1), H(1, 1), H(1, 1), H(1, 1), H(0, 0), H(0, 0), H(0, 0), H(0, 0), H(0, 0), H(0, 0), H(0, 0), H(0, 0), H(0, 0), H(0, 0), H(0, 0), H(1, 1), H(1, 1), H(1, 1), H(1, 1), H(2, 2), H(2, 2), H(2, 2), H(2, 2), H(2, 2), H(2, 2), H(2, 2), H(2, 2), H(2, 2), H(2, 2), H(2, 2), H(2, 2), H(2, 2), H(2, 2), H(2, 2), H(2, 2), H(2, 2), H(2, 2), H(2, 2), H(2, 2), H(1, 1), H(1, 1), H(2, 2), H(2, 2), H(2, 2), H(3, 3), H(3, 3), H(3, 3), H(4, 4), H(5, 5), H(6, 5), H(7, 4), H(7, 4), H(8, 4), H(9, 4), H(10, 4), H(11, 4), H(13, 4), H(15, 4), H(17, 4), H(17, 4), H(15, 4), H(13, 4), H(11, 4), H(8, 4), H(6, 4), H(4, 4), H(3, 3), H(2, 2), H(1, 1), H(0, 0),)

    data class BiomeRange(val biome: Biome, val length: Int)
    const val fileName: String = "save1.dat"

    private fun biomeFromRage(vararg biomes: BiomeRange) : List<Biome> {
        val returnList: MutableList<Biome> = mutableListOf()
        for (biome in biomes) {
            for (i in 0 until biome.length)
                returnList.add(biome.biome)
        }
        return returnList
    }

    val biomeMap: List<Biome> =
        biomeFromRage(
            BiomeRange(Biome.Mountains, 12),
            BiomeRange(Biome.Forest,    18),
            BiomeRange(Biome.Plains,    15),
            BiomeRange(Biome.Forest,    20),
            BiomeRange(Biome.Suburbs,   11),
            BiomeRange(Biome.City,      44),
            BiomeRange(Biome.Suburbs,   17),
            BiomeRange(Biome.Plains,    16),
            BiomeRange(Biome.Airport,   16),
            BiomeRange(Biome.Forest,    12),
            BiomeRange(Biome.Mountains, 19),
        )
    val biomeColorMap: Map<Biome, Int> = mutableMapOf(
        Pair(Biome.Ocean, 0x000000),
        Pair(Biome.Mountains, 0xf1f2f6),
        Pair(Biome.Plains, 0x2ecc71),
        Pair(Biome.Airport, 0xA3CB38),
        Pair(Biome.Forest, 0x27ae60),
        Pair(Biome.City, 0x05c46b),
        Pair(Biome.Suburbs, 0x2ed573),
        Pair(Biome.Docs, 0xFFFFFF),
    )
    val mapSize get() = biomeMap.size
    const val oceanDepth = 4
    const val maxSkyHeight = 10
    val tileSize = Size(96, 32)
    val doubleTileWidth = tileSize.width * 2
    val doubleTileHeight = tileSize.height * 2
    const val twoThirds = 2.0 / 3.0
}