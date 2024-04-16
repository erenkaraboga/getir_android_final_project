package com.getir.product_detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.getir.core.SharedViewModel
import com.getir.core.common.constants.ToolBarType
import com.getir.core.common.ui.CustomQuantityButtonDetail
import com.getir.product_detail.databinding.FragmentProductDetailBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProductDetailFragment : Fragment() {
    private val sharedViewModel: SharedViewModel by activityViewModels()
    private lateinit var binding: FragmentProductDetailBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentProductDetailBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeCartAmount()
        setListeners()
    }

    private fun observeCartAmount() {
        sharedViewModel.cartAmount.observe(viewLifecycleOwner) { quantity ->
            //binding.customQuantityButton.setQuantity(quantity)
            if (quantity > 0) {
                showBasketUI()
            } else {
                showRegularUI()
            }
        }
    }


    private fun showBasketUI() {
        sharedViewModel.setTopBar(ToolBarType.PRODUCT_DETAIL_BASKET)
        binding.customQuantityButton.visibility = View.VISIBLE
        binding.btnNegative.visibility = View.GONE
    }

    private fun showRegularUI() {
        sharedViewModel.setTopBar(ToolBarType.PRODUCT_DETAIL)
        binding.customQuantityButton.visibility = View.GONE
        binding.btnNegative.visibility = View.VISIBLE
    }

    private fun setListeners() {
        binding.customQuantityButton.setOnQuantityChangeListener(object : CustomQuantityButtonDetail.OnQuantityChangeListener {
            override fun onQuantityIncreased() {

            }

            override fun onQuantityDecreased() {

            }

        })

    }
}

