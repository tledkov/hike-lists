package io.github.tledkov.hikelists.ui.lists

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import io.github.tledkov.hikelists.App
import io.github.tledkov.hikelists.R
import io.github.tledkov.hikelists.databinding.ListMainEditBinding
import io.github.tledkov.hikelists.domain.InventoryList

class EditListFragment : Fragment(), ListItemsAdapter.OnItemClickListener {

    private val args: EditListFragmentArgs by navArgs()

    private val listsVm: ListsViewModel by activityViewModels {
        ListsViewModelFactory(
            requireActivity().application,
            (requireActivity().application as App).inventoryListRepository,
        )
    }

    private var _binding: ListMainEditBinding? = null
    private val binding get() = _binding!!

    private lateinit var itemsAdapter: ListItemsAdapter
    private var currentList: InventoryList? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = ListMainEditBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val listId = args.listId

        setupToolbar()
        setupNameField()
        setupRecyclerView()
        setupFab()

        // Наблюдаем за изменениями списка
        listsVm.listLd.observe(viewLifecycleOwner) { lists ->
            val updatedList = lists.find { it.id == listId }
            updatedList?.let { list ->
                currentList = list
                binding.editListMainListMainEditName.setText(list.name)
                itemsAdapter.setItems(list.items)
                updateToolbar(listId != -1)
            }
        }
    }

    private fun setupToolbar() {
        binding.listMainEditToolbar.setNavigationOnClickListener { view ->
            view.findNavController().popBackStack()
        }

        binding.listMainEditToolbar.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.action_save -> {
                    saveList()
                    requireView().findNavController().popBackStack()
                    true
                }
                R.id.action_delete -> {
                    currentList?.let { list ->
                        listsVm.deleteList(list)
                    }
                    requireView().findNavController().popBackStack()
                    true
                }
                else -> false
            }
        }
    }

    private fun updateToolbar(isEditing: Boolean) {
        binding.listMainEditToolbar.menu.findItem(R.id.action_delete).isVisible = isEditing
    }

    private fun setupNameField() {
        binding.editListMainListMainEditName.doAfterTextChanged { text ->
            binding.listMainEditToolbar.menu.findItem(R.id.action_save).isEnabled = !text.isNullOrBlank()
        }
    }

    private fun setupRecyclerView() {
        itemsAdapter = ListItemsAdapter(this)
        binding.listMainEditItemsRecyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = itemsAdapter
        }

        currentList?.let { list ->
            itemsAdapter.setItems(list.items)
        }
    }

    private fun setupFab() {
        binding.listMainEditAddItemFab.setOnClickListener {
            // TODO: Navigate to inventory to select items
        }
    }

    private fun saveList() {
        val name = binding.editListMainListMainEditName.text.toString().trim()
        val listId = args.listId

        val updatedItems = mutableListOf<InventoryList.UsedItem>()
        for (i in 0 until itemsAdapter.itemCount) {
            updatedItems.add(itemsAdapter.getItemAt(i))
        }

        val list = if (listId != -1) {
            InventoryList(
                id = listId,
                name = name,
                description = currentList?.description ?: "",
                items = updatedItems,
                color = currentList?.color ?: Color.valueOf(Color.GRAY)
            )
        } else {
            InventoryList(
                id = 0,
                name = name,
                description = "",
                items = updatedItems,
                color = Color.valueOf(Color.GRAY)
            )
        }
        listsVm.upsertList(list)
    }

    override fun onItemChecked(position: Int, isChecked: Boolean) {
        itemsAdapter.updateItemChecked(position, isChecked)
    }

    override fun onItemRemoveClicked(position: Int) {
        itemsAdapter.removeItemAt(position)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
