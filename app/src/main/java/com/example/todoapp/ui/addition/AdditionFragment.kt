package com.example.todoapp.ui.addition

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.todoapp.R
import com.example.todoapp.databinding.FragmentAdditionBinding
import com.example.todoapp.domain.state.UiState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class AdditionFragment : Fragment() {
    private val additionViewModel: AdditionViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentAdditionBinding.inflate(inflater,container,false)

        onBackButtonPressed(binding)
        addListOnClick(binding)

        return binding.root
    }

    private fun addListOnClick(binding: FragmentAdditionBinding){
        binding.buttonAddtolist.setOnClickListener {
            val text1 = binding.textinputAddition1.editText?.text.toString()
            val text2 = binding.textinputAddition2.editText?.text.toString()
            val text3 = binding.textinputAddition3.editText?.text.toString()

            viewLifecycleOwner.lifecycleScope.launch {
                if (text1.isNotBlank()) additionViewModel.addItemIntoUser(text1)
                if (text2.isNotBlank()) additionViewModel.addItemIntoUser(text2)
                if (text3.isNotBlank()) additionViewModel.addItemIntoUser(text3)
                additionViewModel.additionState.collect{ state->
                    when(state){
                        is UiState.Empty -> findNavController().navigate(R.id.action_additionFragment_to_todoPageFragment)
                        is UiState.Success -> findNavController().navigate(R.id.action_additionFragment_to_todoPageFragment)
                        is UiState.Error -> Toast.makeText(requireContext(), state.error, Toast.LENGTH_SHORT).show()
                        else -> {}
                    }
                }
            }
        }
    }
    private fun onBackButtonPressed(binding: FragmentAdditionBinding){
        binding.imageviewBackbutton.setOnClickListener {
            findNavController().popBackStack()
        }
    }
}

