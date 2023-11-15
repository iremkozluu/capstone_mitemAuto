package com.ikozlu.capstone_mitemauto.ui.main.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ikozlu.capstone_mitemauto.common.Resource
import com.ikozlu.capstone_mitemauto.data.model.ProductUI
import com.ikozlu.capstone_mitemauto.data.model.request.AddToCartRequest
import com.ikozlu.capstone_mitemauto.data.model.response.BaseResponse
import com.ikozlu.capstone_mitemauto.data.repository.ProductRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val productRepository: ProductRepository) :
    ViewModel() {

    private var _homeState = MutableLiveData<HomeState>()
    val homeState: LiveData<HomeState>
        get() = _homeState


    fun getAllProducts() {
        viewModelScope.launch {

            _homeState.value = HomeState.Loading

            val result = productRepository.getAllProducts()

            if (result is Resource.Success) {
                _homeState.value = HomeState.ProductList(result.data)
            } else if (result is Resource.Error) {
                _homeState.value = HomeState.Error(result.throwable)
            }

        }
    }

    fun getSaleProducts() {
        viewModelScope.launch {

            _homeState.value = HomeState.Loading

            val result = productRepository.getSaleProducts()

            if (result is Resource.Success) {
                _homeState.value = HomeState.SaleProductList(result.data)
                result.data.map {

                }

            } else if (result is Resource.Error) {
                _homeState.value = HomeState.Error(result.throwable)
            }

        }
    }

    fun addToCart(addToCartRequest: AddToCartRequest) {
        viewModelScope.launch {

            _homeState.value = HomeState.Loading

            val result = productRepository.addToCart(addToCartRequest)

            if (result is Resource.Success) {
                _homeState.value = HomeState.PostResponse(result.data)
            } else if (result is Resource.Error) {
                _homeState.value = HomeState.Error(result.throwable)
            }

        }
    }

    fun addToFavorite(product: ProductUI) {
        viewModelScope.launch {
            productRepository.addToFavorites(product)
        }
    }

    fun removeFromFavorite(product: ProductUI) {
        viewModelScope.launch {
            productRepository.removeFromFavorites(product)
        }
    }

    fun getFavoriteProducts() {
        viewModelScope.launch {
            productRepository.getFavoriteProducts()
        }
    }

}

sealed interface HomeState {
    object Loading : HomeState
    data class ProductList(val products: List<ProductUI>) : HomeState
    data class SaleProductList(val saleProducts: List<ProductUI>) : HomeState
    data class PostResponse(val crud: BaseResponse) : HomeState
    data class Error(val throwable: Throwable) : HomeState
}