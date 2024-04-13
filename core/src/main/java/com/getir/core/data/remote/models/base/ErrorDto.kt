package com.getir.core.data.remote.models.base

import com.getir.core.domain.models.ErrorModel

data class ErrorDto(val error: String?)

fun ErrorDto.toErrorModel(): ErrorModel = ErrorModel(error = error)
