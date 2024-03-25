package io.github.tledkov.hikelists.ui.inventory

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class DynamicFragmentAdapter(f: Fragment, val mNumOfTabs: Int) :
    FragmentStateAdapter(f) {

    override fun getItemCount(): Int {
        return mNumOfTabs
    }

    override fun createFragment(position: Int): Fragment {
        val b = Bundle()
        b.putInt("position", position)
        val frag = InventoryFragment()
        frag.setArguments(b)

        return frag
    }
} 
