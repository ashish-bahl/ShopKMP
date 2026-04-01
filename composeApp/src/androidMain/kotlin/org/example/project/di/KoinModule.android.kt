package org.example.project.di

import org.example.project.data.local.database.StorefrontDatabase
import org.example.project.getStorefrontDatabase
import org.koin.dsl.module

actual fun platformModule() = module {
    single<StorefrontDatabase> { getStorefrontDatabase(get()) }
}