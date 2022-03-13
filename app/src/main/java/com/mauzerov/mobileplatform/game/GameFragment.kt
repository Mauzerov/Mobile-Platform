package com.mauzerov.mobileplatform.game

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import com.mauzerov.mobileplatform.game.canvas.GameMap

class GameFragment(contentLayoutId: Int) : Fragment(contentLayoutId) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        Toast.makeText(context, "That's a Fragment", Toast.LENGTH_SHORT).show()
        (view as ConstraintLayout).addView(GameMap(requireContext(), ""))
        super.onViewCreated(view, savedInstanceState)
    }
}