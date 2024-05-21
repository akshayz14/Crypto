package com.akshay.upstoxassignment.domain

import com.akshay.upstoxassignment.data.CoinDataItem
import com.akshay.upstoxassignment.db.CryptoItemDao
import javax.inject.Inject

class UpstoxRepositoryImpl @Inject constructor(
    private val upstoxService: UpstoxService, private val cryptoItemDao: CryptoItemDao
) : UpStoxRepository {

    override suspend fun fetchCryptocurrencies(): List<CoinDataItem>? {
        val cryptocurrencies = upstoxService.getCoinData().body()
        val entities = cryptocurrencies?.map { cryptocurrency ->
            CoinDataItem(
                name = cryptocurrency.name,
                symbol = cryptocurrency.symbol,
                is_new = cryptocurrency.is_new,
                is_active = cryptocurrency.is_active,
                type = cryptocurrency.type
            )
        }
        entities?.let { cryptoItemDao.insertAll(it) }
        return entities
    }

    override suspend fun getCryptocurrenciesFromDb(): List<CoinDataItem> {
        return cryptoItemDao.getAll()
    }
}