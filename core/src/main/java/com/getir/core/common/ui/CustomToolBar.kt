package com.getir.core.common.ui

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.getir.core.R
import com.getir.core.common.constants.ToolBarType

class CustomToolBar @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    private val btnCart: CustomBasketButton
    private val tvTitle: TextView
    private val btnClose: ImageView
    private val btnJunk: ImageView

    init {
        LayoutInflater.from(context).inflate(R.layout.custom_tool_bar, this)
        btnCart = findViewById(R.id.btn_cart)
        tvTitle = findViewById(R.id.tvTitle)
        btnClose = findViewById(R.id.btn_close)
        btnJunk = findViewById(R.id.btn_junk)
    }

    fun setType(type: ToolBarType) {
        val fadeDuration = 300L

        when (type) {
            ToolBarType.PRODUCT_LIST -> {
                setTitle("Product List")
                btnClose.fadeOut(fadeDuration)
                btnJunk.fadeOut(fadeDuration)
                btnCart.fadeIn(fadeDuration)
            }
            ToolBarType.PRODUCT_DETAIL -> {
                setTitle("Product Detail")
                btnClose.fadeIn(fadeDuration)
                btnCart.fadeOut(fadeDuration)
                btnJunk.fadeOut(fadeDuration)
            }
            ToolBarType.PRODUCT_DETAIL_BASKET -> {
                setTitle("Product Detail")
                btnClose.fadeIn(fadeDuration)
                btnCart.fadeIn(fadeDuration)
                btnJunk.fadeOut(fadeDuration)
            }
            ToolBarType.BASKET -> {
                setTitle("Basket")
                btnClose.fadeIn(fadeDuration)
                btnJunk.fadeIn(fadeDuration)
                btnCart.fadeOut(fadeDuration)
            }
        }
    }

    private fun setTitle(title: String) {
        tvTitle.text = title
    }

    private fun View.fadeIn(duration: Long) {
        if (visibility == View.VISIBLE) return
        alpha = 0f
        visibility = View.VISIBLE
        animate().alpha(1f).setDuration(duration).setListener(null)
    }

    private fun View.fadeOut(duration: Long) {
        if (visibility == View.GONE) return
        alpha = 1f
        visibility = View.VISIBLE
        animate().alpha(0f).setDuration(duration).setListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator) {
                visibility = View.GONE
            }
        })
    }

    fun setAmount(amount : Double){
        btnCart.setAmount(amount)
    }
    fun setCartButtonClickListener(listener: OnClickListener) {
        btnCart.setOnClickListener(listener)
    }
    fun setCloseButtonClickListener(listener: OnClickListener) {
        btnClose.setOnClickListener(listener)
    }
}
