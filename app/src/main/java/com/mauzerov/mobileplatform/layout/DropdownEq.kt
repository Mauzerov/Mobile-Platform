package com.mauzerov.mobileplatform.layout

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.widget.*
import androidx.constraintlayout.widget.ConstraintLayout
import com.google.android.material.switchmaterial.SwitchMaterial
import com.mauzerov.mobileplatform.R
import com.mauzerov.mobileplatform.databinding.DropdownSettingsBinding
import com.mauzerov.mobileplatform.game.canvas.GameMap
import com.mauzerov.mobileplatform.game.entity.human.Player
import com.mauzerov.mobileplatform.items.ItemDrawable
import com.mauzerov.mobileplatform.items.consumables.Food

@SuppressLint("ViewConstructor")
class DropdownEq : Droppable {

    private lateinit var closeEvent: () -> Unit;

    constructor(context: Context, closeEvent: () -> Unit) : super(context, closeEvent) {
        init(null, closeEvent)
    }

    constructor(context: Context, attrs: AttributeSet, closeEvent: () -> Unit) : super(context, attrs, closeEvent) {
        init(attrs, closeEvent)
    }

    private fun init(attrs: AttributeSet?, closeEvent: () -> Unit) {
        inflate(context, R.layout.dropdown_eq, this)
        this.closeEvent = closeEvent;

        findViewById<ImageView>(R.id.dropdown_settings_closing_button)
            .setOnClickListener{ closeEvent() }
    }

    fun refill(gameMap: GameMap) {
        findViewById<LinearLayout>(R.id.eqItemList).removeAllViews()
        for (item in gameMap.player.items.all) {
            findViewById<LinearLayout>(R.id.eqItemList).addView(ImageView(context).also {
                it.setImageBitmap(item.getIcon())
                it.setOnClickListener {
                    if (item !is Food) {
                        gameMap.player.selectedItem = item
                        gameMap.touchActions[item.resourceIconId] = { event, game ->
                            item.specialActivity(event, game)
                            Log.d("Nothing", "Nothing")
                        }
                    }

                    if (item is ItemDrawable)
                        item.isShowed = true
                    else
                        item.specialActivity(null, gameMap)
                }
            })
        }
    }
}