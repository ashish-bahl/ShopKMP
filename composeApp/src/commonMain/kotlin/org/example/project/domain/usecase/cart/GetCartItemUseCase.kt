package org.example.project.domain.usecase.cart

import org.example.project.domain.repository.CartRepository

class GetCartItemUseCase(
    private val cartRepository: CartRepository,
) {
    suspend operator fun invoke(id: String) = cartRepository.getCartItem(id)
}
