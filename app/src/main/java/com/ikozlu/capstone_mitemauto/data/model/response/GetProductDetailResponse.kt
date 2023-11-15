package com.ikozlu.capstone_mitemauto.data.model.response


import com.ikozlu.capstone_mitemauto.data.model.Product

data class GetProductDetailResponse(
    val message: String?,
    val product: Product,
    val status: Int?
)