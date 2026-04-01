package org.example.project.data.local.mapper

import org.example.project.data.local.entity.CartItemEntity
import org.example.project.domain.model.Product

fun CartItemEntity.toDomainModel() = Product(
    id = id,
    name = name,
    quantity = quantity,
    description = description,
    costPrice = costPrice,
    sellingPrice = sellingPrice,
    imageUrl = imageUrl,
)

fun Product.toLocalEntity() = CartItemEntity(
    id = id,
    name = name,
    quantity = quantity,
    description = description,
    costPrice = costPrice,
    sellingPrice = sellingPrice,
    imageUrl = imageUrl,
)
