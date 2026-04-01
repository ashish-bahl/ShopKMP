package org.example.project.domain.usecase.cart

import org.example.project.domain.repository.CartRepository

class ObserveCartItemsUseCase(
    private val cartRepository: CartRepository,
) {
    operator fun invoke() = cartRepository.observeCartItems()
}
