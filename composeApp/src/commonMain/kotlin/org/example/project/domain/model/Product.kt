package org.example.project.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class Product(
    val id: String,
    val name: String,
    val quantity: Int,
    val description: String,
    val costPrice: Double,
    val sellingPrice: Double,
    val imageUrl: String,
)
