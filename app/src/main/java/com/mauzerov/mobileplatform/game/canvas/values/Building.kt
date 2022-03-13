package com.mauzerov.mobileplatform.game.canvas.values

class Building(buildingType: Int, var height: Int) {
    val type: BuildingType = BuildingType[buildingType]

}

enum class BuildingType {
    None, Intersection, HighDens, Shop, Parking, LowDens, Hut,


    ;companion object {
        operator fun get(index: Int) : BuildingType {
            return BuildingType.values().getOrElse(index) { None }
        }
    }
}