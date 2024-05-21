package com.akshay.upstoxassignment.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.akshay.upstoxassignment.data.CoinDataItem


@Dao
interface CryptoItemDao {

    @Query("SELECT * FROM cryptocurrency")
    suspend fun getAll(): List<CoinDataItem>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(cryptocurrencies: List<CoinDataItem>)

}