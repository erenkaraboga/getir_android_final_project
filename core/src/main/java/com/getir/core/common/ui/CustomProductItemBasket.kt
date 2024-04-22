package com.getir.core.common.ui

import android.animation.ArgbEvaluator
import android.animation.ValueAnimator
import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.getir.core.R
import com.getir.core.domain.extensions.getDescription
import com.getir.core.domain.extensions.getImageUrl
import com.getir.core.domain.models.Product
import com.google.android.material.card.MaterialCardView

class CustomProductItemBasket @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    private val cardView: MaterialCardView
    private val imageView: ImageView
    private val priceTextView: TextView
    private val productNameTextView: TextView
    private val attributeTextView: TextView
    private lateinit var listener: CustomProductItemBasketListener
    private lateinit var product: Product
    private val customquantityButtonBasket: CustomQuantityButtonBasket

    init {
        LayoutInflater.from(context).inflate(R.layout.custom_product_item_basket, this, true)
        cardView = findViewById(R.id.cvImage)
        imageView = findViewById(R.id.ivProductImage)
        priceTextView = findViewById(R.id.tvPrice)
        productNameTextView = findViewById(R.id.tvProductName)
        attributeTextView = findViewById(R.id.tvAttribute)
        customquantityButtonBasket = findViewById(R.id.customQuantityButton)

        setupListeners()
    }
    fun setProduct(product: Product){
        this.product = product
        updateProduct()
    }

    fun setListener(listener: CustomProductItemBasketListener) {
        this.listener = listener
    }
    private fun updateProduct() {
        Glide.with(context)
            .load(product.getImageUrl())
            .into(imageView)
        priceTextView.text = product.priceText
        productNameTextView.text = product.name
        attributeTextView.text = product.getDescription()
        customquantityButtonBasket.setQuantity(product.quantity)

    }


    private fun setupListeners() {
        customquantityButtonBasket.setOnQuantityChangeListener(object :
            CustomQuantityButtonBasket.OnQuantityChangeListener {
            override fun onQuantityIncreased(quantity: Int) {
                listener.onQuantityIncreased(quantity, product)

            }

            override fun onQuantityDecreased(quantity: Int) {
                listener.onQuantityDecreased(quantity, product)
            }
        })

        setOnClickListener {
            listener.onProductClicked(product)
        }
    }

}

interface CustomProductItemBasketListener {
    fun onQuantityIncreased(quantity: Int, product: Product)
    fun onQuantityDecreased(quantity: Int, product: Product)
    fun onProductClicked(product: Product)
}