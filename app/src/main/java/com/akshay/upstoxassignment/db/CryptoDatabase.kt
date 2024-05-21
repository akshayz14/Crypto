package com.akshay.upstoxassignment.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.akshay.upstoxassignment.data.CoinDataItem

@Database(entities = [CoinDataItem::class], version = 1, exportSchema = false)
abstract class CryptoDatabase: RoomDatabase() {

    abstract fun cryptoItemDao(): CryptoItemDao


}

