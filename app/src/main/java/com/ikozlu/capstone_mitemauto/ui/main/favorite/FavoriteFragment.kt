package com.ikozlu.capstone_mitemauto.ui.main.favorite

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.ikozlu.capstone_mitemauto.R
import com.ikozlu.capstone_mitemauto.common.invisible
import com.ikozlu.capstone_mitemauto.common.visible
import com.ikozlu.capstone_mitemauto.data.model.ProductUI
import com.ikozlu.capstone_mitemauto.databinding.FragmentFavoriteBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class FavoriteFragment : Fragment(R.layout.fragment_favorite),
    FavoriteProductsAdapter.ProductListener {

    private lateinit var binding: FragmentFavoriteBinding
    private val viewModel by viewModels<FavoriteViewModel>()
    private val favoriteProductsAdapter by lazy { FavoriteProductsAdapter(this) }



    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFavoriteBinding.inflate(inflater, container, false)
        return binding.root
        val bottomNavigationView = activity?.findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        bottomNavigationView?.visibility = View.VISIBLE

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val bottomNavigationView = activity?.findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        bottomNavigationView?.visibility = View.VISIBLE

        viewModel.getFavoriteProducts()

        with(binding) {
            rvFavorites.adapter = favoriteProductsAdapter
        }

        observeData()
    }

    private fun observeData() = with(binding) {
        viewModel.favoriteState.observe(viewLifecycleOwner) { state ->

            when (state) {

                FavoriteState.Loading -> {
                    progressBar5.visible()
                }

                is FavoriteState.Data -> {
                    binding.rvFavorites.visible()
                    favoriteProductsAdapter.submitList(state.products)
                    progressBar5.invisible()
                }

                is FavoriteState.Error -> {
                    binding.rvFavorites.invisible()
                    Toast.makeText(
                        requireContext(),
                        state.throwable.message.toString(),
                        Toast.LENGTH_SHORT
                    ).show()
                    progressBar5.invisible()
                }
            }

        }
    }

    override fun onProductClick(id: Int) {
        val action = FavoriteFragmentDirections.favoriteToDetail(id)
        findNavController().navigate(action)
    }

    override fun onFavButtonClick(product: ProductUI) {
        viewModel.removeFromFavorites(product)
    }
}