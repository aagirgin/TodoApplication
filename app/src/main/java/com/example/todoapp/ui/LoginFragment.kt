package com.example.todoapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewModelScope
import androidx.navigation.fragment.findNavController

import com.example.todoapp.R
import com.example.todoapp.data.DatabaseService
import com.example.todoapp.data.UserDatabase
import com.example.todoapp.databinding.FragmentLoginBinding
import com.example.todoapp.model.UiState
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch


class LoginFragment : Fragment() {
    private val loginViewModel: LoginViewModel by viewModels()
    private val databaseService = DatabaseService
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentLoginBinding.inflate(inflater,container,false)

        databaseService.init(requireContext())

        onPressNavigateSignUp(binding)
        onPressLogin(binding)

        return binding.root
    }



    private fun onPressNavigateSignUp(binding: FragmentLoginBinding){
        binding.SignUpTextButton.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_signUpFragment)
        }
    }

    private fun onPressLogin(binding: FragmentLoginBinding){
        binding.loginButton.setOnClickListener {
            val email = binding.emailField.editText?.text.toString()
            val passwd = binding.password.editText?.text.toString()

            lifecycleScope.launch {
                loginViewModel.loginUser(email,passwd)
                if(DatabaseService.getCurrentUser()?.id != null){
                    findNavController().navigate(R.id.action_loginFragment_to_additionFragment)
                }

            }

        }
    }

}