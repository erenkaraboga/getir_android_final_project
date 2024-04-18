package com.getir.core.data.repositories

import com.getir.core.data.remote.apiservices.ProductApiService
import com.getir.core.data.remote.models.response.ProductListResponseDto
import com.getir.core.domain.repositories.ProductRepository
import javax.inject.Inject

class ProductRepositoryImpl @Inject constructor(
    private val api: ProductApiService,

    ) :
    ProductRepository {
    override suspend fun getProducts(): ProductListResponseDto {
        return api.getProducts()
    }

    override suspend fun getSuggestedProducts(): ProductListResponseDto {
      return api.getSuggestedProducts()
    }

}
