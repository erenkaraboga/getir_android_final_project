package com.getir.core.domain.mappers

import com.getir.core.common.constants.EMPTY_STRING
import com.getir.core.data.remote.models.response.ProductDto
import com.getir.core.domain.models.Product


class ProductMapper {



    fun fromDtoToDomain(productDto: ProductDto): Product = with(productDto) {
        Product(
            id = id,
            attribute = if (!attribute.isNullOrEmpty()) attribute else EMPTY_STRING,
           imageURL = imageURL,
            name = name,
            price = price,
            priceText = priceText,
            shortDescription = if (!shortDescription.isNullOrEmpty()) shortDescription else EMPTY_STRING,
            thumbnailURL = thumbnailURL,

        )
    }

}
