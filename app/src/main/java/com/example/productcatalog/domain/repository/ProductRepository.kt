package com.example.productcatalog.domain.repository

import com.example.productcatalog.domain.model.Product

interface ProductRepository {
    suspend fun getProducts(skip: Int, limit: Int = 20): Result<List<Product>>
    suspend fun searchProducts(query: String, skip: Int, limit: Int = 20): Result<List<Product>>
    suspend fun getProductDetails(id: Int): Result<Product>
}
