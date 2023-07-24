package com.example.todoapp.ui.login

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
import com.example.todoapp.databinding.FragmentLoginBinding
import com.example.todoapp.domain.state.LogInError
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LoginFragment : Fragment() {
    private val loginViewModel: LoginViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentLoginBinding.inflate(inflater,container,false)

        onPressNavigateSignUp(binding)
        onPressLogin(binding)

        return binding.root
    }


    private fun onPressNavigateSignUp(binding: FragmentLoginBinding){
        binding.SignUpTextButton.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_signUpFragment)
        }
    }

    private fun isBlankItem(mail:String, pass:String): LogInError.BlankItem? {
        return if (mail.isNotBlank() && pass.isNotBlank())
            null
        else
            LogInError.BlankItem
    }

    private fun onPressLogin(binding: FragmentLoginBinding) {
        binding.loginButton.setOnClickListener {
            // Disable the login button to prevent multiple clicks during login process
            binding.loginButton.isEnabled = false

            val email = binding.emailField.editText?.text.toString()
            val passwd = binding.password.editText?.text.toString()

            viewLifecycleOwner.lifecycleScope.launch {
                val isNotBlank = isBlankItem(email,passwd)
                if(isNotBlank == null){
                    try {

                        loginViewModel.loginUser(email, passwd)

                        loginViewModel.currentUserState.collect { currentUser ->
                            if (currentUser != null) {
                                findNavController().navigate(R.id.action_loginFragment_to_additionFragment)
                            }else{
                                val errorMessage = LogInError.InvalidCredentials.message
                                Toast.makeText(requireContext(), errorMessage, Toast.LENGTH_SHORT).show()
                            }
                            binding.loginButton.isEnabled = true
                        }
                    } catch (e: Exception) {
                        binding.loginButton.isEnabled = true
                    }
                }
                else{
                    binding.loginButton.isEnabled = true
                    val errorMessage = isNotBlank.message
                    Toast.makeText(requireContext(), errorMessage, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

}