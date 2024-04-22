package com.getir.core.common.ui

import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Paint
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.animation.Animation
import android.view.animation.TranslateAnimation
import android.widget.LinearLayout
import android.widget.TextView
import androidx.cardview.widget.CardView
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
    private val tvOlderPrice: TextView
    private val cvOrder: CardView
    private var buttonClickedListener: ButtonClickedListener? = null

    init {
        LayoutInflater.from(context).inflate(R.layout.custom_order_button, this, true)
        orderLayout = findViewById(R.id.cvPrice)
        buttonText = findViewById(R.id.tvText)
        tvPrice = findViewById(R.id.tvPrice)
        cvOrder = findViewById(R.id.cvOrder)
        tvOlderPrice = findViewById(R.id.tvOlderPrice)
        cvOrder.setOnClickListener {
            buttonClickedListener?.onButtonClicked()
        }
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
        setStrikeThrough(tvOlderPrice)
    }

    fun setAmount(amount: Double) {
        val newPrice = "₺${amount.twoDigit}"
        val oldPrice = "₺${(amount * 1.5).twoDigit}"

        val tvPriceAnimator = ObjectAnimator.ofFloat(tvPrice, "scaleX", 1f, 1.2f, 1f)
        tvPriceAnimator.duration = 500
        tvPriceAnimator.start()

        val tvOlderPriceAnimator = ObjectAnimator.ofFloat(tvOlderPrice, "scaleX", 1f, 1.2f, 1f)
        tvOlderPriceAnimator.duration = 500
        tvOlderPriceAnimator.start()

        tvPrice.text = newPrice
        tvOlderPrice.text = oldPrice
    }

    fun setButtonClickListener(listener: ButtonClickedListener) {
        buttonClickedListener = listener
    }
    private fun setStrikeThrough(textView: TextView) {
        textView.paintFlags = textView.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
    }

    interface ButtonClickedListener {
        fun onButtonClicked()
    }

}
