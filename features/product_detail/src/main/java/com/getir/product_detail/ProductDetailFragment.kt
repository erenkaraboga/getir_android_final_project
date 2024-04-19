package com.getir.product_detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.bumptech.glide.Glide
import com.getir.core.SharedViewModel
import com.getir.core.common.constants.ToolBarType
import com.getir.core.common.ui.CustomOrderButton
import com.getir.core.common.ui.CustomQuantityButtonDetail
import com.getir.core.domain.extensions.getImageUrl
import com.getir.core.domain.models.Product
import com.getir.product_detail.databinding.FragmentProductDetailBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProductDetailFragment : Fragment() {
    private val sharedViewModel: SharedViewModel by activityViewModels()
    private lateinit var product: Product
    private lateinit var binding: FragmentProductDetailBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentProductDetailBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeViewModels()
        setListeners()
    }

    private fun observeViewModels() {
        sharedViewModel.cartItems.observe(viewLifecycleOwner) { cartItems ->
            val existingProduct = cartItems.find { it.id == sharedViewModel.selectedProduct.value?.id }
            existingProduct?.let {
                binding.customQuantityButton.setQuantity(it.quantity)
                if (it.quantity > 0) {
                    showBasketUI()
                } else {
                    showRegularUI()
                }
            } ?: showBasketUI()
        }

        sharedViewModel.selectedProduct.observe(viewLifecycleOwner) { selectedProduct ->
            selectedProduct?.let {
                product = it
                binding.tvProductName.text = it.name
                binding.tvAttribute.text = it.attribute
                binding.tvPrice.text = it.priceText
                Glide.with(this)
                    .load(product.getImageUrl())
                    .into(binding.ivProductImage)
            }
        }
    }

    private fun showBasketUI() {
        sharedViewModel.setTopBar(ToolBarType.PRODUCT_DETAIL_BASKET)
        binding.customQuantityButton.visibility = View.VISIBLE
        binding.btnCart.visibility = View.GONE
    }

    private fun showRegularUI() {
        sharedViewModel.setTopBar(ToolBarType.PRODUCT_DETAIL)
        binding.customQuantityButton.visibility = View.GONE
        binding.btnCart.visibility = View.VISIBLE
    }

    private fun setListeners() {
        binding.customQuantityButton.setOnQuantityChangeListener(object : CustomQuantityButtonDetail.OnQuantityChangeListener {
            override fun onQuantityIncreased(quantity: Int) {
                sharedViewModel.addToCart(product)
            }

            override fun onQuantityDecreased(quantity: Int) {
                sharedViewModel.removeFromCart(product)
            }
        })
        binding.btnCart.setButtonClickListener(object: CustomOrderButton.ButtonClickedListener{
            override fun onButtonClicked() {
                sharedViewModel.addToCart(product)
                binding.customQuantityButton.setQuantity(1)
            }

        })
    }
}