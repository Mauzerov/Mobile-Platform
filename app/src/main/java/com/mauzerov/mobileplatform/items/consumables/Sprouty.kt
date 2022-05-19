package com.mauzerov.mobileplatform.items.consumables

import android.content.res.Resources
import com.mauzerov.mobileplatform.R

class Sprouty(resources: Resources) : Food(resources) {
    override val healthPoints: Int
        get() = 2
    override val resourceIconId: Int
        get() = R.drawable.ground_concrete
}