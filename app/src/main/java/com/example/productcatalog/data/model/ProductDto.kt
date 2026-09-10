package com.example.productcatalog.data.model

import com.google.gson.annotations.SerializedName
import com.example.productcatalog.domain.model.Product

data class ProductDto(
    @SerializedName("id") val id: Int?,
    @SerializedName("title") val title: String?,
    @SerializedName("description") val description: String?,
    @SerializedName("price") val price: Double?,
    @SerializedName("rating") val rating: Double?,
    @SerializedName("thumbnail") val thumbnail: String?,
    @SerializedName("images") val images: List<String>?
)

fun ProductDto.toDomain(): Product {
    return Product(
        id = id ?: 0,
        title = title.orEmpty(),
        description = description.orEmpty(),
        price = price ?: 0.0,
        rating = rating ?: 0.0,
        thumbnail = thumbnail.orEmpty(),
        images = images.orEmpty()
    )
}
