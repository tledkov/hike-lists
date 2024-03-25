package io.github.tledkov.hikelists.ui.inventory

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
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
        binding.viewpager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageScrolled(
                position: Int, positionOffset: Float, positionOffsetPixels: Int
            ) {
                binding.tabs.selectTab(binding.tabs.getTabAt(position))
                super.onPageScrolled(position, positionOffset, positionOffsetPixels)
            }
        })

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

        val dynamicFragmentAdapter = DynamicFragmentAdapter(this, binding.tabs.tabCount)

        binding.viewpager.setAdapter(dynamicFragmentAdapter)
        binding.viewpager.setCurrentItem(0)

        return binding.root
    }
}
