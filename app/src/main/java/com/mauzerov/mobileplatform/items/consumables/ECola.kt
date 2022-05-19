package com.mauzerov.mobileplatform.items.consumables

import android.content.res.Resources
import com.mauzerov.mobileplatform.R

class ECola(resources: Resources): Food(resources) {
    override val healthPoints: Int
        get() = 2
    override val resourceIconId: Int
        get() = R.drawable.tree_spruce_1
}