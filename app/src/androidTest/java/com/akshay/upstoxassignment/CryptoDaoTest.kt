package com.akshay.upstoxassignment

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.akshay.upstoxassignment.data.CoinDataItem
import com.akshay.upstoxassignment.db.CryptoDatabase
import com.akshay.upstoxassignment.db.CryptoItemDao
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test

class UpstoxDaoTest {


    lateinit var cryptoDatabase: CryptoDatabase
    lateinit var cryptoItemDao: CryptoItemDao


    @Before
    fun setUp() {
        cryptoDatabase = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            CryptoDatabase::class.java
        ).allowMainThreadQueries().build()
        cryptoItemDao = cryptoDatabase.cryptoItemDao()
    }

    @Test
    fun insertData_Test() = runBlocking {
        val cryptocurrencies = listOf(
            CoinDataItem(
                name = "Bitcoin",
                symbol = "BTC",
                is_new = true,
                is_active = true,
                type = "CRYPTO"
            ),
            CoinDataItem(
                name = "Ethereum",
                symbol = "ETH",
                is_new = true,
                is_active = true,
                type = "CRYPTO"
            )
        )

        cryptoItemDao.insertAll(cryptocurrencies)
        val allCrypto = cryptoItemDao.getAll()
        assert(allCrypto.containsAll(cryptocurrencies))
    }


    @After
    fun tearDown() {
        cryptoDatabase.close()
    }

}