package com.getir.core.domain.repositories

import com.getir.core.data.remote.models.response.ProductListResponseDto


interface ProductRepository {
    suspend fun getProducts(): ProductListResponseDto
}
