package com.getir.basket

import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.NavDeepLinkRequest
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.getir.basket.databinding.FragmentBasketBinding
import com.getir.core.SharedViewModel
import com.getir.core.common.constants.NavigationRoute
import com.getir.core.common.constants.ToolBarType
import com.getir.core.common.extentions.addHorizontalDecoration
import com.getir.core.common.extentions.addSimpleVerticalDecoration
import com.getir.core.common.extentions.twoDigit
import com.getir.core.common.ui.CustomOrderButton
import com.getir.core.common.utils.AlertDialogListener
import com.getir.core.common.utils.DialogHelper
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
    private lateinit var cartItems: List<Product>
    private  var cartAmount:Double = 0.0


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
        setListeners()
        sharedViewModel.basketItems.value?.let { productAdapter.setItems(it) }
        sharedViewModel.suggestedProductItems.value?.let { suggestedAdapter.setItems(it) }
    }

    private fun setObservers() {
        sharedViewModel.basketItems.observe(viewLifecycleOwner) { list ->
            list?.let {
                productAdapter.setItems(list)
                cartItems = list
            }
        }
        sharedViewModel.suggestedProductItems.observe(viewLifecycleOwner) { list ->
            list?.let { suggestedAdapter.setItems(list) }
        }
        sharedViewModel.cartAmount.observe(viewLifecycleOwner) { amount ->
            amount?.let { binding.btnCart.setAmount(it)
                cartAmount = amount
            }
        }
    }

    private fun setListeners() {
        binding.btnCart.setButtonClickListener(object : CustomOrderButton.ButtonClickedListener {
            override fun onButtonClicked() {
                if (cartItems.isEmpty()){
                    Toast.makeText(context, getString(R.string.select_product), Toast.LENGTH_SHORT).show()
                }else{
                    showDialog()
                }
            }
        })
    }

    private fun setUpProductList() {
        productList = binding.rvProduct
        productList.layoutManager = LinearLayoutManager(
            requireContext(),
            RecyclerView.VERTICAL,
            false
        )
        productList.addSimpleVerticalDecoration(
            18,
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
        suggestedProductList.addHorizontalDecoration(space = 10, startSpace = 20, endSpace = 20)
        suggestedAdapter = createSuggestedProductListAdapter()
        suggestedProductList.adapter = suggestedAdapter
    }

    private fun createSuggestedProductListAdapter(): ProductSuggestedListAdapter {
        return ProductSuggestedListAdapter(requireContext(), object : ProductSuggestedItemListener {
            override fun onProductClicked(product: Product) {
                sharedViewModel.setSelectedProduct(product)
                navigateToProductDetail()
            }

            override fun onProductDecreased(quantity: Int, product: Product) {
                sharedViewModel.removeFromCart(product)
            }

            override fun onProductIncreased(quantity: Int, product: Product) {
                binding.nestedScroll.post { binding.nestedScroll.fullScroll(View.FOCUS_DOWN) }
                sharedViewModel.addToCart(product)
            }
        })
    }

    private fun createProductListAdapter(): BasketListAdapter {
        return BasketListAdapter(requireContext(), object : ProductItemListener {
            override fun onProductClicked(product: Product) {
                sharedViewModel.setSelectedProduct(product)
                navigateToProductDetail()
            }

            override fun onProductDecreased(quantity: Int, product: Product) {
                sharedViewModel.removeFromCart(product)
            }

            override fun onProductIncreased(quantity: Int, product: Product) {
                sharedViewModel.addToCart(product)
            }
        })
    }

    private fun navigateToProductDetail() {
        val navOptions = NavOptions.Builder()
            .setEnterAnim(com.getir.core.R.anim.slide_in)
            .setPopEnterAnim(com.getir.core.R.anim.fade_in)
            .build()
        val deepLinkUri = NavigationRoute.PRODUCT_DETAIL.toUri()
        val request = NavDeepLinkRequest.Builder.fromUri(deepLinkUri).build()
        findNavController().navigate(request, navOptions)
    }

    private fun showDialog() {
        DialogHelper.showDialog(
            context = context,
            object : AlertDialogListener {
                override fun onPositiveClicked(dialog: DialogInterface) {
                    Toast.makeText(context,
                        getString(R.string.order_approved, cartAmount.twoDigit), Toast.LENGTH_LONG).show()
                        sharedViewModel.setIsOrdered(true)
                }

                override fun onNegativeClicked(dialog: DialogInterface) {
                    dialog.dismiss()
                    dialog.cancel()
                }
            },
            getString(R.string.are_you_approve_order)
        )
    }

}