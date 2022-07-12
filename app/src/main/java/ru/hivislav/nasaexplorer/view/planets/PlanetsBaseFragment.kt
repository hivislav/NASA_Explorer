package ru.hivislav.nasaexplorer.view.planets

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import ru.hivislav.nasaexplorer.R
import ru.hivislav.nasaexplorer.databinding.FragmentPlanetsBaseBinding


class PlanetsBaseFragment : Fragment() {

    private var _binding: FragmentPlanetsBaseBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {

        _binding = FragmentPlanetsBaseBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.planetsViewPager.adapter = PlanetsViewPager2Adapter(this)
        bindTabLayout()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun bindTabLayout() {
        TabLayoutMediator(binding.planetsTabLayout, binding.planetsViewPager,
            object : TabLayoutMediator.TabConfigurationStrategy {
                override fun onConfigureTab(tab: TabLayout.Tab, position: Int) {
                    tab.text = when (position) {
                        0 -> {
                            resources.getString(R.string.planets_fragment_earth)
                        }
                        1 -> {
                            resources.getString(R.string.planets_fragment_mars)
                        }
                        else -> ""
                    }
                }
            }).attach()
    }

    companion object {
        fun newInstance() = PlanetsBaseFragment()
    }
}