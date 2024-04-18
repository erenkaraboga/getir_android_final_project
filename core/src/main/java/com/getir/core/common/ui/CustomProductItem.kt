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
import com.getir.core.domain.models.Product
import com.google.android.material.card.MaterialCardView

class CustomProductItem @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    private val cardView: MaterialCardView
    private val imageView: ImageView
    private val priceTextView: TextView
    private val productNameTextView: TextView
    private val attributeTextView: TextView
    private lateinit var listener: CustomProductItemListener
    private lateinit var product: Product
    private val customQuantityButtonList: CustomQuantityButtonList
    val primaryColor = ContextCompat.getColor(context, R.color.color_primary)
    val borderColor = ContextCompat.getColor(context, R.color.product_item_border)
    init {
        LayoutInflater.from(context).inflate(R.layout.custom_product_item, this, true)
        cardView = findViewById(R.id.cvImage)
        imageView = findViewById(R.id.ivProductImage)
        priceTextView = findViewById(R.id.tvPrice)
        productNameTextView = findViewById(R.id.tvProductName)
        attributeTextView = findViewById(R.id.tvAttribute)
        customQuantityButtonList = findViewById(R.id.customQuantityButton)

        setupListeners()
    }
    fun setProduct(product: Product){
        this.product = product
        updateProduct()
    }

    fun setListener(listener: CustomProductItemListener) {
        this.listener = listener
    }
    private fun updateProduct() {
        Glide.with(context)
            .load(product.imageURL)
            .into(imageView)
        priceTextView.text = product.priceText
        productNameTextView.text = product.name
        attributeTextView.text = product.attribute
        customQuantityButtonList.setQuantity(product.quantity)
        cardView.strokeColor = if (product.quantity > 0) primaryColor else borderColor
    }


    private fun setupListeners() {
        customQuantityButtonList.setOnQuantityChangeListener(object :
            CustomQuantityButtonList.OnQuantityChangeListener {
            override fun onQuantityIncreased(quantity: Int) {
                listener.onQuantityIncreased(quantity, product)

                animateBorderColorChange(if (quantity > 0) primaryColor else borderColor)
            }

            override fun onQuantityDecreased(quantity: Int) {
                listener.onQuantityDecreased(quantity, product)
                animateBorderColorChange(if (quantity == 0) borderColor else primaryColor)
            }
        })

        setOnClickListener {
            listener.onProductClicked(product)
        }
    }
    private fun animateBorderColorChange(targetColor: Int) {
        val colorAnimation = ValueAnimator.ofObject(ArgbEvaluator(), cardView.strokeColor, targetColor)
        colorAnimation.duration = 600
        colorAnimation.addUpdateListener { animator ->
            cardView.strokeColor = animator.animatedValue as Int
        }
        colorAnimation.start()
    }
}

interface CustomProductItemListener {
    fun onQuantityIncreased(quantity: Int, product: Product)
    fun onQuantityDecreased(quantity: Int, product: Product)
    fun onProductClicked(product: Product)
}