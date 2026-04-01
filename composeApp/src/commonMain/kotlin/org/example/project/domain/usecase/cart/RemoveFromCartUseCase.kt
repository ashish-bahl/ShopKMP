package org.example.project.domain.usecase.cart

import org.example.project.domain.model.Product
import org.example.project.domain.repository.CartLocalDataSource

class RemoveFromCartUseCase(
    private val cartLocalDataSource: CartLocalDataSource,
) {
    suspend operator fun invoke(product: Product) = cartLocalDataSource.remove(product)
}
