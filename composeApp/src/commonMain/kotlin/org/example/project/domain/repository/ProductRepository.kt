package org.example.project.domain.repository

import org.example.project.domain.model.NetworkResult
import org.example.project.domain.model.Product
import org.example.project.domain.model.ProductSummary

interface ProductRepository {
    suspend fun getProductSummary(): NetworkResult<ProductSummary>

    suspend fun getProductDetails(productName: String): NetworkResult<Product>
}
