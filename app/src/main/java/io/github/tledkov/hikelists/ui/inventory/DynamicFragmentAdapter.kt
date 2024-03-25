package io.github.tledkov.hikelists.ui.inventory

import android.os.Bundle

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter

class DynamicFragmentAdapter(fm: FragmentManager, val mNumOfTabs: Int) :
    FragmentStatePagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
    override fun getCount(): Int {
        return mNumOfTabs
    }

    override fun getItem(position: Int): Fragment {
        val b = Bundle()
        b.putInt("position", position)
        val frag = InventoryFragment("Category " + position, listOf(), null)
        frag.setArguments(b)
        return frag
    }
} 
