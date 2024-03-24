package io.github.tledkov.hikelists.ui.inventory

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import io.github.tledkov.hikelists.App
import io.github.tledkov.hikelists.data.entity.ItemEntity
import io.github.tledkov.hikelists.databinding.FragmentInventoryBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.UUID
import java.util.concurrent.ThreadLocalRandom

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
        retrievePersons()

        binding.addItemButton.setOnClickListener {
            val itemEntity = ItemEntity(
                id = ThreadLocalRandom.current().nextInt(),
                name = "Name " + ThreadLocalRandom.current().nextInt(),
                description = "Some description " + UUID.randomUUID(),
                img = "",
                weightGr = ThreadLocalRandom.current().nextInt()
            )

            itemAdapter.addItem(itemEntity)

            insertPerson(itemEntity)
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

    override fun onItemClicked(item: ItemEntity) {
    }

    private fun insertPerson(itemEntity: ItemEntity) {
        // Work on background thread
        lifecycleScope.launch(Dispatchers.IO) {
            (activity?.applicationContext as App).repository.insert(itemEntity = itemEntity)
        }
    }

    private fun retrievePersons() {
        // Work on background thread
        lifecycleScope.launch(Dispatchers.IO) {
            val persons = (activity?.applicationContext as App).repository.getAllPersons()
            // Work on main thread

            withContext(Dispatchers.Main) {
                itemAdapter.setItems(persons)
            }
        }
    }
}