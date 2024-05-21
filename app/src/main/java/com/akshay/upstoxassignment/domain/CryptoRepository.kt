package com.akshay.upstoxassignment.domain

import com.akshay.upstoxassignment.data.CoinDataItem

interface UpStoxRepository {

    suspend fun fetchCryptocurrencies(): List<CoinDataItem>?

    suspend fun getCryptocurrenciesFromDb(): List<CoinDataItem>

}