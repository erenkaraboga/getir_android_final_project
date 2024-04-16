package com.getir.core.common.extentions

import android.content.res.Resources.getSystem

val Float.toDp: Int get() = (this / getSystem().displayMetrics.density).toInt()
val Float.toPx: Int get() = (this * getSystem().displayMetrics.density).toInt()
val Double.twoDigit: String get() = String.format("%.2f", this)
