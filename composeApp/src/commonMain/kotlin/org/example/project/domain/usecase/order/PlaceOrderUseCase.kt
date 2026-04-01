package org.example.project.domain.usecase.order

import org.example.project.domain.model.Order
import org.example.project.domain.repository.OrderRepository

class PlaceOrderUseCase(
    private val orderRepository: OrderRepository,
) {
    suspend operator fun invoke(order: Order) = orderRepository.placeOrder(order)
}
