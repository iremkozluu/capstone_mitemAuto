package com.ikozlu.capstone_mitemauto.data.model.response


import com.ikozlu.capstone_mitemauto.data.model.Product

data class GetCartProductsResponse(
    val message: String?,
    val products: List<Product>?,
    val status: Int?
)