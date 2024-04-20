package com.getir.core.use_cases.view_model

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.getir.core.SharedViewModel
import com.getir.core.common.utils.Resource
import com.getir.core.common.utils.UiText
import com.getir.core.domain.usecases.home.GetProductsUseCase
import com.getir.core.utils.MockHelper
import com.google.common.truth.Truth.assertThat

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.whenever

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class HomeViewModelTest {
    @get:Rule

    val rule = InstantTaskExecutorRule()
    private val errorMessage: String = "error"

    @Mock
    private lateinit var getProductsUseCase: GetProductsUseCase

    private lateinit var viewModel: SharedViewModel

    @Before
    fun setUp() {
        Dispatchers.setMain(UnconfinedTestDispatcher())
        viewModel = SharedViewModel(getProductsUseCase)
    }

    @Test
    fun `getProductsUseCase emits success`() = runTest {
        whenever(getProductsUseCase.getProducts()).thenAnswer {
            flow { emit(Resource.Success(data = MockHelper.productListResponseDto)) }
        }
        viewModel.getProducts()
        val currentState = viewModel.getViewState()
        assertThat(currentState.value).isInstanceOf(SharedViewModel.ProductViewState.Success::class.java)
    }



    @Test
    fun `getProductsUseCase emits error`() = runTest {
        whenever(getProductsUseCase.getProducts()).thenAnswer {
            flow<Resource<Any>> {
                emit(
                    Resource.Error(message = UiText.DynamicString(value = errorMessage))
                )
            }
        }
        viewModel.getProducts()
        val currentState = viewModel.getViewState()
        assertThat(currentState.value).isInstanceOf(SharedViewModel.ProductViewState.Error::class.java)
    }

    @Test
    fun `getProducts emits loading`() = runTest {
        whenever(getProductsUseCase.getProducts()).thenAnswer {
            flow<Resource<Any>> { emit(Resource.Loading()) }
        }
        viewModel.getProducts()
        val currentState = viewModel.getViewState()
        assertThat(currentState.value).isInstanceOf(SharedViewModel.ProductViewState.Loading::class.java)
    }


    @Test
    fun `verify setLoading function called with isLoading=true `() = runTest {
        viewModel.setLoading(true)
        val currentState = viewModel.getViewState()
        assertThat(currentState.value).isInstanceOf(SharedViewModel.ProductViewState.Loading::class.java)
        val loadingState = currentState.value as SharedViewModel.ProductViewState.Loading
        assertThat(loadingState.isLoading).isEqualTo(true)
    }

    @Test
    fun `verify setLoading function called with isLoading=false `() = runTest {
        viewModel.setLoading(false)
        val currentState = viewModel.getViewState()
        assertThat(currentState.value).isInstanceOf(SharedViewModel.ProductViewState.Loading::class.java)
        val loadingState = currentState.value as SharedViewModel.ProductViewState.Loading
        assertThat(loadingState.isLoading).isEqualTo(false)
    }

    @After
    fun tearDown() = Dispatchers.resetMain()
}
