package org.example.project.domain.usecase.order

import org.example.project.domain.repository.OrderRepository

class ObserveOrdersUseCase(
    private val orderRepository: OrderRepository,
) {
    operator fun invoke() = orderRepository.observeOrders()
}
