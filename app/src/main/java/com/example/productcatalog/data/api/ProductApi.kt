package com.example.productcatalog.data.api

import com.example.productcatalog.data.model.ProductDto
import com.example.productcatalog.data.model.ProductResponseDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ProductApi {
    @GET("products")
    suspend fun getProducts(
        @Query("skip") skip: Int,
        @Query("limit") limit: Int = 20
    ): ProductResponseDto

    @GET("products/search")
    suspend fun searchProducts(
        @Query("q") query: String,
        @Query("skip") skip: Int = 0,
        @Query("limit") limit: Int = 20
    ): ProductResponseDto

    @GET("products/{id}")
    suspend fun getProductDetails(
        @Path("id") id: Int
    ): ProductDto
}
