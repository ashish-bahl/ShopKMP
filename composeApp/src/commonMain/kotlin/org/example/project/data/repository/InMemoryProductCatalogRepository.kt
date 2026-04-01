package org.example.project.data.repository

import kotlinx.coroutines.delay
import org.example.project.data.source.dummyProducts
import org.example.project.domain.model.NetworkResult
import org.example.project.domain.model.Product
import org.example.project.domain.repository.ProductCatalogRepository

class InMemoryProductCatalogRepository : ProductCatalogRepository {
    override suspend fun getProducts(): NetworkResult<List<Product>> = run {
        delay(1000)
        NetworkResult.Success(status = 200, message = "", data = dummyProducts)
    }
}
