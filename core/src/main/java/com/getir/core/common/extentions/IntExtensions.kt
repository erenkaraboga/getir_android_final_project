package com.getir.core.common.extentions

import android.content.Context
import android.content.res.Resources.getSystem

val Int.toDp: Int get() = (this / getSystem().displayMetrics.density).toInt()
val Int.toPx: Int get() = (this * getSystem().displayMetrics.density).toInt()

