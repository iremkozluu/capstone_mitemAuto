package com.ikozlu.capstone_mitemauto.ui.searching

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.SearchView
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.ikozlu.capstone_mitemauto.R
import com.ikozlu.capstone_mitemauto.common.invisible
import com.ikozlu.capstone_mitemauto.common.viewBinding
import com.ikozlu.capstone_mitemauto.common.visible
import com.ikozlu.capstone_mitemauto.data.model.ProductUI
import com.ikozlu.capstone_mitemauto.data.model.request.AddToCartRequest
import com.ikozlu.capstone_mitemauto.databinding.FragmentSearchingBinding
import com.ikozlu.capstone_mitemauto.ui.login.AuthViewModel
import com.ikozlu.capstone_mitemauto.ui.main.home.HomeState
import com.ikozlu.capstone_mitemauto.ui.main.home.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchingFragment : Fragment(R.layout.fragment_searching) ,
    SearchProductsAdapter.SearchProductListener {

    private val binding by viewBinding(FragmentSearchingBinding::bind)

    private val viewModel by viewModels<SearchViewModel>()

    private val viewModelHome by viewModels<HomeViewModel>()

    private val viewModelFirebase by viewModels<AuthViewModel>()

    private val searchProductsAdapter by lazy { SearchProductsAdapter(this) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val bottomNavigationView = activity?.findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        bottomNavigationView?.visibility = View.VISIBLE


        with(binding) {

            rvSearch.adapter = searchProductsAdapter

            searchview.setOnQueryTextListener(object : SearchView.OnQueryTextListener,
                androidx.appcompat.widget.SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(p0: String?): Boolean {
                    return false
                }

                override fun onQueryTextChange(p0: String?): Boolean {
                    viewModel.getProductSearch(p0.toString())
                    return false
                }
            })
        }
        observeData()
    }

    private fun observeData() {
        viewModel.searchState.observe(viewLifecycleOwner) { state ->

            when (state) {

                SearchState.Loading -> {
                    binding.progressBar7.visible()
                }

                is SearchState.Data -> {
                    searchProductsAdapter.submitList(state.products)
                    binding.progressBar7.invisible()
                }

                is SearchState.Error -> {
                    Toast.makeText(
                        requireContext(),
                        state.throwable.message.orEmpty(),
                        Toast.LENGTH_SHORT
                    ).show()
                    binding.progressBar7.invisible()
                }
            }

        }

    }

    private fun cartObserver() {

        viewModelHome.homeState.observe(viewLifecycleOwner) {
            if (it is HomeState.PostResponse) {
                binding.progressBar7.invisible()
                Toast.makeText(
                    requireContext(),
                    it.crud.message,
                    Toast.LENGTH_SHORT
                ).show()
            } else if (it is HomeState.Error) {
                binding.progressBar7.invisible()
                Toast.makeText(
                    requireContext(),
                    it.throwable.message.orEmpty(),
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    override fun onProductClick(id: Int) {
        val action = SearchingFragmentDirections.searchingToDetail(id)
        findNavController().navigate(action)
    }

    override fun onCartButtonClick(id: Int) {
        val addToCartRequest = AddToCartRequest(viewModelFirebase.currentUser!!.uid, id)
        viewModelHome.addToCart(addToCartRequest)
        cartObserver()
    }

    override fun onFavButtonClick(product: ProductUI) {
        viewModel.addToFavorites(product)
    }
}