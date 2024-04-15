package com.getir.core.common.ui

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.animation.TranslateAnimation
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import com.getir.core.R
import com.google.android.material.card.MaterialCardView

class CustomQuantityButtonList @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    interface OnQuantityChangeListener {
        fun onQuantityIncreased()
        fun onQuantityDecreased()
    }

    private var minusButton: ImageView
    private var plusButton: ImageView
    private var quantityTextView: TextView
    private var quantityCard: MaterialCardView
    private var quantity: Int = 0
    private var quantityChangeListener: OnQuantityChangeListener? = null

    init {
        LayoutInflater.from(context).inflate(R.layout.custom_quantity_button_list, this, true)

        minusButton = findViewById(R.id.btn_minus)
        plusButton = findViewById(R.id.btn_plus)
        quantityTextView = findViewById(R.id.tvQuantity)
        quantityCard = findViewById(R.id.cvQuantity)
        minusButton.setOnClickListener {
            quantityChangeListener?.onQuantityDecreased()
        }

        plusButton.setOnClickListener {
            quantityChangeListener?.onQuantityIncreased()
        }
        updateButton()
    }



    private fun updateButton() {
        quantityTextView.text = quantity.toString()
        if(quantity<=0){
            minusButton.visibility = View.GONE
            quantityCard.visibility =View.GONE

        }
        else{
            minusButton.visibility = View.VISIBLE
            quantityCard.visibility =View.VISIBLE
            minusButton.setImageResource(if (quantity <= 1) R.drawable.ic_junk_quantity else R.drawable.ic_minus)
        }

    }

    fun setOnQuantityChangeListener(listener: OnQuantityChangeListener) {
        quantityChangeListener = listener
    }

    fun setQuantity(newQuantity: Int) {
        quantity = newQuantity
        updateButton()
    }
}