package com.akshay.upstoxassignment.di

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.akshay.upstoxassignment.db.CryptoDatabase
import com.akshay.upstoxassignment.db.CryptoItemDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideCryptoDatabase(@ApplicationContext context: Context): CryptoDatabase {
        return Room.databaseBuilder(context, CryptoDatabase::class.java, "crypto_database")
            .build()
    }

    @Provides
    fun provideCryptoItemDao(database: CryptoDatabase): CryptoItemDao {
        return database.cryptoItemDao()
    }


}