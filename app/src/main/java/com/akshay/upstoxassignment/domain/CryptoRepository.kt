package com.akshay.upstoxassignment.domain

import com.akshay.upstoxassignment.data.CoinDataItem

interface CryptoRepository {

    suspend fun fetchCryptocurrencies(): List<CoinDataItem>?

    suspend fun getCryptocurrenciesFromDb(): List<CoinDataItem>

}