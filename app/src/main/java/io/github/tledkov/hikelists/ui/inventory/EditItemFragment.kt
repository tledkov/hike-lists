package io.github.tledkov.hikelists.ui.inventory

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.core.view.isInvisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import io.github.tledkov.hikelists.App
import io.github.tledkov.hikelists.R
import io.github.tledkov.hikelists.databinding.FragmentItemEditBinding
import io.github.tledkov.hikelists.domain.Category
import io.github.tledkov.hikelists.domain.InventoryItem
import io.github.tledkov.hikelists.domain.Weight
import java.lang.Exception

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

        val adapter: ArrayAdapter<Category> = ArrayAdapter(
            this.requireContext(),
            androidx.transition.R.layout.support_simple_spinner_dropdown_item,
            inventoryVm.categories
        )

        adapter.setDropDownViewResource(androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
        binding.editItemCategorySpin.adapter = adapter

        fillFormWithItem(this.requireContext(), inventoryVm.editingItem)

        val popStack: (View) -> Unit = { it.findNavController().popBackStack() }
        binding.itemEditBackBtn.setOnClickListener(popStack)
        binding.itemEditCancelBtn.setOnClickListener(popStack)

        binding.editItemSaveBtn.setOnClickListener(::save)
        binding.editItemDeleteBtn.setOnClickListener(::delete)

        return root
    }

    fun delete(v: View) {
        val id = inventoryVm.editingItem!!.id
        inventoryVm.deleteItem(id)

        val popStack: (View) -> Unit = { it.findNavController().popBackStack() }
        popStack(v)
    }

    fun save(v: View) {
        val popStack: (View) -> Unit = { it.findNavController().popBackStack() }
        val weight: Weight?

        try {
            weight = Weight.from(Integer.parseInt(binding.editItemEditWeight.text.toString()))
        } catch (ex: Exception) {
            val builder = AlertDialog.Builder(v.context)
            val message: String =
                if (binding.editItemEditWeight.text.isNullOrEmpty()) {
                    requireContext().resources.getString(R.string.alert_invalid_weight_empty)
                } else {
                    requireContext().resources.getString(R.string.alert_invalid_weight_invalid) + binding.editItemEditWeight.text.toString()
                }
            builder.setTitle(R.string.alert_invalid_weight_title)
                .setMessage(message)
                .setIcon(R.drawable.baseline_announcement_24)
                .setPositiveButton("ОК") { dialog, id ->
                    dialog.cancel()
                }
            val alert = builder.create()

            alert.show()

            return
        }

        val item = InventoryItem(
            id = inventoryVm.editingItem?.id ?: 0,
            category = binding.editItemCategorySpin.selectedItem as Category,
            weight = weight,
            name = binding.editItemEditItemName.text.toString(),
            description = binding.editItemEditNotes.text.toString(),
            img = ""
        )
        inventoryVm.upsertItem(item)
        popStack(v)
    }

    private fun fillFormWithItem(context: Context, item: InventoryItem?) {
        binding.editItemEditItemName.text.clear()
        binding.editItemEditNotes.text.clear()
        binding.editItemEditWeight.text.clear()

        if (item == null) {
            binding.itemEditTextTitle.text =
                context.resources.getString(R.string.edit_item_title_new)
            binding.editItemDeleteBtn.visibility = View.INVISIBLE
        } else {
            binding.itemEditTextTitle.text =
                context.resources.getString(R.string.edit_item_title_edit)
            binding.editItemDeleteBtn.visibility = View.VISIBLE

            binding.editItemEditItemName.text.append(item.name)
            binding.editItemEditNotes.text.append(item.description)
            binding.editItemEditWeight.text.append(item.weight.value().toString())
            binding.editItemCategorySpin.setSelection(inventoryVm.categories.indexOf(item.category))
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}