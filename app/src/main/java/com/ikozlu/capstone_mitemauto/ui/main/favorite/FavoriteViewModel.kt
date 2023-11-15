package com.ikozlu.capstone_mitemauto.ui.main.favorite

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ikozlu.capstone_mitemauto.common.Resource
import com.ikozlu.capstone_mitemauto.data.model.ProductUI
import com.ikozlu.capstone_mitemauto.data.repository.ProductRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject constructor(private val productRepository: ProductRepository) :
    ViewModel() {

    private var _favoriteState = MutableLiveData<FavoriteState>()
    val favoriteState: LiveData<FavoriteState>
        get() = _favoriteState


    fun getFavoriteProducts() {
        viewModelScope.launch {

            _favoriteState.value = FavoriteState.Loading

            val result = productRepository.getFavoriteProducts()
            if (result is Resource.Success) {
                _favoriteState.value = FavoriteState.Data(result.data)
            } else if (result is Resource.Error) {
                _favoriteState.value = FavoriteState.Error(result.throwable)
            }
        }
    }

    fun removeFromFavorites(product: ProductUI) {
        viewModelScope.launch {
            productRepository.removeFromFavorites(product)
            getFavoriteProducts()
        }
    }



}

sealed interface FavoriteState {
    object Loading : FavoriteState
    data class Data(val products: List<ProductUI>) : FavoriteState
    data class Error(val throwable: Throwable) : FavoriteState
}