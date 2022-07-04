package ru.hivislav.nasaexplorer.view.picoftheday

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class PicOfTheDayViewPager2Adapter(fragmentActivity: PicOfTheDayBaseFragment) : FragmentStateAdapter(fragmentActivity) {

    private val bundleToday = Bundle().apply { putInt(PicOfTheDayFragment.PIC_OF_THE_DAY_BUNDLE_EXTRA, 0) }
    private val bundleYesterday = Bundle().apply { putInt(PicOfTheDayFragment.PIC_OF_THE_DAY_BUNDLE_EXTRA, 1) }
    private val bundleDayBeforeYesterday = Bundle().apply { putInt(PicOfTheDayFragment.PIC_OF_THE_DAY_BUNDLE_EXTRA, 2) }

    private val fragments = arrayOf(
        PicOfTheDayFragment.newInstance(bundleToday),
        PicOfTheDayFragment.newInstance(bundleYesterday),
        PicOfTheDayFragment.newInstance(bundleDayBeforeYesterday))
    override fun getItemCount(): Int {
        return fragments.size
    }

    override fun createFragment(position: Int): Fragment {
        return fragments[position]
    }
}