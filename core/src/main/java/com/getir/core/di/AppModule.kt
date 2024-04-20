package com.getir.core.di


import com.google.gson.Gson

import com.getir.core.domain.mappers.ProductMapper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Singleton
    @Provides
    fun provideGson() = Gson()

    @Provides
    @Singleton
    fun provideProductMapper(): ProductMapper = ProductMapper()
}
