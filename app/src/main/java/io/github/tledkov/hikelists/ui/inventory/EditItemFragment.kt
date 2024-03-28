package io.github.tledkov.hikelists.ui.inventory

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import io.github.tledkov.hikelists.databinding.FragmentItemEditBinding

class EditItemFragment : Fragment() {

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

        val popStack: (View) -> Unit = { it.findNavController().popBackStack() }
        binding.itemEditCancelBtn.setOnClickListener(popStack)
        binding.itemEditBackBtn.setOnClickListener(popStack)

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}