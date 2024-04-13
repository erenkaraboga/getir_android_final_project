package com.getir.core.data.remote.models.response

import com.getir.core.data.remote.models.response.ProductDto

data class ProductListDto(
    val id: String,
    val name: String,
    val productCount: Int,
    val products: List<ProductDto>
)