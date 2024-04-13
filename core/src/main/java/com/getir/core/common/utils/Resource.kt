package com.getir.core.common.utils

sealed class Resource<T>(val data: T? = null, val message: UiText = UiText.DynamicString("")) {
    class Loading<T>(data: T? = null) : Resource<T>(data)
    class Success<T>(data: T?) : Resource<T>(data)
    class Error<T>(message: UiText, data: T? = null) : Resource<T>(data, message)
}
