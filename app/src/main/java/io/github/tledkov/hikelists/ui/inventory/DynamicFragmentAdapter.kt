package io.github.tledkov.hikelists.ui.inventory

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class DynamicFragmentAdapter(f: Fragment, val inventoryVm: InventoryViewModel) :
    FragmentStateAdapter(f) {

    override fun getItemCount(): Int {
        return inventoryVm.tabs.size
    }

    override fun createFragment(position: Int): Fragment {
        val fragBundle = Bundle()
        fragBundle.putInt(ITEMS_KEY, position)

        val frag = InventoryFragment()
        frag.setArguments(fragBundle)

        return frag
    }
} 
