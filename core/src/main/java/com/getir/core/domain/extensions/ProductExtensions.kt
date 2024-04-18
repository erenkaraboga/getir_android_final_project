package com.getir.core.domain.extensions

import com.getir.core.domain.models.Product

fun Product.getImageUrl(): String {
    return when {
        imageURL.isNotEmpty() -> imageURL
        thumbnailURL.isNotEmpty() -> thumbnailURL
        squareThumbnailURL.isNotEmpty() -> squareThumbnailURL
        else -> ""
    }
}