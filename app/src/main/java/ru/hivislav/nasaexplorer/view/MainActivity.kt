package ru.hivislav.nasaexplorer.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import ru.hivislav.nasaexplorer.R
import ru.hivislav.nasaexplorer.databinding.ActivityMainBinding
import ru.hivislav.nasaexplorer.view.coordinator.CoordinatorFragment
import ru.hivislav.nasaexplorer.view.picoftheday.PicOfTheDayBaseFragment
import ru.hivislav.nasaexplorer.view.planets.PlanetsBaseFragment

const val ThemeDefault = 0
const val ThemeRed = 1
const val ThemeSilverGold = 2


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val KEY_SP = "sp"
    private val KEY_CURRENT_THEME = "current_theme"
    private val KEY_NIGHT_MODE = "night_mode"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setTheme(applyAppStyle(getCurrentTheme()))
        binding =  ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction().add(R.id.container, PicOfTheDayBaseFragment.newInstance()).commit()
        }

        binding.mainBottomNavigation.selectedItemId = R.id.navBottomPicOfTheDay

        binding.mainBottomNavigation.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.navBottomPicOfTheDay -> {
                    navigateTo(PicOfTheDayBaseFragment.newInstance())
                    true
                }
                R.id.navBottomPlanets -> {
                    navigateTo(PlanetsBaseFragment.newInstance())
                    true
                }
                R.id.navBottomSettings -> {
                    navigateTo(SettingsFragment.newInstance())
                    true
                }
                R.id.navBottomCoordinator -> {
                    navigateTo(CoordinatorFragment.newInstance())
                    true
                }
                else -> false
            }
        }
    }

    private fun navigateTo(fragment: Fragment) {
        supportFragmentManager.beginTransaction().replace(R.id.container, fragment).commit()
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

        if (getNightMode()) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }
        return when (currentTheme) {
            ThemeDefault -> R.style.Theme_NASAExplorer
            ThemeRed -> R.style.Theme_NASAExplorerRed
            ThemeSilverGold -> R.style.Theme_NASAExplorerSilverGold
            else -> R.style.Theme_NASAExplorer
        }
    }

    fun setNightMode(nightModeOn: Boolean) {
        val sharedPreferences = getSharedPreferences(KEY_SP, MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putBoolean(KEY_NIGHT_MODE, nightModeOn)
        editor.apply()
    }

    fun getNightMode(): Boolean {
        val sharedPreferences = getSharedPreferences(KEY_SP, MODE_PRIVATE)
        return sharedPreferences.getBoolean(KEY_NIGHT_MODE, false)
    }
}