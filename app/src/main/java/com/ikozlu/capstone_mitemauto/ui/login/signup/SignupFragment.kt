package com.ikozlu.capstone_mitemauto.ui.login.signup

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.ikozlu.capstone_mitemauto.R
import com.ikozlu.capstone_mitemauto.common.invisible
import com.ikozlu.capstone_mitemauto.common.isValidEmail
import com.ikozlu.capstone_mitemauto.common.isValidPassword
import com.ikozlu.capstone_mitemauto.common.viewBinding
import com.ikozlu.capstone_mitemauto.common.visible
import com.ikozlu.capstone_mitemauto.databinding.FragmentSignupBinding
import com.ikozlu.capstone_mitemauto.ui.login.AuthState
import com.ikozlu.capstone_mitemauto.ui.login.AuthViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignupFragment : Fragment(R.layout.fragment_signup) {

    private val binding by viewBinding(FragmentSignupBinding::bind)
    private val viewModel by viewModels<AuthViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val bottomNavigationView = activity?.findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        bottomNavigationView?.visibility = View.GONE

        observeData()

        with(binding) {
            btnSignUp2.setOnClickListener {
                if (checkInfos(etEmailSignUp.text.toString(), etPasswordSignUp.text.toString())) {
                    viewModel.signup(etEmailSignUp.text.toString(), etPasswordSignUp.text.toString())
                }
            }

            tvToSignin2.setOnClickListener {
                findNavController().navigate(SignupFragmentDirections.signupToSignin())
            }
        }
    }

    private fun observeData() = with(binding) {

        viewModel.authState.observe(viewLifecycleOwner) { state ->

            when (state) {
                AuthState.Loading -> {
                    binding.progressBar6.visible()
                }

                is AuthState.Data -> {
                    binding.progressBar6.invisible()
                    findNavController().navigate(SignupFragmentDirections.signupToMain())
                }

                is AuthState.Error -> {
                    binding.progressBar6.invisible()
                    Toast.makeText(
                        requireContext(),
                        state.throwable.message.orEmpty(),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

    private fun checkInfos(email: String, password: String): Boolean {

        val checkInfos = when {
            email.isValidEmail(email).not() -> false
            password.isValidPassword(password).not() -> false
            else -> true
        }
        return checkInfos
    }
}