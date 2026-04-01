package org.example.project

import android.content.Context
import androidx.room.Room
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import org.example.project.data.local.database.STOREFRONT_DATABASE_NAME
import org.example.project.data.local.database.StorefrontDatabase

fun getStorefrontDatabase(context: Context): StorefrontDatabase {
    return Room.databaseBuilder(
        context.applicationContext,
        StorefrontDatabase::class.java,
        STOREFRONT_DATABASE_NAME)
        .setDriver(BundledSQLiteDriver())
        .build()
}