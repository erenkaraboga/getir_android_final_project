package com.getir.core.domain.usecases.home



import com.getir.core.common.utils.Resource
import com.getir.core.domain.models.Product
import kotlinx.coroutines.flow.Flow

interface GetProductsUseCase {
    suspend fun getProducts(): Flow<Resource<List<Product>>>
    suspend fun getSuggestedProducts(): Flow<Resource<List<Product>>>
}
