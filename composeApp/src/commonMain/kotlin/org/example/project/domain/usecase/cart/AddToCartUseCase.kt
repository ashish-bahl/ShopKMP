package org.example.project.domain.usecase.cart

import org.example.project.domain.model.Product
import org.example.project.domain.repository.CartRepository

class AddToCartUseCase(
    private val cartRepository: CartRepository,
) {
    suspend operator fun invoke(product: Product) = cartRepository.add(product)
}
