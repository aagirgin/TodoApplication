package com.example.todoapp.ui.register

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
import com.example.todoapp.domain.model.ApplicationUser
import com.example.todoapp.domain.state.SignUpError
import com.example.todoapp.domain.state.UiState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
@AndroidEntryPoint
class SignUpFragment : Fragment() {

    private val signUpViewModel: SignUpViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {


        val binding = FragmentSignUpBinding.inflate(inflater, container, false)

        onBackButtonPressed(binding)
        registerUser(binding)
        onPressNavigateSignIn(binding)

        return binding.root
    }

    private fun onPressNavigateSignIn(binding: FragmentSignUpBinding){
        binding.SignInTextButton.setOnClickListener {
            findNavController().navigate(R.id.action_signUpFragment_to_loginFragment)
        }
    }

    private fun mailValidation(mail:String): SignUpError.InvalidEmail? {
        val emailRegex = Regex("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+.[A-Za-z]")
        return if (emailRegex.matches(mail)) null else SignUpError.InvalidEmail
    }

    private suspend fun checkForSameMailAddress(mail: String): SignUpError.EmailExists? {
        val emailExists = signUpViewModel.checkEmailExists(mail)
        return if (emailExists) SignUpError.EmailExists else null
    }
    private fun checkPassword(pass:String, pass2:String): SignUpError.PasswordMismatch? {
        return if (pass == pass2) null else SignUpError.PasswordMismatch
    }

    private fun isBlankItem(mail:String, pass:String, pass2:String, fullName:String): SignUpError.BlankItem? {
        return if (mail.isNotBlank() && pass.isNotBlank() && pass2.isNotBlank() && fullName.isNotBlank())
            null
        else
            SignUpError.BlankItem
    }
    private fun onBackButtonPressed(binding: FragmentSignUpBinding){
        binding.backBtn.setOnClickListener {
            findNavController().popBackStack()
        }
    }
    private fun registerUser(binding: FragmentSignUpBinding) {
        binding.signUpButton.setOnClickListener {

            val mail = binding.enterEmail.text.toString()
            val password = binding.createPassword.text.toString()
            val passwordCheck = binding.confirmPassword.text.toString()
            val fullName = binding.enterFullName.text.toString()

            lifecycleScope.launch {
            val isNotBlank = isBlankItem(mail,password,passwordCheck,fullName)
            val isValidMail = mailValidation(mail)
            val isPassMatch = checkPassword(password,passwordCheck)
            val isMailNotSame = checkForSameMailAddress(mail)

            if (isNotBlank == null && isValidMail == null && isPassMatch == null && isMailNotSame == null) {
                val user = ApplicationUser(
                    mail = mail,
                    fullname = fullName,
                    password = password
                )
                signUpViewModel.signUpUser(user)
                    signUpViewModel.registerState.collect { state ->
                        when(state){
                            is UiState.Loading -> {
                                //TODO
                            }
                            is UiState.Success -> {
                                Toast.makeText(requireContext(),
                                    getString(R.string.success_message), Toast.LENGTH_SHORT).show()
                                println(R.string.success_message.toString())
                                println(R.string.success_message)
                                findNavController().navigate(R.id.action_signUpFragment_to_loginFragment)
                            }
                            is UiState.Error -> {
                                Toast.makeText(requireContext(), state.error, Toast.LENGTH_SHORT).show()
                            }
                            else -> {}
                        }
                    }

                }  else {
                val errorMessage = isNotBlank?.message ?: isValidMail?.message ?: isPassMatch?.message ?: isMailNotSame?.message
                Toast.makeText(requireContext(), errorMessage, Toast.LENGTH_SHORT).show()
                }
            }
        }

    }
}