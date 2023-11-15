package com.ikozlu.capstone_mitemauto.ui.main.paymentsuccess

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.ikozlu.capstone_mitemauto.R
import com.ikozlu.capstone_mitemauto.common.visible
import com.ikozlu.capstone_mitemauto.data.model.request.ClearCartRequest
import com.ikozlu.capstone_mitemauto.databinding.FragmentPaymentSuccessBinding
import com.ikozlu.capstone_mitemauto.ui.main.cart.CartState
import com.ikozlu.capstone_mitemauto.ui.main.cart.CartViewModel
import com.google.firebase.auth.FirebaseAuth
import com.ikozlu.capstone_mitemauto.common.invisible
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PaymentSuccessFragment : Fragment(R.layout.fragment_payment_success) {

    private lateinit var binding: FragmentPaymentSuccessBinding
    private val viewModel by viewModels<CartViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPaymentSuccessBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val bottomNavigationView = activity?.findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        bottomNavigationView?.visibility = View.GONE

        val req = ClearCartRequest(FirebaseAuth.getInstance().currentUser!!.uid)
        viewModel.clearCart(req)

        observers()

        with(binding) {
            button.setOnClickListener {
                findNavController().navigate(
                    R.id.homeFragment,
                    null,
                    NavOptions.Builder()
                        .setPopUpTo(R.id.cartFragment, true)
                        .build()
                )

            }
        }
    }

    private fun observers() {
        viewModel.cartState.observe(viewLifecycleOwner) { state ->
            when (state) {
                CartState.Loading -> {
                    binding.progressBar3.visible()
                }

                is CartState.PostResponse -> {
                    binding.progressBar3.invisible()
                    Toast.makeText(
                        requireContext(),
                        state.crud.message,
                        Toast.LENGTH_SHORT
                    ).show()
                }

                is CartState.Error -> {
                    Toast.makeText(
                        requireContext(),
                        state.throwable.message.toString(),
                        Toast.LENGTH_SHORT
                    ).show()
                    binding.progressBar3.visible()
                }

                else -> {

                }
            }

        }
    }
}