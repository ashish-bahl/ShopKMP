package org.example.project.domain.usecase.order

import org.example.project.domain.repository.OrderRepository

class GetOrderUseCase(
    private val orderRepository: OrderRepository,
) {
    suspend operator fun invoke(id: String) = orderRepository.getOrder(id)
}
