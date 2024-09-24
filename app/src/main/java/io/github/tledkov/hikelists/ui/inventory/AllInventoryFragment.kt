package io.github.tledkov.hikelists.ui.inventory

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
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

        binding.allItemsToolbarAddItemBtn.setOnClickListener {
            it.findNavController().navigate(R.id.action_navigation_inventory_to_editItemFragment)

//            val itemEntity = InventoryItem(
//                name = "Name " + ThreadLocalRandom.current().nextInt(),
//                description = "Some description " + UUID.randomUUID(),
//                weight = Weight.from(ThreadLocalRandom.current().nextInt() % 5000)
//            )
//
//            itemAdapter.addItem(itemEntity)
//
//            insertItem(itemEntity)
        }


        loadTabData()
    }

    private fun loadTabData() {
        // Work on background thread
//        lifecycleScope.launch(Dispatchers.IO) {
//            val allItems: List<InventoryItem> =
//                (activity?.applicationContext as App).inventoryItemRepository.getAllItems()
//            val categories: List<Category> =
//                (activity?.applicationContext as App).categoryRepository.getAllCategories()
//
//            val inventory = Inventory(categories, allItems)
//
//            tabData.add(TabData(inventory.allInventoryItems, binding.root.resources.getString(R.string.category_all_items)))
//
//            for (cat in categories) {
//                tabData.add(TabData(inventory.itemsByCategory[cat]!!, cat))
//            }
//
//            tabData.add(TabData(inventory.withoutCategoryItems, binding.root.resources.getString(R.string.category_not_category)))
//
//            withContext(Dispatchers.Main) {
//                dynamicFragmentAdapter.notifyDataSetChanged()
//            }
//        }
    }

//    inner class TabData(
//        val items: List<InventoryItem>,
//        private val category: Category?,
//        private val name: String?
//    ) : Serializable {
//        constructor(items: List<InventoryItem>, category: Category) : this(items, category, null)
//        constructor(items: List<InventoryItem>, name: String) : this(items, null, name)
//
//        fun name(): String {
//            return if (name != null) {
//                return name
//            } else {
//                category!!.name
//            }
//        }
//    }
}
