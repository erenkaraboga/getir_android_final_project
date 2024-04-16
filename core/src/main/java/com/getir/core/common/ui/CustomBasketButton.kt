package com.getir.core.common.ui

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

class CustomBasketButton @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    private val tvAmount: TextView
    private val root: MaterialCardView
    init {
        LayoutInflater.from(context).inflate(R.layout.custom_basket_button, this, true)
        tvAmount = findViewById(R.id.tvAmount)
        root = findViewById(R.id.cvBasket)
    }

    fun setAmount(amount: Double) {
        val slideAnimation = TranslateAnimation(root.width.toFloat(), 0f, 0f, 0f)
        slideAnimation.duration = 300
        root.startAnimation(slideAnimation)

        slideAnimation.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation?) {
                "₺${amount.twoDigit}".also { tvAmount.text = it }
            }

            override fun onAnimationEnd(animation: Animation?) {}
            override fun onAnimationRepeat(animation: Animation?) {}
        })
    }
}