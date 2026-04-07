package org.example.project.domain.usecase.cart

import org.example.project.domain.repository.CartRepository

class ClearCartUseCase(
    private val cartRepository: CartRepository,
) {
    suspend operator fun invoke() = cartRepository.clear()
}
