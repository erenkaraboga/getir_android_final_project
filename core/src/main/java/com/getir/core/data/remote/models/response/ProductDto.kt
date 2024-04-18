package com.getir.core.data.remote.models.response

data class ProductDto(
    val attribute: String?,
    val id: String,
    val imageURL: String?,
    val name: String,
    val price: Double,
    val priceText: String,
    val shortDescription: String?,
    val thumbnailURL: String?,
    val squareThumbnailURL: String?,
    val isSuggestedProduct: Boolean =false,
)