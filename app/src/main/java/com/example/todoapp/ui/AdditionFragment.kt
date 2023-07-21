package com.example.todoapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.todoapp.R
import com.example.todoapp.databinding.FragmentAdditionBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class AdditionFragment : Fragment() {
    private val additionViewModel: AdditionViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentAdditionBinding.inflate(inflater,container,false)

        addListOnClick(binding)

        return binding.root
    }



    private fun addListOnClick(binding: FragmentAdditionBinding){
        binding.addlistBtn.setOnClickListener {
            val text1 = binding.addition1.editText?.text.toString()
            val text2 = binding.addition2.editText?.text.toString()
            val text3 = binding.addition3.editText?.text.toString()

            lifecycleScope.launch {
                if (!text1.isBlank()) additionViewModel.addItemIntoUser(text1)
                if (!text2.isBlank()) additionViewModel.addItemIntoUser(text2)
                if (!text3.isBlank()) additionViewModel.addItemIntoUser(text3)

                findNavController().navigate(R.id.action_additionFragment_to_todoPageFragment)
            }
        }
    }
}