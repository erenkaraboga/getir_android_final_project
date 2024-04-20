package com.getir.core.use_cases.products

import com.getir.core.common.utils.Resource
import com.getir.core.common.utils.UiText
import com.getir.core.domain.mappers.ProductMapper
import com.getir.core.domain.repositories.ProductRepository
import com.getir.core.domain.usecases.home.GetProductsUseCase
import com.getir.core.domain.usecases.home.GetProductsUseCaseImpl
import com.getir.core.utils.MockHelper
import com.google.common.truth.Truth.assertThat

import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.whenever

@RunWith(MockitoJUnitRunner::class)
class GetProductsUseCaseTest {
    private lateinit var getProductsUseCase: GetProductsUseCase

    @Mock
    private lateinit var productRepository: ProductRepository

    private  var mapper: ProductMapper = ProductMapper()
    
    @Before
    fun setUp() {
        getProductsUseCase = GetProductsUseCaseImpl(
            productRepository,
            mapper
        )
    }

    @Test
    fun `check getProducts() success case`() = runBlocking {
        // when
        whenever(productRepository.getProducts()).thenAnswer { MockHelper.productListResponseDto }
        val result = getProductsUseCase.getProducts()
        val flowList = result.toList()
        // then
        assertThat(flowList[0]).isInstanceOf(Resource.Loading::class.java)
        assertThat(flowList[1]).isInstanceOf(Resource.Success::class.java)
    }

    @Test
    fun `check getProducts() http exception error case`() = runBlocking {
        // when
        whenever(productRepository.getProducts()).thenAnswer { throw MockHelper.getHttpException() }
        val result = getProductsUseCase.getProducts()
        val flowList = result.toList()
        // then
        assertThat(flowList[0]).isInstanceOf(Resource.Loading::class.java)
        assertThat(flowList[1]).isInstanceOf(Resource.Error::class.java)
        assertThat(flowList[1].message).isInstanceOf(UiText.DynamicString::class.java)
    }

    @Test
    fun `check getProducts() io exception error case`() = runBlocking {
        // when
        whenever(productRepository.getProducts()).thenAnswer { throw MockHelper.ioException }
        val result = getProductsUseCase.getProducts()
        val flowList = result.toList()
        // then

        assertThat(flowList[0]).isInstanceOf(Resource.Loading::class.java)
        assertThat(flowList[1]).isInstanceOf(Resource.Error::class.java)
        assertThat(flowList[1].message).isInstanceOf(UiText.StringResource::class.java)
    }
}
