package com.getir.product_list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.net.toUri
import androidx.core.view.isVisible
import androidx.core.widget.NestedScrollView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.NavDeepLinkRequest
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.getir.basket.ProductItemListener
import com.getir.core.SharedViewModel
import com.getir.core.common.constants.ToolBarType
import com.getir.core.common.extentions.addHorizontalDecoration
import com.getir.core.common.extentions.observeInLifecycle
import com.getir.core.common.utils.UiText
import com.getir.core.domain.models.Product
import com.getir.product_list.databinding.FragmentProductListBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProductListFragment : Fragment() {
    private val sharedViewModel: SharedViewModel by activityViewModels()
    private lateinit var binding: FragmentProductListBinding

    private lateinit var productList: RecyclerView
    private lateinit var suggestedProductList: RecyclerView
    private lateinit var productAdapter: ProductListAdapter
    private lateinit var suggestedAdapter: ProductSuggestedListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProductListBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sharedViewModel.setTopBar(ToolBarType.PRODUCT_LIST)
        setUpProductList()
        setUpSuggestedProductList()
        listeners()
        setupObservers()
        init()
        binding.rvProduct.isNestedScrollingEnabled = false
    }

    private fun setupObservers() {
        sharedViewModel.getViewState().observeInLifecycle(lifecycle, ::handleStateChange)
        sharedViewModel.getSuggestedProductViewState().observeInLifecycle(lifecycle, ::handleStateChangeSuggested)
    }

    private fun handleStateChange(state: SharedViewModel.ProductViewState) {
        when (state) {
            is SharedViewModel.ProductViewState.Success -> handleSuccess(state.data)
            is SharedViewModel.ProductViewState.Error -> handleError(state.error)
            is SharedViewModel.ProductViewState.Loading -> handleLoading(state.isLoading)
            else -> Unit
        }
    }
    private fun handleStateChangeSuggested(state: SharedViewModel.SuggestedProductViewState) {
        when (state) {
            is SharedViewModel.SuggestedProductViewState.Success -> handleSuccessSuggested(state.data)
            is SharedViewModel.SuggestedProductViewState.Error -> handleError(state.error)
            is SharedViewModel.SuggestedProductViewState.Loading -> handleLoadingSuggested(state.isLoading)
            else -> Unit
        }
    }

    private fun init(){
        sharedViewModel.cartItems.value?.takeIf { it.isEmpty() }?.run {
            sharedViewModel.getProducts()
            sharedViewModel.getSuggestedProducts()
        }
    }
    private fun handleSuccess(list: List<Product>) {
        sharedViewModel.productItems.value?.takeIf { it.isEmpty() }?.run {
            sharedViewModel.setCartItems(list)
            productAdapter.setItems(list)
        }
    }
    private fun handleSuccessSuggested(list: List<Product>) {
        sharedViewModel.suggestedProductItems.value?.takeIf { it.isEmpty() }?.run {
            sharedViewModel.setCartItems(list)
            suggestedAdapter.setItems(list)
        }
    }

    private fun listeners(){
        binding.nestedScroll.setOnScrollChangeListener(NestedScrollView.OnScrollChangeListener { _, _, scrollY, _, _ ->
            sharedViewModel.updateScrollPosition(scrollY)
        })
    }
    private fun handleLoading(loading: Boolean) {
        if(loading){
            binding.rvProduct.visibility  = View.GONE
            binding.shimmerProductList.visibility = View.VISIBLE
            binding.shimmerProductList.startShimmer()

            binding.rvSuggestedProduct.visibility = View.GONE
            binding.shimmerSuggested.visibility = View.VISIBLE
            binding.shimmerSuggested.startShimmer()

        }
        else{
            binding.shimmerProductList.visibility = View.GONE
            binding.shimmerProductList.stopShimmer()
            binding.rvProduct.visibility  = View.VISIBLE

            binding.shimmerSuggested.visibility = View.GONE
            binding.shimmerSuggested.stopShimmer()
            binding.rvSuggestedProduct.visibility = View.VISIBLE

        }
    }
    private fun handleLoadingSuggested(loading: Boolean) {


    }

    private fun setUpProductList() {
        productList = binding.rvProduct
        productList.layoutManager = GridLayoutManager(requireContext(), 3)
        productAdapter = createProductListAdapter()
        productList.adapter = productAdapter
    }

    private fun setUpSuggestedProductList() {
        suggestedProductList = binding.rvSuggestedProduct
        suggestedProductList.layoutManager = LinearLayoutManager(
            requireContext(),
            RecyclerView.HORIZONTAL,
            false
        )
        suggestedProductList.addHorizontalDecoration(space = 0, startSpace = 20, endSpace = 20)
        suggestedAdapter = createSuggestedProductListAdapter()
        suggestedProductList.adapter = suggestedAdapter
    }

    override fun onResume() {
        //to refresh recyclerviews for change and scroll older position

        sharedViewModel.productItems.value?.let { productAdapter.setItems(it) }
        sharedViewModel.suggestedProductItems.value?.let { suggestedAdapter.setItems(it) }
        binding.nestedScroll.post {
            binding.nestedScroll.scrollTo(0, sharedViewModel.scrollPosition)
        }
        super.onResume()
    }

    private fun createProductListAdapter(): ProductListAdapter {
        return ProductListAdapter(requireContext(),object : ProductItemListener {
            override fun onProductClicked(product: Product) {
                sharedViewModel.setSelectedProduct(product)

                val deepLinkUri = "android-app://example.google.app/fragment_product_detail".toUri()
                val request = NavDeepLinkRequest.Builder.fromUri(deepLinkUri).build()
                findNavController().navigate(request)
            }

            override fun onProductDecreased(quantity: Int, product: Product) {
                sharedViewModel.removeFromCart(product)
            }

            override fun onProductIncreased(quantity: Int, product: Product) {
                sharedViewModel.addToCart(product)
            }
        })
    }
    private fun createSuggestedProductListAdapter(): ProductSuggestedListAdapter {
        return ProductSuggestedListAdapter(requireContext(),object : ProductSuggestedItemListener {
            override fun onProductClicked(product: Product) {
                sharedViewModel.setSelectedProduct(product)
                val deepLinkUri = "android-app://example.google.app/fragment_product_detail".toUri()
                val request = NavDeepLinkRequest.Builder.fromUri(deepLinkUri).build()
                findNavController().navigate(request)
            }

            override fun onProductDecreased(quantity: Int, product: Product) {
                sharedViewModel.removeFromCart(product)
            }

            override fun onProductIncreased(quantity: Int, product: Product) {
                sharedViewModel.addToCart(product)
            }
        })
    }

    private fun handleError(error: UiText) {
        Toast.makeText(requireContext(), error.asString(requireContext()), Toast.LENGTH_SHORT).show()
    }
}
