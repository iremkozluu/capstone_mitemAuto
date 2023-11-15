package com.ikozlu.capstone_mitemauto.ui.splash

import android.os.Bundle
import android.os.Handler
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.ikozlu.capstone_mitemauto.R
import com.ikozlu.capstone_mitemauto.common.viewBinding
import com.ikozlu.capstone_mitemauto.databinding.FragmentSplashBinding
import com.ikozlu.capstone_mitemauto.ui.login.AuthViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashFragment : Fragment(R.layout.fragment_splash) {

    private val binding by viewBinding(FragmentSplashBinding::bind)
    private val viewModel by viewModels<AuthViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val bottomNavigationView = activity?.findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        bottomNavigationView?.visibility = View.GONE

        with(binding) {
            Handler().postDelayed(Runnable {
                if (checkCurrentUser()) {
                    findNavController().navigate(SplashFragmentDirections.splashToMain())
                } else {
                    findNavController().navigate(SplashFragmentDirections.splashToSignin())
                }
            }, 3000)

        }
    }

    private fun checkCurrentUser(): Boolean {
        return viewModel.currentUser != null
    }
}
