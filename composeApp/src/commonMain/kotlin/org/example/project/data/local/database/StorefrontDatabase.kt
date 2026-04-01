package org.example.project.data.local.database

import androidx.room.ConstructedBy
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.RoomDatabaseConstructor
import androidx.room.TypeConverters
import org.example.project.data.local.converter.StorefrontConverters
import org.example.project.data.local.dao.StorefrontDao
import org.example.project.data.local.entity.CartItemEntity
import org.example.project.data.local.entity.OrderEntity

@Database(
    entities = [CartItemEntity::class, OrderEntity::class],
    version = 1,
)
@ConstructedBy(AppDatabaseConstructor::class)
@TypeConverters(StorefrontConverters::class)
abstract class StorefrontDatabase : RoomDatabase() {
    abstract fun storefrontDao(): StorefrontDao
}

@Suppress("KotlinNoActualForExpect")
expect object AppDatabaseConstructor : RoomDatabaseConstructor<StorefrontDatabase> {
    override fun initialize(): StorefrontDatabase
}
const val STOREFRONT_DATABASE_NAME = "storefront.db"
