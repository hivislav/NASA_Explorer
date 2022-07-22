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

        binding =  ActivityMainBinding.inflate(layoutInflater)

        setTheme(applyAppStyle(getCurrentTheme()))
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction().add(R.id.container, PicOfTheDayBaseFragment.newInstance()).commit()
        }

        binding.mainBottomNavigation.selectedItemId = R.id.navBottomPicOfTheDay
        var menuItemOrder = 2

        binding.mainBottomNavigation.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.navBottomPicOfTheDay -> {
                    if (it.order < menuItemOrder) {
                        navigateToLeft(PicOfTheDayBaseFragment.newInstance())
                    } else {
                        navigateToRight(PicOfTheDayBaseFragment.newInstance())
                    }
                    menuItemOrder = it.order
                    true
                }
                R.id.navBottomPlanets -> {
                    if (it.order < menuItemOrder) {
                        navigateToLeft(PlanetsBaseFragment.newInstance())
                    } else {
                        navigateToRight(PlanetsBaseFragment.newInstance())
                    }
                    menuItemOrder = it.order
                    true
                }
                R.id.navBottomSettings -> {
                    if (it.order < menuItemOrder) {
                        navigateToLeft(SettingsFragment.newInstance())
                    } else {
                        navigateToRight(SettingsFragment.newInstance())
                    }
                    menuItemOrder = it.order
                    true
                }
                R.id.navBottomCoordinator -> {
                    if (it.order < menuItemOrder) {
                        navigateToLeft(CoordinatorFragment.newInstance())
                    } else {
                        navigateToRight(CoordinatorFragment.newInstance())
                    }
                    menuItemOrder = it.order
                    true
                }
                else -> false
            }
        }
    }

    private fun navigateToLeft(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .setCustomAnimations(R.anim.enter_from_left, R.anim.exit_to_right, R.anim.fade_in, R.anim.fade_out)
            .replace(R.id.container, fragment)
            .commit()
    }

    private fun navigateToRight(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim.fade_in, R.anim.fade_out)
            .replace(R.id.container, fragment)
            .commit()
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