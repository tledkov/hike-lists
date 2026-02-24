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
import io.github.tledkov.hikelists.App
import io.github.tledkov.hikelists.databinding.ListMainEditBinding
import io.github.tledkov.hikelists.domain.InventoryList

class EditListFragment : Fragment() {

    private val args: EditListFragmentArgs by navArgs()

    private val listsVm: ListsViewModel by activityViewModels {
        ListsViewModelFactory(
            requireActivity().application,
            (requireActivity().application as App).inventoryListRepository,
        )
    }

    private var _binding: ListMainEditBinding? = null
    private val binding get() = _binding!!

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
        val existingList = listsVm.getListById(listId)

        if (existingList != null) {
            binding.listMainEditTextTitle.text = "Edit List"
            binding.editListMainListMainEditName.setText(existingList.name)
            binding.listMainEditDeleteBtn.visibility = View.VISIBLE
        } else {
            binding.listMainEditTextTitle.text = "New List"
            binding.listMainEditDeleteBtn.visibility = View.INVISIBLE
        }

        binding.listMainEditBackBtn.setOnClickListener {
            it.findNavController().popBackStack()
        }

        binding.listMainEditCancelBtn.setOnClickListener {
            it.findNavController().popBackStack()
        }

        binding.listMainEditSaveBtn.setOnClickListener {
            saveList()
            it.findNavController().popBackStack()
        }

        binding.listMainEditDeleteBtn.setOnClickListener {
            existingList?.let { list ->
                listsVm.deleteList(list)
            }
            it.findNavController().popBackStack()
        }

        binding.listMainEditSaveBtn.isEnabled = false
        binding.editListMainListMainEditName.doAfterTextChanged { text ->
            binding.listMainEditSaveBtn.isEnabled = !text.isNullOrBlank()
        }
    }

    private fun saveList() {
        val name = binding.editListMainListMainEditName.text.toString().trim()
        val listId = args.listId

        if (listId != null) {
            // Edit existing list
            listsVm.getListById(listId)?.let { existingList ->
                val updatedList = existingList.copy(name = name)
                listsVm.upsertList(updatedList)
            }
        } else {
            // Create new list
            val newList = InventoryList(
                id = 0,
                name = name,
                description = "",
                items = mutableListOf(),
                color = Color.valueOf(Color.GRAY)
            )
            listsVm.upsertList(newList)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
