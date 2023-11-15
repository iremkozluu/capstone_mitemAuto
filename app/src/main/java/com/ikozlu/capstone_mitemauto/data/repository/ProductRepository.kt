package com.ikozlu.capstone_mitemauto.data.repository

import com.ikozlu.capstone_mitemauto.common.Resource
import com.ikozlu.capstone_mitemauto.data.mapper.mapToProductEntity
import com.ikozlu.capstone_mitemauto.data.mapper.mapToProductUI
import com.ikozlu.capstone_mitemauto.data.model.ProductUI
import com.ikozlu.capstone_mitemauto.data.model.request.AddToCartRequest
import com.ikozlu.capstone_mitemauto.data.model.request.ClearCartRequest
import com.ikozlu.capstone_mitemauto.data.model.request.DeleteFromCartRequest
import com.ikozlu.capstone_mitemauto.data.model.response.BaseResponse
import com.ikozlu.capstone_mitemauto.data.source.local.ProductDao
import com.ikozlu.capstone_mitemauto.data.source.remote.ProductService
import com.google.firebase.auth.FirebaseAuth


class ProductRepository(
    private val productService: ProductService,
    private val productDao: ProductDao
) {

    suspend fun getProductDetail(id: Int): Resource<ProductUI> {
        return try {

            val getFavoriteIds = getFavoriteIds()
            val result = productService.getProductDetail(id).product
            result.let {
                Resource.Success(result.mapToProductUI(isFavorite = getFavoriteIds.contains(result.id)))
            }
        } catch (e: Exception) {
            Resource.Error(e)
        }
    }

    suspend fun getAllProducts(): Resource<List<ProductUI>> {
        return try {
            val getFavoriteIds = getFavoriteIds()
            val result = productService.getAllProducts().products.orEmpty()

            if (result.isEmpty()) {
                Resource.Error(Exception("Products not found"))
            } else {
                Resource.Success(result.map {
                    it.mapToProductUI(isFavorite = getFavoriteIds.contains(it.id))
                })
            }

        } catch (e: Exception) {
            Resource.Error(e)
        }
    }

    suspend fun getSaleProducts(): Resource<List<ProductUI>> {
        return try {
            val getFavoriteIds = getFavoriteIds()
            val result = productService.getSaleProducts().products.orEmpty()

            if (result.isEmpty()) {
                Resource.Error(Exception("Products not found"))
            } else {
                Resource.Success(result.map {
                    it.mapToProductUI(isFavorite = getFavoriteIds.contains(it.id))
                })
            }

        } catch (e: Exception) {
            Resource.Error(e)
        }
    }

    suspend fun getSearchProducts(query: String): Resource<List<ProductUI>> {
        return try {
            val getFavoriteIds = getFavoriteIds()
            val result = productService.getSearchProduct(query).products
            result?.let {
                Resource.Success(result.map {
                    it.mapToProductUI(isFavorite = getFavoriteIds.contains(it.id))
                })
            } ?: kotlin.run {
                Resource.Error(Exception("Sorry, products not found."))
            }
        } catch (e: Exception) {
            Resource.Error(e)
        }
    }

    suspend fun addToCart(addToCartRequest: AddToCartRequest): Resource<BaseResponse> {
        return try {
            val result = productService.addToCart(addToCartRequest)
            if (result.status == 200) {
                Resource.Success(result)
            } else {
                Resource.Error(Exception("Product not added"))
            }
        } catch (e: Exception) {
            Resource.Error(e)
        }
    }

    suspend fun getCartProducts(userId: String): Resource<List<ProductUI>> {
        return try {
            val getFavoriteIds = getFavoriteIds()
            val result = productService.getCartProducts(userId)

            if (result.status == 200) {
                Resource.Success(result.products.orEmpty().map {
                    it.mapToProductUI(isFavorite = getFavoriteIds.contains(it.id))
                })
            } else {
                Resource.Error(Exception("Your cart is empty. Let's add something."))
            }
        } catch (e: Exception) {
            Resource.Error(e)
        }
    }

    suspend fun deleteFromCart(deleteFromCartRequest: DeleteFromCartRequest): Resource<BaseResponse> {
        return try {
            val result = productService.deleteFromCart(deleteFromCartRequest)
            if (result.status == 200) {
                Resource.Success(result)
            } else {
                Resource.Error(Exception("Product not deleted"))
            }

        } catch (e: Exception) {
            Resource.Error(e)
        }
    }

    suspend fun clearCart(clearCartRequest: ClearCartRequest): Resource<BaseResponse> {

        return try {
            val result = productService.clearCart(clearCartRequest)
            if (result.status == 200) {
                Resource.Success(result)
            } else {
                Resource.Error(Exception("Cart not Cleared !"))
            }
        } catch (e: Exception) {
            Resource.Error(e)
        }
        getCartProducts(FirebaseAuth.getInstance().currentUser!!.uid)
    }


    suspend fun addToFavorites(product: ProductUI) {
        productDao.addToFavorites(product.mapToProductEntity())
    }

    suspend fun removeFromFavorites(product: ProductUI) {
        productDao.removeFromFavorites(product.mapToProductEntity())
    }

    suspend fun getFavoriteProducts(): Resource<List<ProductUI>> {
        return try {
            val result = productDao.getFavoriteProducts().map { it.mapToProductUI() }
            if (result.isEmpty()) {
                Resource.Error(Exception("Your favorites are empty, let's favorite something."))
            } else {
                Resource.Success(result)
            }
        } catch (e: Exception) {
            Resource.Error(e)
        }
    }

    suspend fun getFavoriteIds() = productDao.getFavoriteIds()



}
