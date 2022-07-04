package ru.hivislav.nasaexplorer.view

import android.content.Context
import android.os.Bundle
import android.util.AttributeSet
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import ru.hivislav.nasaexplorer.R
import ru.hivislav.nasaexplorer.databinding.ActivityMainBinding

const val ThemeDefault = 0
const val ThemeRed = 1
const val ThemeSilverGold = 2


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val KEY_SP = "sp"
    private val KEY_CURRENT_THEME = "current_theme"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setTheme(applyAppStyle(getCurrentTheme()))
        binding =  ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction().add(R.id.container, MainFragment.newInstance()).commit()
        }
    }

    fun setCurrentTheme(currentTheme: Int) {
        val sharedPreferences = getSharedPreferences(KEY_SP, MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putInt(KEY_CURRENT_THEME, currentTheme)
        editor.apply()
    }

    fun getCurrentTheme(): Int {
        val sharedPreferences = getSharedPreferences(KEY_SP, MODE_PRIVATE)
        return sharedPreferences.getInt(KEY_CURRENT_THEME, -1)
    }

    private fun applyAppStyle(currentTheme: Int): Int {
        return when (currentTheme) {
            ThemeDefault -> R.style.Theme_NASAExplorer
            ThemeRed -> R.style.Theme_NASAExplorerRed
            ThemeSilverGold -> R.style.Theme_NASAExplorerSilverGold
            else -> 0
        }
    }
}