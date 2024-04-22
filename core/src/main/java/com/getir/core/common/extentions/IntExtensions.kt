package com.getir.core.common.extentions

import android.content.Context
import android.content.res.Resources.getSystem

val Int.toPx: Int get() = (this * getSystem().displayMetrics.density).toInt()

