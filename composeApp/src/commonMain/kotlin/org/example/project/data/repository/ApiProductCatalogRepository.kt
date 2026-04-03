package org.example.project.data.repository

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import org.example.project.data.remote.ApiConfig
import org.example.project.domain.model.NetworkResult
import org.example.project.domain.model.Product
import org.example.project.domain.repository.ProductCatalogRepository

class ApiProductCatalogRepository(
    private val httpClient: HttpClient,
) : ProductCatalogRepository {

    override suspend fun getProducts(): NetworkResult<List<Product>> {
        return try {
            val response = httpClient.get("${ApiConfig.BASE_URL}${ApiConfig.ALL_PRODUCTS_PATH}")
            if (response.status.value in 200..299) {
                val products = response.body<List<Product>>()
                NetworkResult.Success(
                    status = response.status.value,
                    message = "Products loaded successfully",
                    data = products,
                )
            } else {
                NetworkResult.NetworkError(
                    statusCode = response.status.value,
                    message = "Failed to load products",
                )
            }
        } catch (exception: Exception) {
            NetworkResult.Exception(
                message = exception.message ?: "Unexpected error while loading products",
            )
        }
    }
}
