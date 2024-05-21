package com.akshay.upstoxassignment.di

import com.akshay.upstoxassignment.domain.UpStoxRepository
import com.akshay.upstoxassignment.domain.UpstoxRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun bindUpstoxRepository(upstoxRepositoryImpl: UpstoxRepositoryImpl): UpStoxRepository
}