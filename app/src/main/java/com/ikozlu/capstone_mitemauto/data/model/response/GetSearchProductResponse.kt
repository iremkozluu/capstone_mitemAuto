package com.ikozlu.capstone_mitemauto.data.model.response


import com.ikozlu.capstone_mitemauto.data.model.Product

data class GetSearchProductResponse(
    val message: String?,
    val products: List<Product>?,
    val status: Int?
)