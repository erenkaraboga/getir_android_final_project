package com.getir.core.common.extentions


val Double.twoDigit: String get() = String.format("%.2f", this)
