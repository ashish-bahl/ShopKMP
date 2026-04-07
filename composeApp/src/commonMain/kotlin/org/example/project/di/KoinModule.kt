package org.example.project.di

import io.ktor.client.HttpClient
import io.ktor.client.engine.HttpClientEngineFactory
import org.example.project.data.remote.createHttpClient
import org.example.project.data.repository.ApiProductCatalogRepository
import org.example.project.data.repository.DefaultCartRepository
import org.example.project.domain.repository.CartRepository
import org.example.project.domain.repository.ProductCatalogRepository
import org.example.project.domain.usecase.cart.AddToCartUseCase
import org.example.project.domain.usecase.cart.ClearCartUseCase
import org.example.project.domain.usecase.cart.GetCartItemUseCase
import org.example.project.domain.usecase.cart.ObserveCartItemsUseCase
import org.example.project.domain.usecase.cart.RemoveFromCartUseCase
import org.example.project.domain.usecase.cart.UpdateCartItemUseCase
import org.example.project.domain.usecase.product.LoadProductsUseCase
import org.example.project.presentation.core.MainViewModel
import org.example.project.presentation.cart.CartViewModel
import org.example.project.presentation.productdetails.ProductDetailsViewModel
import org.example.project.presentation.productlist.ProductListingViewModel
import org.koin.core.context.startKoin
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.KoinAppDeclaration
import org.koin.dsl.bind
import org.koin.dsl.module

expect fun platformModule(): Module

fun initKoin(config: KoinAppDeclaration? = null) =
    startKoin {
        config?.invoke(this)
        modules(
            platformModule(),
            provideRepositoryModule,
            provideNetworkModule,
            provideUseCaseModule,
            provideViewModelModule
        )
    }


val provideNetworkModule = module {
    single<HttpClient> { createHttpClient(get<HttpClientEngineFactory<*>>()) }
}

val provideRepositoryModule = module {
    singleOf(::ApiProductCatalogRepository).bind(ProductCatalogRepository::class)
    singleOf(::DefaultCartRepository).bind(CartRepository::class)
}

val provideUseCaseModule = module {
    singleOf(::LoadProductsUseCase)
    singleOf(::AddToCartUseCase)
    singleOf(::UpdateCartItemUseCase)
    singleOf(::RemoveFromCartUseCase)
    singleOf(::GetCartItemUseCase)
    singleOf(::ObserveCartItemsUseCase)
    singleOf(::ClearCartUseCase)
}

val provideViewModelModule = module {
    viewModelOf(::ProductDetailsViewModel)
    viewModelOf(::ProductListingViewModel)
    viewModelOf(::CartViewModel)
    viewModelOf(::MainViewModel)
}
