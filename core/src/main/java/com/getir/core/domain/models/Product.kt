package com.getir.core.domain.models

data class Product(
    val attribute: String,
    val id: String,
    val imageURL: String,
    val name: String,
    val price: Double,
    val priceText: String,
    val shortDescription: String,
    val thumbnailURL: String,
    var quantity: Int = 0,
    val squareThumbnailURL: String,
    val isSuggestedItem: Boolean = false,
)