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
import com.example.todoapp.data.DatabaseService
import com.example.todoapp.databinding.FragmentAdditionBinding
import kotlinx.coroutines.launch


class AdditionFragment : Fragment() {
    private val additionFragment: AdditionViewModel by viewModels()
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
                if(!text1.isBlank()) DatabaseService.addActivityElement(text1)
                if(!text2.isBlank()) DatabaseService.addActivityElement(text2)
                if(!text3.isBlank()) DatabaseService.addActivityElement(text3)

                findNavController().navigate(R.id.action_additionFragment_to_todoPageFragment)
                println(DatabaseService.getCurrentUser())
            }
        }
    }
}