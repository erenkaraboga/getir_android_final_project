package com.getir.core.utils

import com.getir.core.common.constants.EMPTY_STRING
import com.getir.core.data.remote.models.response.ProductDto
import com.getir.core.data.remote.models.response.ProductListDto
import com.getir.core.data.remote.models.response.ProductListResponseDto
import com.getir.core.domain.mappers.ProductMapper

import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.ResponseBody
import okhttp3.ResponseBody.Companion.toResponseBody
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException

class MockHelper {
    companion object {
        private const val errorJson = "{\"error\":\"\"}"

        val ioException = IOException()
        private val productDto = ProductDto(
            attribute = EMPTY_STRING,
            id = EMPTY_STRING,
            imageURL = EMPTY_STRING,
            name = EMPTY_STRING,
            price = 0.0,
            priceText = EMPTY_STRING,
            shortDescription = EMPTY_STRING,
            thumbnailURL = EMPTY_STRING,
            squareThumbnailURL = EMPTY_STRING,
            isSuggestedProduct = false,
        )

        private val productList = listOf(productDto)

        private val productListDto = ProductListDto(EMPTY_STRING, EMPTY_STRING,0, productList)

        var productListResponseDto = ProductListResponseDto().apply {
           add(productListDto)
           add(productListDto)
        }

        fun getHttpException(): HttpException {
            return HttpException(
                Response.error<ResponseBody>(
                    500,
                    errorJson.toResponseBody("plain/text".toMediaTypeOrNull())
                )
            )
        }
    }
}
