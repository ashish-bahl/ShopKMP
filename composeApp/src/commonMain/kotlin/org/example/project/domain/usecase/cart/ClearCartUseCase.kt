package org.example.project.domain.usecase.cart

import org.example.project.domain.repository.CartLocalDataSource

class ClearCartUseCase(
    private val cartLocalDataSource: CartLocalDataSource,
) {
    suspend operator fun invoke() = cartLocalDataSource.clear()
}
