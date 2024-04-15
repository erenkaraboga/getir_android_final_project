package com.getir.product_list

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.net.toUri
import androidx.fragment.app.activityViewModels
import androidx.navigation.NavDeepLinkRequest
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.getir.core.SharedViewModel
import com.getir.core.common.constants.ToolBarType
import com.getir.core.common.extentions.addHorizontalDecoration
import com.getir.core.common.extentions.addSimpleVerticalDecoration
import com.getir.core.common.extentions.observeInLifecycle
import com.getir.core.common.ui.CustomQuantityButtonDetail
import com.getir.core.common.ui.CustomQuantityButtonList
import com.getir.core.common.utils.UiText
import com.getir.core.domain.models.Product

import com.getir.product_list.databinding.FragmentProductListBinding


import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProductListFragment : Fragment() {
    private val sharedViewModel: SharedViewModel by activityViewModels()
    private lateinit var binding: FragmentProductListBinding

    private lateinit var adapter: ProductListAdapter
    private lateinit var suggestedAdapter: ProductListAdapter
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
        setupObservers()
        sharedViewModel.getProducts()
        binding.rvProduct.isNestedScrollingEnabled = false
    }

    private fun setupObservers() {
        sharedViewModel.getViewState().observeInLifecycle(lifecycle, ::handleStateChange)
    }

    private fun handleStateChange(state: SharedViewModel.ProductViewState) {
        when (state) {
            is SharedViewModel.ProductViewState.Init -> Unit
            is SharedViewModel.ProductViewState.Loading -> handleLoading(state.isLoading)
            is SharedViewModel.ProductViewState.Success -> handleSuccess(state.data)
            is SharedViewModel.ProductViewState.SuccessWithEmptyData -> Unit
            is SharedViewModel.ProductViewState.Error -> handleError(state.error)
        }
    }

    private fun handleSuccess(list: List<Product>){
       adapter.setItems(list)
        suggestedAdapter.setItems(list)
    }

    private fun handleLoading(loading: Boolean) {
        //binding.progressBar.isVisible = loading
    }
    private fun setUpProductList() {
        binding.rvProduct.layoutManager = GridLayoutManager(
          requireContext(),3
        )
        adapter = ProductListAdapter()
        binding.rvProduct.adapter = adapter
    }
    private fun setUpSuggestedProductList() {
        binding.rvSuggestedProduct.layoutManager = LinearLayoutManager(
            requireContext(),
            RecyclerView.HORIZONTAL,
            false
        )
        binding.rvSuggestedProduct.addHorizontalDecoration(
            space = 0,
            startSpace = 20,
            endSpace = 20
        )
        suggestedAdapter = ProductListAdapter()
        binding.rvSuggestedProduct.adapter = suggestedAdapter
    }

    private fun handleError(error: UiText) =
        Toast.makeText(requireContext(), error.asString(requireContext()), Toast.LENGTH_SHORT).show()

}