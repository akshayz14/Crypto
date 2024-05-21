package com.akshay.upstoxassignment.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cryptocurrency")
data class CoinDataItem(
    @PrimaryKey val symbol: String,
    val is_active: Boolean,
    val is_new: Boolean,
    val name: String,
    val type: String
)