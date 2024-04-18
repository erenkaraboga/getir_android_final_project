package com.getir.core.data.remote.apiservices

import com.getir.core.data.remote.models.response.ProductListResponseDto
import retrofit2.http.GET

interface ProductApiService {
    @GET("/api/products")
    suspend fun getProducts() : ProductListResponseDto

    @GET("/api/suggestedProducts")
    suspend fun getSuggestedProducts() : ProductListResponseDto
    
}
