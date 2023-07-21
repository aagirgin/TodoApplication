package com.example.todoapp.ui

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
import com.example.todoapp.databinding.FragmentSignUpBinding
import com.example.todoapp.model.ApplicationUser
import com.example.todoapp.model.UiState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
@AndroidEntryPoint
class SignUpFragment : Fragment() {

    private val signUpViewModel: SignUpViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        val binding = FragmentSignUpBinding.inflate(inflater, container, false)

        registerUser(binding)
        onPressNavigateSignIn(binding)

        return binding.root
    }

    private fun onPressNavigateSignIn(binding: FragmentSignUpBinding){
        binding.SignInTextButton.setOnClickListener {
            findNavController().navigate(R.id.action_signUpFragment_to_loginFragment)
        }
    }

    private fun mailValidation(mail:String):Boolean{
        val emailRegex = Regex("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+.[A-Za-z]")
        return emailRegex.matches(mail)
    }

    private fun checkPassword(pass:String,pass2:String):Boolean{
        return pass == pass2
    }

    private fun isBlankItem(mail:String,pass:String,pass2:String,fullName:String): Boolean{
        return !mail.isNullOrBlank() && !pass.isNullOrBlank() && !pass2.isNullOrBlank() && !fullName.isNullOrBlank()
    }

    private fun registerUser(binding: FragmentSignUpBinding) {
        binding.signUpButton.setOnClickListener {

            val mail = binding.enterEmail.editText?.text.toString()
            val password = binding.createPassword.editText?.text.toString()
            val passwordCheck = binding.confirmPassword.editText?.text.toString()
            val fullName = binding.enterFullName.editText?.text.toString()

            val isNotBlank = isBlankItem(mail,password,passwordCheck,fullName)
            val isValidMail = mailValidation(mail)
            val isPassMatch = checkPassword(password,passwordCheck)

            println(isNotBlank)
            println(isValidMail)
            println(isPassMatch)

            if(isNotBlank && isValidMail && isPassMatch){
                val user = ApplicationUser(
                    mail = mail,
                    fullname = fullName,
                    password = password
                )
                signUpViewModel.signUpUser(user)
                lifecycleScope.launch {
                    signUpViewModel.registerState.collect { state ->
                        when(state){
                            is UiState.Loading -> {
                                //TODO
                            }
                            is UiState.Success -> {
                                Toast.makeText(requireContext(), "Success", Toast.LENGTH_SHORT).show()
                                findNavController().navigate(R.id.action_signUpFragment_to_loginFragment)
                            }
                            is UiState.Error -> {
                                Toast.makeText(requireContext(), state.error, Toast.LENGTH_SHORT).show()
                            }
                            else -> {}
                        }
                    }

                } }else{
                    Toast.makeText(requireContext(), "Please re-check the fields or your password.", Toast.LENGTH_SHORT).show()
                }
            }

    }
}