package com.getir.core.common.extentions

import com.getir.core.R
import com.google.gson.Gson
import com.getir.core.common.utils.UiText
import com.getir.core.data.remote.models.base.ErrorDto
import com.getir.core.data.remote.models.base.toErrorModel


import retrofit2.HttpException

val gson = Gson()

@Synchronized
fun HttpException.handleError(): UiText {
    val errorString = this.response()?.errorBody()?.string()
    errorString?.let {
        val errorModel = gson.fromJson(errorString, ErrorDto::class.java)?.toErrorModel()
        if (errorModel?.error != null) {
            return UiText.DynamicString(errorModel.error)
        } else {
            return UiText.StringResource(R.string.unexpectedError)
        }
    }
    return this.localizedMessage?.let { UiText.DynamicString(it) }
        ?: UiText.StringResource(R.string.unexpectedError)
}
