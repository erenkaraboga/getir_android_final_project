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

    private val _suggestedProductState = MutableStateFlow<SuggestedProductViewState>(SuggestedProductViewState.Init)
    fun getSuggestedProductViewState(): StateFlow<SuggestedProductViewState> = _suggestedProductState.asStateFlow()


    private val _selectedProduct = MutableLiveData<Product>()
    val selectedProduct: LiveData<Product> get() = _selectedProduct


    private val _toolBarType = MutableLiveData<ToolBarType>()
    val toolBarType: LiveData<ToolBarType> get() = _toolBarType


    private val _cartAmount = MutableLiveData(0.0)
    val cartAmount: LiveData<Double> get() = _cartAmount

    var scrollPosition: Int = 0

    private val _isOrdered = MutableLiveData<Boolean>()
    val isOrdered: LiveData<Boolean> get() = _isOrdered


    private val _cartItems = MutableLiveData<MutableList<Product>>()
    val cartItems: LiveData<MutableList<Product>> = _cartItems


    private val _productItems = MutableLiveData<MutableList<Product>>()
    val productItems: LiveData<MutableList<Product>> = _productItems


    private val _suggestedProductItems = MutableLiveData<MutableList<Product>>()
    val suggestedProductItems: LiveData<MutableList<Product>> = _suggestedProductItems

    private val _basketItems = MutableLiveData<MutableList<Product>>()
    val basketItems: LiveData<MutableList<Product>> = _basketItems

    init {
        _cartItems.value = mutableListOf()
        _productItems.value = mutableListOf()
        _suggestedProductItems.value = mutableListOf()
        _basketItems.value = mutableListOf()
        _isOrdered.value = false
    }
    fun updateScrollPosition(position: Int) {
        scrollPosition = position
    }
    fun setSelectedProduct(product: Product) {
        _selectedProduct.value = product
    }

    fun addToCart(product: Product) {
        val currentCartItems = _cartItems.value ?: mutableListOf()
        val existingProductIndex = currentCartItems.indexOfFirst { it.id == product.id }
            val existingProduct = currentCartItems[existingProductIndex]
            val updatedProduct = existingProduct.copy(quantity = existingProduct.quantity + 1)
            currentCartItems[existingProductIndex] = updatedProduct
        _cartItems.value = currentCartItems
        calculateTotal()
        updateProductLists()
    }

    fun removeFromCart(product: Product) {
        val currentCartItems = _cartItems.value ?: mutableListOf()
        val productIndex = currentCartItems.indexOfFirst { it.id == product.id }

            val existingProduct = currentCartItems[productIndex]
            val updatedProduct = existingProduct.copy(quantity = existingProduct.quantity - 1)
            currentCartItems[productIndex] = updatedProduct
            _cartItems.value = currentCartItems
            calculateTotal()
            updateProductLists()
    }

    private fun updateProductLists() {
        val cartList = _cartItems.value ?: mutableListOf()
        val productList = mutableListOf<Product>()
        val suggestedProductList = mutableListOf<Product>()
        for (product in cartList) {
            if (product.isSuggestedItem) {
                suggestedProductList.add(product)
            } else {
                productList.add(product)
            }
        }
        _productItems.value = productList
        _suggestedProductItems.value = suggestedProductList
        getBasketItems()

    }

    private fun getBasketItems()  {
        val basketItems = mutableListOf<Product>()
        _cartItems.value?.forEach { product ->
            if (product.quantity > 0) {
                basketItems.add(product)
            }
            _basketItems.value = basketItems
        }
    }

    fun setCartItems(items: List<Product>) {
        val currentItems = _cartItems.value ?: mutableListOf()
        currentItems.addAll(items)
        _cartItems.value = currentItems
        updateProductLists()
    }

    private fun calculateTotal() {
        val currentCartItems = _cartItems.value ?: return
        var total = 0.0
        for (item in currentCartItems) {
            total += item.price * item.quantity
        }
        _cartAmount.value = total
    }

    fun setTopBar(type: ToolBarType) {
        _toolBarType.value = type
    }

    fun setIsOrdered(ordered: Boolean) {
        _isOrdered.value = ordered
        _isOrdered.value=false
    }

    fun setLoading(isLoading: Boolean) {
        _productState.value = ProductViewState.Loading(isLoading)

    }
    private fun setLoadingSuggested(isLoading: Boolean) {
        _suggestedProductState.value = SuggestedProductViewState.Loading(isLoading)
    }

    fun canNavigateBasket(): Boolean {
        val cartAmount = _cartAmount.value
        return cartAmount != null && cartAmount > 0
    }

    fun isDeleteButtonActive(): Boolean {
        val cartAmount = _cartAmount.value
        return cartAmount != null && cartAmount > 0
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
    fun getSuggestedProducts() {
        if (_suggestedProductState.value is SuggestedProductViewState.Success) return
        viewModelScope.launch {
            getProductsUseCase.getSuggestedProducts().collectLatest { result ->
                when (result) {
                    is Resource.Error -> {
                        setLoadingSuggested(false)
                        _suggestedProductState.value = SuggestedProductViewState.Error(result.message)
                    }
                    is Resource.Loading -> setLoadingSuggested(true)
                    is Resource.Success -> {
                        setLoadingSuggested(false)
                        if (result.data.isNullOrEmpty()) {
                            _suggestedProductState.value = SuggestedProductViewState.SuccessWithEmptyData
                        } else {
                            _suggestedProductState.value = SuggestedProductViewState.Success(result.data)
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
    sealed class SuggestedProductViewState {
        object Init : SuggestedProductViewState()
        data class Loading(val isLoading: Boolean) : SuggestedProductViewState()
        data class Success(val data: List<Product>) : SuggestedProductViewState()
        object SuccessWithEmptyData : SuggestedProductViewState()
        data class Error(val error: UiText) : SuggestedProductViewState()
    }
}
