package com.akshay.upstoxassignment.domain

import com.akshay.upstoxassignment.data.CoinDataItem
import retrofit2.Response
import retrofit2.http.GET

interface CryptoService {
    @GET("CoinData")
    suspend fun getCoinData(): Response<List<CoinDataItem>>
}