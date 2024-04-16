package com.getir.core.common.ui

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import com.getir.core.R

class CustomQuantityButtonDetail @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    interface OnQuantityChangeListener {
        fun onQuantityIncreased(quantity : Int)
        fun onQuantityDecreased(quantity : Int)
    }

    private var minusButton: ImageView
    private var plusButton: ImageView
    private var quantityTextView: TextView
    private var quantity: Int = 1
    private var quantityChangeListener: OnQuantityChangeListener? = null

    init {
        LayoutInflater.from(context).inflate(R.layout.custom_quantity_button_detail, this, true)

        minusButton = findViewById(R.id.btn_minus)
        plusButton = findViewById(R.id.btn_plus)
        quantityTextView = findViewById(R.id.tvQuantity)
        minusButton.setOnClickListener {
            setQuantity(quantity - 1)
            updateButton()
            quantityChangeListener?.onQuantityDecreased(quantity)
        }

        plusButton.setOnClickListener {
            setQuantity(quantity + 1)
            updateButton()
            quantityChangeListener?.onQuantityIncreased(quantity)
        }
    }



    private fun updateButton() {
        quantityTextView.text = quantity.toString()
        minusButton.setImageResource(if (quantity <= 1) R.drawable.ic_junk_quantity else R.drawable.ic_minus)
    }

    fun setOnQuantityChangeListener(listener: OnQuantityChangeListener) {
        quantityChangeListener = listener
    }

    fun setQuantity(newQuantity: Int) {
        quantity = newQuantity
        updateButton()
    }
}