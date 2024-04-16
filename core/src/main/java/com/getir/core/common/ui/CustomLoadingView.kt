package com.getir.core.common.ui

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import com.getir.core.R


class CustomLoadingView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    init {
        initializeView()
    }

    private fun initializeView() {
        View.inflate(context, R.layout.custom_loading_view, this)
    }
}
