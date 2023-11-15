package com.ikozlu.capstone_mitemauto.ui.main.profile

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.ikozlu.capstone_mitemauto.R
import com.ikozlu.capstone_mitemauto.common.viewBinding
import com.ikozlu.capstone_mitemauto.databinding.FragmentProfileBinding
import com.ikozlu.capstone_mitemauto.ui.login.AuthViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileFragment : Fragment(R.layout.fragment_profile) {

    private val binding by viewBinding(FragmentProfileBinding::bind)
    private val viewModel by viewModels<AuthViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val bottomNavigationView = activity?.findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        bottomNavigationView?.visibility = View.GONE

        with(binding) {

            toolbar.setNavigationOnClickListener {
                findNavController().navigateUp()
            }

            tvUserEmail.text = viewModel.currentUser?.email.toString()

            btnLogout.setOnClickListener {
                viewModel.logout()
               // findNavController().navigate(ProfileFragmentDirections.profileToLogin())
            }
        }
    }
}