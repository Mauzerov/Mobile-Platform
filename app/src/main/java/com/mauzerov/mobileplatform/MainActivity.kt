package com.mauzerov.mobileplatform

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.*
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.*
import androidx.navigation.ui.AppBarConfiguration
import androidx.core.content.ContextCompat
import com.mauzerov.mobileplatform.game.Dimensions
import com.mauzerov.mobileplatform.game.JoyStick
import com.mauzerov.mobileplatform.game.canvas.GameConstants
import com.mauzerov.mobileplatform.game.canvas.GameMap
import com.mauzerov.mobileplatform.game.canvas.GameTopBar
import com.mauzerov.mobileplatform.layout.DropdownEq
import com.mauzerov.mobileplatform.layout.DropdownSettings
import com.mauzerov.mobileplatform.layout.Droppable
import com.mauzerov.mobileplatform.databinding.ActivityMainBinding
import com.mauzerov.mobileplatform.layout.MainMenuLayout
import com.mauzerov.mobileplatform.sizes.Size
import com.mauzerov.mobileplatform.source.FunctionStack

class MainActivity : AppCompatActivity(), JoyStick.JoystickListener {
    private var backButtonStack: FunctionStack = FunctionStack()
    private var isDropdownDown = false
    private lateinit var dropdownAnimationSlideUp: Animation
    private lateinit var dropdownAnimationSlideDown: Animation
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding
    private lateinit var mainMenu: MainMenuLayout
    private var settingMenu: View? = null
    private var eqMenu: View? = null
    private var gameMap: GameMap? = null
    var gameTopBar: GameTopBar? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        /** Load Animations **/
        dropdownAnimationSlideUp =
            AnimationUtils.loadAnimation(this, R.anim.settings_slide_up)
        dropdownAnimationSlideDown =
            AnimationUtils.loadAnimation(this, R.anim.settings_slide_down)

        ///setSupportActionBar(binding.toolbar)

        //val navController = findNavController(R.id.nav_host_fragment_content_main)
//        appBarConfiguration = AppBarConfiguration(navController.graph)
//        setupActionBarWithNavController(navController, appBarConfiguration)

//        binding.dropdownSettingsButton.setOnClickListener {
//            if (settingMenu != null && settingMenu!!.visibility == View.GONE) {
//                showSettingMenu()
//            }
//            else {
//                createOrHideSettingMenu()
//            }
//        }
        mainMenu = MainMenuLayout(this)
        val mainMenuLayoutParams = LinearLayout.LayoutParams(
            ViewGroup.LayoutParams(MATCH_PARENT, MATCH_PARENT)
        )
        binding.activityMainLayout.addView(mainMenu, mainMenuLayoutParams)
    }

    fun loadGame(fileName: String) {
        newGame(fileName)
        gameMap!!.readSaveFile()
    }

    fun newGame(fileName: String) {
        gameMap = GameMap(this, fileName)
        val gameTopBarLayout = LinearLayout.LayoutParams(
            ViewGroup.LayoutParams(MATCH_PARENT, 80)
        )
        gameTopBar = GameTopBar(this, gameMap!!)
        binding.activityMainLayout.addView(gameMap)
        binding.activityMainLayout.addView(gameTopBar, gameTopBarLayout)
        binding.joystick.visibility = View.VISIBLE
        binding.joystick.bringToFront()
        gameTopBar!!.bringToFront()

        backButtonStack += {
            this.gameMap!!.saveSaveFile()
            this.binding.activityMainLayout.removeView(this.gameMap)
            this.binding.joystick.visibility = View.GONE
            this.gameMap = null
            this.binding.activityMainLayout.removeView(this.gameTopBar)
            this.gameTopBar = null
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onBackPressed() {
//        if (this.isDropdownDown) {
//            hideDropdownMenu(settingMenu)
//            hideDropdownMenu(eqMenu)
//            return
//        }
//
//        if (this.gameMap != null) {
//
//            this.gameMap!!.saveSaveFile()
//            this.binding.activityMainLayout.removeView(this.gameMap)
//            this.binding.joystick.visibility = View.GONE
//            this.gameMap = null
//            this.binding.activityMainLayout.removeView(this.gameTopBar)
//            this.gameTopBar = null
//            return
//        }
        if (backButtonStack.size > 0) {
            backButtonStack.call()
            return
        }

        super.onBackPressed()
    }
/*
//    override fun onOptionsItemSelected(item: MenuItem): Boolean {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        return when (item.itemId) {
//            R.id.action_settings -> {
//                if (settingMenu != null && settingMenu?.visibility == View.GONE) {
//                    showSettingMenu()
//                }
//                else {
//                    createOrHideSettingMenu()
//                }
//                return true
//            }
//            R.id.action_save_map -> {
//                gameMap.saveSaveFile(GameConstants.fileName)
//                return true
//            }
//
//            R.id.action_height_change -> {
//                gameMap.heightTypeChange = if (gameMap.heightTypeChange == 0) 1 else 0
//                return true
//            }
//
//            else -> super.onOptionsItemSelected(item)
//        }
//    }
*/
    private inline fun <reified T: Droppable> createAndAddPanel(crossinline closeEvent: () -> Unit) : View {
        val constructor = T::class.constructors.firstOrNull {
            it.parameters.size == 2
        }
        val panel = constructor!!.call(this, { closeEvent() })

        val screenSize = Size.getScreenSize(this)

        if (screenSize.height > screenSize.width)
            screenSize.width = screenSize.height.also { screenSize.height = screenSize.width }

        val panelLayout = RelativeLayout.LayoutParams(
            RelativeLayout.LayoutParams.MATCH_PARENT,
            RelativeLayout.LayoutParams.MATCH_PARENT
        ).apply {
            leftMargin = 0
            topMargin = 0
            rightMargin = 0
            bottomMargin = 0
        }

        panel.setBackgroundColor(
            ContextCompat.getColor( this, R.color.dark_black_overlay )
        )
        this.addContentView(panel, panelLayout)
        return panel
    }
    private fun runAfterResourceId(function : () -> Unit, id : Int) {
        Handler(Looper.getMainLooper()).postDelayed(
            function, resources.getInteger(id).toLong()
        )
    }
    private fun createSettingMenu() {
        if (settingMenu == null) {
            settingMenu = createAndAddPanel<DropdownSettings>{ hideDropdownMenu(settingMenu) }
            showDropdownMenu(settingMenu)
        }
    }

    private fun createEqMenu() {
        if (eqMenu == null) {
            eqMenu = createAndAddPanel<DropdownEq> { hideDropdownMenu(eqMenu) }
            showDropdownMenu(eqMenu)
        }
    }

    private fun showDropdownMenu(droppable: View?) {
        if (droppable == null)
            return
        isDropdownDown = true
        droppable.visibility = View.VISIBLE
        droppable.startAnimation(dropdownAnimationSlideDown)
        backButtonStack += { hideDropdownMenu(droppable, false) }
    }
    private fun hideDropdownMenu(droppable: View?, remove: Boolean = true) {
        if (droppable == null)
            return
        isDropdownDown = false
        droppable.startAnimation(dropdownAnimationSlideUp)
        runAfterResourceId(
            { droppable.visibility = View.GONE; }, R.integer.animation_duration
        )
        if (remove) {
            backButtonStack--
        }
    }

    fun toggleSettingsMenu() {
        settingMenu?.let {
            when (it.visibility) {
                View.VISIBLE -> { hideDropdownMenu(it) }
                View.GONE -> { showDropdownMenu(it) }
                else -> Unit
            }
        }?:run {
            createSettingMenu()
        }
    }

    fun toggleEqMenu() {
        eqMenu?.let {
            when (it.visibility) {
                View.VISIBLE -> { hideDropdownMenu(it) }
                View.GONE -> { showDropdownMenu(it) }
                else -> Unit
            }
        }?:run {
            createEqMenu()
        }
    }


    override fun onJoystickMoved(percent: Dimensions, id: Int) {
        gameMap?.player?.setVelocity(percent.x.times(5).toInt(), 0)//-percent.y.times(5).toInt()
    }

//    override fun onPause() {
//        Toast.makeText(this, "onPause()", Toast.LENGTH_SHORT).show()
//        gameMap?.saveSaveFile(GameConstants.fileName)
//        super.onPause()
//    }
//    override fun onResume() {
//        Toast.makeText(this, "onResume()", Toast.LENGTH_SHORT).show()
//        gameMap?.readSaveFile(GameConstants.fileName)
//        super.onResume()
//    }
}