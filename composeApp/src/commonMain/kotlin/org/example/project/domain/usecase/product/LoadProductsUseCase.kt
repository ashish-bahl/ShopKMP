package org.example.project.domain.usecase.product

import org.example.project.domain.repository.ProductCatalogRepository

class LoadProductsUseCase(
    private val productCatalogRepository: ProductCatalogRepository,
) {
    suspend operator fun invoke() = productCatalogRepository.getProducts()
}
