package com.getir.core.di

import com.getir.core.domain.usecases.home.GetProductsUseCase
import com.getir.core.domain.usecases.home.GetProductsUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class UseCaseModule {
    @Binds
    @Singleton
    abstract fun bindGetProductsUseCase(
        getProductsUseCaseImpl: GetProductsUseCaseImpl
    ): GetProductsUseCase


}
