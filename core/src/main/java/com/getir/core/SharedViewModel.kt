package com.getir.core

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.getir.core.domain.models.Product
import com.getir.core.domain.usecases.home.GetProductsUseCase

import com.getir.core.common.constants.ToolBarType
import com.getir.core.common.utils.Resource
import com.getir.core.common.utils.UiText
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SharedViewModel @Inject constructor(private val getProductsUseCase: GetProductsUseCase) :
    ViewModel() {
    private val _productState = MutableStateFlow<ProductViewState>(ProductViewState.Init)
    fun getViewState(): StateFlow<ProductViewState> = _productState.asStateFlow()

    fun setLoading(isLoading: Boolean) {
        _productState.value = ProductViewState.Loading(isLoading)
    }

    val cartItems = mutableListOf<Product>()

    private val _toolBarType = MutableLiveData<ToolBarType>()
    val toolBarType: LiveData<ToolBarType> get() = _toolBarType

    private val _cartAmount = MutableLiveData(0)
    val cartAmount: LiveData<Int> get() = _cartAmount

    fun setTopBar(type: ToolBarType) {
        _toolBarType.value = type
    }

    fun setCartAmount(amount: Int) {
        _cartAmount.value = amount
    }

    fun setCartAmountNegative(amount: Int) {
        val currentAmount = _cartAmount.value ?: 0
        _cartAmount.value = currentAmount - amount
    }

    fun shouldNavigateToBasketFragment(): Boolean {
        val cartAmount = cartAmount.value ?: 0
        return cartAmount > 0
    }

    fun getProducts() {
        if (_productState.value is ProductViewState.Success) return

        viewModelScope.launch {
            getProductsUseCase.getProducts().collectLatest { result ->
                when (result) {
                    is Resource.Error -> {
                        setLoading(false)
                        _productState.value = ProductViewState.Error(result.message)
                    }

                    is Resource.Loading -> setLoading(true)
                    is Resource.Success -> {
                        setLoading(false)
                        if (result.data.isNullOrEmpty()) {
                            _productState.value = ProductViewState.SuccessWithEmptyData
                        } else {
                            _productState.value = ProductViewState.Success(result.data)
                        }
                    }
                }
            }
        }
    }

    sealed class ProductViewState {
        object Init : ProductViewState()
        data class Loading(val isLoading: Boolean) : ProductViewState()
        data class Success(val data: List<Product>) : ProductViewState()
        object SuccessWithEmptyData : ProductViewState()
        data class Error(val error: UiText) : ProductViewState()
    }
}
