package com.mauzerov.mobileplatform.items.consumables

import android.content.res.Resources
import com.mauzerov.mobileplatform.R

class Chocolate(resources: Resources) : Food(resources) {
    override val healthPoints: Int
        get() = 3
    override val resourceIconId: Int
        get() = R.drawable.ground_dirt2
}