package io.github.tledkov.hikelists.ui.lists

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import io.github.tledkov.hikelists.App
import io.github.tledkov.hikelists.databinding.FragmentListsBinding
import io.github.tledkov.hikelists.ui.inventory.InventoryViewModel
import io.github.tledkov.hikelists.ui.inventory.InventoryViewModelFactory

class ListsFragment : Fragment() {

    private val listsVm: ListsViewModel by activityViewModels {
        ListsViewModelFactory(
            requireActivity().application,
            (requireActivity().application as App).inventoryListRepository,
        )
    }

    private var _binding: FragmentListsBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentListsBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.listsIdleMessageText
        listsVm.text.observe(viewLifecycleOwner) {
            textView.text = it
        }

        listsVm.listLd.observe(viewLifecycleOwner) {lists ->
            println()
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}