package com.getir.core.domain.usecases.home


import com.getir.core.R
import com.getir.core.common.utils.UiText
import com.getir.core.common.extentions.handleError
import com.getir.core.common.utils.Resource
import com.getir.core.domain.mappers.ProductMapper
import com.getir.core.domain.models.Product
import com.getir.core.domain.repositories.ProductRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class GetProductsUseCaseImpl @Inject constructor(
    private val repository: ProductRepository,

    private val mapper: ProductMapper
) :
    GetProductsUseCase {
    override suspend fun getProducts(): Flow<Resource<List<Product>>> = flow {
        try {
            emit(Resource.Loading())

            val response = repository.getProducts().first().products

            emit(Resource.Success(data = response.map(mapper::fromDtoToDomain)))
        } catch (e: HttpException) {
            emit(Resource.Error(e.handleError()))
        } catch (e: IOException) {
            emit(Resource.Error(UiText.StringResource(R.string.couldntReachServerError)))
        }
    }

    override suspend fun getSuggestedProducts(): Flow<Resource<List<Product>>> = flow {
        try {
            emit(Resource.Loading())
            val response = repository.getSuggestedProducts().first().products
            val updatedProducts = response.map { product ->
                    product.copy(isSuggestedProduct = true)
            }
            emit(Resource.Success(data = updatedProducts.map(mapper::fromDtoToDomain)))
        } catch (e: HttpException) {
            emit(Resource.Error(e.handleError()))
        } catch (e: IOException) {
            emit(Resource.Error(UiText.StringResource(R.string.couldntReachServerError)))
        }
    }

    companion object {
        val CACHE_EXPIRATION_TIME = TimeUnit.MINUTES.toMillis(5)
    }
}
