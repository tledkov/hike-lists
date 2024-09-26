package io.github.tledkov.hikelists.ui.inventory

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayoutMediator
import io.github.tledkov.hikelists.App
import io.github.tledkov.hikelists.R
import io.github.tledkov.hikelists.databinding.FragmentAllInventoryBinding

class AllInventoryFragment : Fragment() {

    private val inventoryVm: InventoryViewModel by activityViewModels {
        InventoryViewModelFactory(
            requireActivity().application,
            (requireActivity().application as App).categoryRepository,
            (requireActivity().application as App).inventoryItemRepository,
        )
    }

    private var _binding: FragmentAllInventoryBinding? = null

    private val binding get() = _binding!!

    private lateinit var dynamicFragmentAdapter: DynamicFragmentAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAllInventoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.viewpager.offscreenPageLimit = 5

        dynamicFragmentAdapter = DynamicFragmentAdapter(this, inventoryVm)

        binding.viewpager.setAdapter(dynamicFragmentAdapter)
        TabLayoutMediator(binding.tabs, binding.viewpager, true) { tab, position ->
            tab.text = inventoryVm.tabs[position].name()
        }.attach()

        inventoryVm.categoriesLd.observe(viewLifecycleOwner) { categories ->
            inventoryVm.updateCategories(categories)
            dynamicFragmentAdapter.notifyDataSetChanged()
        }

        inventoryVm.allItemsLd.observe(viewLifecycleOwner) { allItems ->
            inventoryVm.updateAllItems(allItems)
            dynamicFragmentAdapter.notifyDataSetChanged()
        }

        binding.allItemsToolbarAddItemBtn.setOnClickListener {
            inventoryVm.editingItem = null
            it.findNavController().navigate(R.id.action_navigation_inventory_to_editItemFragment)
        }
    }
}
