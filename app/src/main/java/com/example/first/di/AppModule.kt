package com.example.first.di

import com.example.first.data.repository.AppsRepositoryImpl
import com.example.first.domain.repository.AppsRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class AppModule {

    @Binds
    @Singleton
    abstract fun bindAppsRepository(
        impl: AppsRepositoryImpl
    ): AppsRepository
}