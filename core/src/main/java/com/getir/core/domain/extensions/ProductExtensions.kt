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

fun Product.getDescription(): String {
    return when {
        shortDescription.isNotEmpty() -> shortDescription
        attribute.isNotEmpty() -> attribute
        else -> ""
    }
}