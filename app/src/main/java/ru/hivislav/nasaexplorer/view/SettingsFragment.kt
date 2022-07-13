package ru.hivislav.nasaexplorer.view

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayout
import ru.hivislav.nasaexplorer.databinding.FragmentSettingsBinding

class SettingsFragment : Fragment() {

    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding!!
    private lateinit var parentActivity: MainActivity

    override fun onAttach(context: Context) {
        super.onAttach(context)
        parentActivity = requireActivity() as MainActivity
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {

        _binding = FragmentSettingsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setTabLayoutClicks()
        binding.settingsNightModeSwitch.isChecked = parentActivity.getNightMode()

        binding.settingsTabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener{
            override fun onTabSelected(tab: TabLayout.Tab?) {
                when(tab?.position) {
                    ThemeDefault -> {parentActivity.setCurrentTheme(ThemeDefault)
                        parentActivity.recreate()}
                    ThemeRed -> {parentActivity.setCurrentTheme(ThemeRed)
                        parentActivity.recreate()}
                    ThemeSilverGold -> { parentActivity.setCurrentTheme(ThemeSilverGold)
                        parentActivity.recreate()
                    }
                }
            }
            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }
            override fun onTabReselected(tab: TabLayout.Tab?) {
            }

        })

        binding.settingsNightModeSwitch.setOnCheckedChangeListener { button, isChecked ->
            if (isChecked) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
            parentActivity.setNightMode(isChecked)
        }
    }

    private fun setTabLayoutClicks() = with(binding) {
        when(parentActivity.getCurrentTheme()) {
            ThemeDefault -> {
                settingsTabLayout.selectTab(settingsTabLayout.getTabAt(ThemeDefault))
            }
            ThemeRed -> {
                settingsTabLayout.selectTab(settingsTabLayout.getTabAt(ThemeRed))
            }
            ThemeSilverGold -> {
                settingsTabLayout.selectTab(settingsTabLayout.getTabAt(ThemeSilverGold))
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {
        fun newInstance() = SettingsFragment()
    }
}