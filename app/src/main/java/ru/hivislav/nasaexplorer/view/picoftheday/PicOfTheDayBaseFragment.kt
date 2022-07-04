package ru.hivislav.nasaexplorer.view.picoftheday

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import ru.hivislav.nasaexplorer.R
import ru.hivislav.nasaexplorer.databinding.FragmentPicOfTheDayBaseBinding

class PicOfTheDayBaseFragment : Fragment() {

    private var _binding: FragmentPicOfTheDayBaseBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentPicOfTheDayBaseBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.picOfTheDayViewPager.adapter = PicOfTheDayViewPager2Adapter(this)
        bindTabLayout()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun bindTabLayout() {
        TabLayoutMediator(binding.picOfTheDayTabLayout, binding.picOfTheDayViewPager,
            object : TabLayoutMediator.TabConfigurationStrategy {
                override fun onConfigureTab(tab: TabLayout.Tab, position: Int) {
                    tab.text = when (position) {
                        0 -> {
                            resources.getString(R.string.today)
                        }
                        1 -> {
                            resources.getString(R.string.yesterday)
                        }
                        2 -> {
                            resources.getString(R.string.day_before_yesterday)
                        }
                        else -> ""
                    }
                }
            }).attach()
    }

    companion object {
        fun newInstance() = PicOfTheDayBaseFragment()
    }
}