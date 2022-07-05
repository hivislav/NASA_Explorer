package ru.hivislav.nasaexplorer.view.planets

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class PlanetsViewPager2Adapter(fragmentActivity: PlanetsBaseFragment) : FragmentStateAdapter(fragmentActivity) {

    private val fragments = arrayOf(
        EarthFragment.newInstance(),
        MarsFragment.newInstance())

    override fun getItemCount(): Int {
        return fragments.size
    }

    override fun createFragment(position: Int): Fragment {
        return fragments[position]
    }
}