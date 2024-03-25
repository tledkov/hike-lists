package io.github.tledkov.hikelists.ui.inventory

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayout
import io.github.tledkov.hikelists.databinding.FragmentAllInventoryBinding


class AllInventoryFragment : Fragment() {

    private var _binding: FragmentAllInventoryBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAllInventoryBinding.inflate(inflater, container, false)

        binding.viewpager.offscreenPageLimit = 5
        binding.viewpager.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(binding.tabs))

        binding.tabs.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                binding.viewpager.currentItem = tab.position
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {
            }

            override fun onTabReselected(tab: TabLayout.Tab) {
            }
        })

        for (i in 0..9) {
            binding.tabs.addTab(binding.tabs.newTab().setText("Page: $i"))
        }

        val dynamicFragmentAdapter =
            activity?.let { DynamicFragmentAdapter(it.supportFragmentManager, binding.tabs.tabCount) }

        binding.viewpager.setAdapter(dynamicFragmentAdapter)
        binding.viewpager.setCurrentItem(0)

        return binding.root
    }
}
