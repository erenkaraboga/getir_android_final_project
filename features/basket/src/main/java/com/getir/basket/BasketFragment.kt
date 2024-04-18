package com.getir.basket

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.NavDeepLinkRequest
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.getir.basket.databinding.FragmentBasketBinding
import com.getir.core.SharedViewModel
import com.getir.core.common.constants.ToolBarType
import com.getir.core.common.extentions.addHorizontalDecoration
import com.getir.core.common.extentions.addSimpleVerticalDecoration
import com.getir.core.domain.extensions.getImageUrl
import com.getir.core.domain.models.Product
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class BasketFragment : Fragment() {
    private val sharedViewModel: SharedViewModel by activityViewModels()

    private lateinit var binding: FragmentBasketBinding
    private lateinit var productAdapter: BasketListAdapter
    private lateinit var productList: RecyclerView
    private lateinit var suggestedAdapter: ProductSuggestedListAdapter
    private lateinit var suggestedProductList: RecyclerView


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentBasketBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        sharedViewModel.setTopBar(ToolBarType.BASKET)
        super.onViewCreated(view, savedInstanceState)
        setUpProductList()
        setUpSuggestedProductList()
        setObservers()
        sharedViewModel.basketItems.value?.let { productAdapter.setItems(it) }
        sharedViewModel.suggestedProductItems.value?.let { suggestedAdapter.setItems(it) }
    }

    private fun setObservers(){
        sharedViewModel.basketItems.observe(viewLifecycleOwner) { list ->
            list?.let {
              productAdapter.setItems(list)
            }
        }
        sharedViewModel.suggestedProductItems.observe(viewLifecycleOwner) { list ->
            list?.let {
                suggestedAdapter.setItems(list)
            }
        }
    }
    private fun setUpProductList() {
        productList = binding.rvProduct
        productList.layoutManager = LinearLayoutManager(
            requireContext(),
            RecyclerView.VERTICAL,
            false
        )

        productList.addSimpleVerticalDecoration(
            12,
            includeFirstItem = true,
            includeLastItem = false

        )
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

    private fun createProductListAdapter(): BasketListAdapter {
        return BasketListAdapter(requireContext(),object : ProductItemListener {
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

}