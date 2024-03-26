package io.github.tledkov.hikelists.ui.inventory

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class DynamicFragmentAdapter(f: Fragment, val tabData: MutableList<AllInventoryFragment.TabData>) :
    FragmentStateAdapter(f) {

    override fun getItemCount(): Int {
        return tabData.size
    }

    override fun createFragment(position: Int): Fragment {
        val fragBundle = Bundle()
        fragBundle.putSerializable(ITEMS_KEY, tabData[position])

        val frag = InventoryFragment()
        frag.setArguments(fragBundle)

        return frag
    }
} 
