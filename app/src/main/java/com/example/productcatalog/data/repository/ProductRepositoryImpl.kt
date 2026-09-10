package com.example.productcatalog.data.repository

import com.example.productcatalog.data.api.ProductApi
import com.example.productcatalog.data.model.toDomain
import com.example.productcatalog.domain.model.Product
import com.example.productcatalog.domain.repository.ProductRepository

class ProductRepositoryImpl(
    private val api: ProductApi
) : ProductRepository {
    
    override suspend fun getProducts(skip: Int, limit: Int): Result<List<Product>> {
        return try {
            val response = api.getProducts(skip, limit)
            val products = response.products?.map { it.toDomain() } ?: emptyList()
            Result.success(products)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun searchProducts(query: String, skip: Int, limit: Int): Result<List<Product>> {
        return try {
            val response = api.searchProducts(query, skip, limit)
            val products = response.products?.map { it.toDomain() } ?: emptyList()
            Result.success(products)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getProductDetails(id: Int): Result<Product> {
        return try {
            val response = api.getProductDetails(id)
            Result.success(response.toDomain())
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
