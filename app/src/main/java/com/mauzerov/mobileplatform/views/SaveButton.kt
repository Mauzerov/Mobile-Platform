package com.mauzerov.mobileplatform.views

import android.annotation.SuppressLint
import android.content.Context
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageButton
import androidx.constraintlayout.widget.ConstraintLayout
import com.mauzerov.mobileplatform.MainActivity
import com.mauzerov.mobileplatform.R
import com.mauzerov.mobileplatform.layout.MainMenuLayout
import java.io.File
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.*

@SuppressLint("ViewConstructor")
class SaveButton(context: Context, menu: MainMenuLayout, saveFileName: String) : ConstraintLayout(context) {
    private var infoTextView: TextView
    private var playButton: ImageButton
    private var deleteButton: ImageButton

    init {
        inflate(context, R.layout.main_menu_save_row,this)
        infoTextView = this.findViewById(R.id.main_menu_save_row_info)
        playButton = this.findViewById(R.id.main_menu_save_row_play)
        deleteButton = this.findViewById(R.id.main_menu_save_row_delete)
        val saveFile = File(context.filesDir, saveFileName)
        val fileWithoutExtension = saveFile.nameWithoutExtension
        var infoString = "$fileWithoutExtension\n" +
                if (saveFile.exists()) "Load Game!" else "New Game!"
        if (saveFile.exists()) {
            val dateFormat = SimpleDateFormat("dd-MM-yyyy HH:mm:ss", Locale.ROOT)
            infoString += "    " + dateFormat.format(Date(saveFile.lastModified()))
        }
        infoTextView.text = infoString

        if (saveFile.exists()) {
            playButton.setOnClickListener {
                (context as MainActivity).loadGame(saveFileName)
            }
        } else {
            playButton.setOnClickListener {
                (context as MainActivity).newGame(saveFileName)
            }
        }
    }
}