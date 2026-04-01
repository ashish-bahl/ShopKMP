package org.example.project.domain.repository

import org.example.project.domain.model.NetworkResult
import org.example.project.domain.model.Product

interface ProductCatalogRepository {
    suspend fun getProducts(): NetworkResult<List<Product>>
}
