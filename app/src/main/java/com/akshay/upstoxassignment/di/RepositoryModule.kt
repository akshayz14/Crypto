package com.akshay.upstoxassignment.di

import com.akshay.upstoxassignment.domain.CryptoRepository
import com.akshay.upstoxassignment.domain.CryptoRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun bindUpstoxRepository(cryptoRepositoryImpl: CryptoRepositoryImpl): CryptoRepository
}