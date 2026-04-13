package org.example.project.domain.usecase.order

import org.example.project.domain.model.Order
import org.example.project.domain.repository.OrderRepository

class AddToOrderUseCase (val orderRepo: OrderRepository) {

    suspend operator fun invoke(order: Order) = orderRepo.placeOrder(order)

}