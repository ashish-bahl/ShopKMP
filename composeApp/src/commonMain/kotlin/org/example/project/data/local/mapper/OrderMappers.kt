package org.example.project.data.local.mapper

import org.example.project.data.local.entity.OrderEntity as LocalOrderEntity
import org.example.project.domain.model.Order

fun LocalOrderEntity.toDomainModel() = Order(
    id = id,
    cartItems = cartItems.map { it.toDomainModel() },
    totalAmount = totalAmount,
    totalQuantity = totalQuantity,
    name = name,
    address = address,
    number = number,
)

fun Order.toLocalEntity() = LocalOrderEntity(
    id = id,
    cartItems = cartItems.map { it.toLocalEntity() },
    totalAmount = totalAmount,
    totalQuantity = totalQuantity,
    name = name,
    address = address,
    number = number,
)
