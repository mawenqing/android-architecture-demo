package com.timeline.tabhost

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.timeline.list.ARGUMENT_KEY_CATEGORY
import com.timeline.list.TimelineListFragment

/**
 * A [FragmentPagerAdapter] that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
class SectionsPagerAdapter(fm: FragmentManager) :
    FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
    private var tabs = listOf<String>()

    fun updateTabs(tabs: List<String>) {
        this.tabs = tabs
        notifyDataSetChanged()
    }

    override fun getItem(position: Int): Fragment {
        return TimelineListFragment().apply {
            arguments = Bundle().apply {
                putString(ARGUMENT_KEY_CATEGORY, tabs[position])
            }
        }
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return tabs[position]
    }

    override fun getCount(): Int {
        return tabs.count()
    }
}
