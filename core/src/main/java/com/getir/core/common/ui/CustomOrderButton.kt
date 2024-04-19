package com.getir.core.common.ui

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.animation.Animation
import android.view.animation.TranslateAnimation
import android.widget.LinearLayout
import android.widget.TextView
import com.getir.core.R
import com.getir.core.common.extentions.twoDigit
import com.google.android.material.card.MaterialCardView

class CustomOrderButton @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    private val orderLayout: LinearLayout
    private val buttonText: TextView
    private val tvPrice: TextView
    init {
        LayoutInflater.from(context).inflate(R.layout.custom_order_button, this, true)
        orderLayout = findViewById(R.id.cvPrice2)
        buttonText = findViewById(R.id.tvText)
        tvPrice= findViewById(R.id.tvPrice)
        attrs?.let { retrieveAttributes(attrs) }
    }

    @SuppressLint("CustomViewStyleable")
    private fun retrieveAttributes(attrs: AttributeSet) {
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.custom_order_button)

        val isBasket = typedArray.getBoolean(R.styleable.custom_order_button_isBasket, false)
        val buttonText = typedArray.getString(R.styleable.custom_order_button_customButtonText)
        buttonText?.let { setButton(isBasket, it) }
        typedArray.recycle()
    }

    private fun setButton(isBasket: Boolean, text: String) {
        orderLayout.visibility = if (isBasket) VISIBLE else GONE
        buttonText.text = text
    }
      fun setAmount(amount : Double){
        "₺${amount.twoDigit}".also { tvPrice.text = it }
    }
}