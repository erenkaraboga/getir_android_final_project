package com.getir.core.common.utils

import com.getir.core.R
import com.getir.core.common.extentions.handleError
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException

fun <T, K> performRequest(mapper: (response: T) -> K, networkCall: suspend () -> T): Flow<Resource<K>> = flow {
    try {
        emit(Resource.Loading())
        val response = networkCall.invoke()
        emit(Resource.Success(data = mapper(response)))
    } catch (e: HttpException) {
        emit(Resource.Error(e.handleError()))
    } catch (e: IOException) {
        emit(Resource.Error(UiText.StringResource(R.string.couldntReachServerError)))
    }
}
