package com.ikozlu.capstone_mitemauto.data.source.remote


import com.ikozlu.capstone_mitemauto.common.Constants.Endpoint.ADD_TO_CART
import com.ikozlu.capstone_mitemauto.common.Constants.Endpoint.CLEAR_CART
import com.ikozlu.capstone_mitemauto.common.Constants.Endpoint.DELETE_FROM_CART
import com.ikozlu.capstone_mitemauto.common.Constants.Endpoint.GET_ALL_PRODUCTS
import com.ikozlu.capstone_mitemauto.common.Constants.Endpoint.GET_CART_PRODUCTS
import com.ikozlu.capstone_mitemauto.common.Constants.Endpoint.GET_PRODUCT_DETAIL
import com.ikozlu.capstone_mitemauto.common.Constants.Endpoint.GET_SALE_PRODUCTS
import com.ikozlu.capstone_mitemauto.common.Constants.Endpoint.SEARCH_PRODUCT
import com.ikozlu.capstone_mitemauto.data.model.request.AddToCartRequest
import com.ikozlu.capstone_mitemauto.data.model.request.ClearCartRequest
import com.ikozlu.capstone_mitemauto.data.model.request.DeleteFromCartRequest
import com.ikozlu.capstone_mitemauto.data.model.response.BaseResponse
import com.ikozlu.capstone_mitemauto.data.model.response.GetCartProductsResponse
import com.ikozlu.capstone_mitemauto.data.model.response.GetProductDetailResponse
import com.ikozlu.capstone_mitemauto.data.model.response.GetProductResponse
import com.ikozlu.capstone_mitemauto.data.model.response.GetSaleProductResponse
import com.ikozlu.capstone_mitemauto.data.model.response.GetSearchProductResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface ProductService {

    @GET(GET_ALL_PRODUCTS)
    suspend fun getAllProducts(): GetProductResponse

    @GET(GET_PRODUCT_DETAIL)
    suspend fun getProductDetail(
        @Query("id") id: Int
    ): GetProductDetailResponse

    @GET(GET_SALE_PRODUCTS)
    suspend fun getSaleProducts(): GetSaleProductResponse

    @GET(SEARCH_PRODUCT)
    suspend fun getSearchProduct(
        @Query("query") query: String
    ): GetSearchProductResponse

    @POST(ADD_TO_CART)
    suspend fun addToCart(
        @Body addToCartRequest: AddToCartRequest
    ): BaseResponse

    @GET(GET_CART_PRODUCTS)
    suspend fun getCartProducts(
        @Query("userId") userId: String
    ): GetCartProductsResponse

    @POST(DELETE_FROM_CART)
    suspend fun deleteFromCart(
        @Body deleteFromCartRequest: DeleteFromCartRequest
    ): BaseResponse

    @POST(CLEAR_CART)
    suspend fun clearCart(
        @Body clearCartRequest: ClearCartRequest
    ) : BaseResponse

}