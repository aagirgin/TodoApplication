package com.example.todoapp.ui.onboarding

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController

import com.example.todoapp.R
import com.example.todoapp.databinding.FragmentLoginBinding
import com.example.todoapp.databinding.FragmentOnBoardBinding

class OnBoardFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentOnBoardBinding.inflate(inflater,container,false)

        onPressNavigate(binding)

        return binding.root
    }

    private fun onPressNavigate(binding: FragmentOnBoardBinding){
        binding.containedButton.setOnClickListener {
            findNavController().navigate(R.id.action_onBoardFragment_to_loginFragment)
        }
    }
}