package com.mauzerov.mobileplatform.engine.assets

import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import com.mauzerov.mobileplatform.R

class Textures(resources: Resources) {
    class Ground(resources: Resources) {
        val dirt: Bitmap = BitmapFactory.decodeResource(resources, R.drawable.ground_dirt)
        val grass: Bitmap = BitmapFactory.decodeResource(resources, R.drawable.ground_grass1)
        val concrete: Bitmap = BitmapFactory.decodeResource(resources, R.drawable.ground_concrete)
        val dirt2: Bitmap = BitmapFactory.decodeResource(resources, R.drawable.ground_dirt2)
        val grass3: Bitmap = BitmapFactory.decodeResource(resources, R.drawable.ground_grass3)
        val wall: Bitmap = BitmapFactory.decodeResource(resources, R.drawable.ground_wall)
    }

    class Tree(resources: Resources) {
        val spruce: Bitmap = BitmapFactory.decodeResource(resources, R.drawable.tree_spruce_1)
    }
    val ground = Ground(resources)
    val tree = Tree(resources)
    val sky: Bitmap = BitmapFactory.decodeResource(resources, R.drawable.sky)
}