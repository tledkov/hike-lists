package io.github.tledkov.hikelists.ui.inventory

import android.R
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import io.github.tledkov.hikelists.App
import io.github.tledkov.hikelists.databinding.FragmentItemEditBinding
import io.github.tledkov.hikelists.domain.InventoryItem
import io.github.tledkov.hikelists.domain.Weight


class EditItemFragment : Fragment() {

    private val inventoryVm: InventoryViewModel by activityViewModels {
        InventoryViewModelFactory(
            requireActivity().application,
            (requireActivity().application as App).categoryRepository,
            (requireActivity().application as App).inventoryItemRepository,
        )
    }

    private var _binding: FragmentItemEditBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentItemEditBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val adapter: ArrayAdapter<String> = ArrayAdapter<String>(
            this.requireContext(),
            R.layout.simple_spinner_item,
            inventoryVm.categories.map { it.name }.toList()
        )
        adapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_item);
        binding.editItemCategorySpin.adapter = adapter

        val popStack: (View) -> Unit = { it.findNavController().popBackStack() }
        binding.itemEditBackBtn.setOnClickListener(popStack)
        binding.itemEditCancelBtn.setOnClickListener(popStack)
        binding.editItemSaveBtn.setOnClickListener {
            val item = InventoryItem(
                id = 0,
                category = null,
                weight = Weight.from(100),
                name = binding.editItemEditItemName.text.toString(),
                description = binding.editItemEditNotes.text.toString(),
                img = ""
            )
            inventoryVm.upsertItem(item)
            popStack(it)
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}