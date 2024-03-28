package io.github.tledkov.hikelists.ui.inventory

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import io.github.tledkov.hikelists.App
import io.github.tledkov.hikelists.R
import io.github.tledkov.hikelists.databinding.FragmentInventoryBinding
import io.github.tledkov.hikelists.domain.InventoryItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


const val ITEMS_KEY: String = "items"
class InventoryFragment : Fragment(), ItemAdapter.OnItemClickListener {

    private var _binding: FragmentInventoryBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private lateinit var itemAdapter: ItemAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentInventoryBinding.inflate(inflater, container, false)
        val root: View = binding.root

        initRecyclerView()

        val items = arguments?.getSerializable(ITEMS_KEY)
        itemAdapter.setItems((items as AllInventoryFragment.TabData).items)

        binding.addItemButton.setOnClickListener {
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

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun initRecyclerView() {
        itemAdapter = ItemAdapter(this)

        with(binding.itemList) {
            this.layoutManager = LinearLayoutManager(context)
            this.adapter = itemAdapter
            this.setHasFixedSize(true)
        }
    }

    override fun onItemClicked(item: InventoryItem) {
    }

    private fun insertItem(item: InventoryItem) {
        // Work on background thread
        lifecycleScope.launch(Dispatchers.IO) {
            (activity?.applicationContext as App).inventoryItemRepository.insert(item)
        }
    }
}