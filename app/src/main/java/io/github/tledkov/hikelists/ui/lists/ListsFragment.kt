package io.github.tledkov.hikelists.ui.lists

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import io.github.tledkov.hikelists.App
import io.github.tledkov.hikelists.R
import io.github.tledkov.hikelists.databinding.FragmentListsBinding
import io.github.tledkov.hikelists.domain.InventoryList

class ListsFragment : Fragment(), ListViewHolder.OnItemClickListener {

    private val listsVm: ListsViewModel by activityViewModels {
        ListsViewModelFactory(
            requireActivity().application,
            (requireActivity().application as App).inventoryListRepository,
        )
    }

    private var _binding: FragmentListsBinding? = null

    private val binding get() = _binding!!

    private lateinit var listAdapter: ListAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentListsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initRecyclerView()

        listsVm.listLd.observe(viewLifecycleOwner) { lists ->
            listAdapter.setLists(lists)
            updateIdleMessage(lists)
        }

        binding.listsToolbarAddListBtn.setOnClickListener {
            val action = ListsFragmentDirections.actionNavigationListsToEditListFragment()
            it.findNavController().navigate(action)
        }
    }

    private fun initRecyclerView() {
        listAdapter = ListAdapter(this)

        with(binding.listsList) {
            layoutManager = LinearLayoutManager(context)
            adapter = listAdapter
            setHasFixedSize(true)
        }
    }

    private fun updateIdleMessage(lists: List<InventoryList>) {
        binding.listsIdleMessageText.visibility = if (lists.isEmpty()) {
            View.VISIBLE
        } else {
            View.GONE
        }
    }

    override fun onItemClicked(view: View, item: InventoryList) {
        val action = ListsFragmentDirections.actionNavigationListsToEditListFragment(item.id)
        view.findNavController().navigate(action)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}