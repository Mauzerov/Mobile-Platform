package com.mauzerov.mobileplatform.layout

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.*
import androidx.constraintlayout.widget.ConstraintLayout
import com.google.android.material.switchmaterial.SwitchMaterial
import com.mauzerov.mobileplatform.R
import com.mauzerov.mobileplatform.databinding.DropdownSettingsBinding

@SuppressLint("ViewConstructor")
class DropdownEq : Droppable {

    private lateinit var closeEvent: () -> Unit;
    private lateinit var locationButton: Button;
    private lateinit var locationCheckBox: CheckBox;
    private lateinit var locationSwitch: Switch;

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
}