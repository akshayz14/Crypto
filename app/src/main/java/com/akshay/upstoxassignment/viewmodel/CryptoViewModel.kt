package com.akshay.upstoxassignment.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.akshay.upstoxassignment.data.CoinDataItem
import com.akshay.upstoxassignment.domain.UpStoxRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UpstoxViewModel @Inject constructor(private val repository: UpStoxRepository) : ViewModel() {

    private val _upStoxResponseStateFlow = MutableStateFlow<List<CoinDataItem>>(emptyList())
    val upStoxResponseStateFlow: StateFlow<List<CoinDataItem>> = _upStoxResponseStateFlow


    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> get() = _searchQuery

    init {
        fetchCryptocurrencies()
    }

    /**
     * Fetches the list of cryptocurrencies from a remote source and stores them in the database.
     * Then, reads the list from the database and updates the _upStoxResponseStateFlow.
     *
     * - If the database is empty, it fetches the cryptocurrencies from the remote source
     *   and updates the state flow with the fetched data.
     * - If the database already contains data, it updates the state flow with the data from the database.
     */
    private fun fetchCryptocurrencies() {
        viewModelScope.launch {
            val data = repository.getCryptocurrenciesFromDb()
            if (data.isEmpty()) {
                _upStoxResponseStateFlow.value = repository.fetchCryptocurrencies() ?: emptyList()
            } else {
                _upStoxResponseStateFlow.value = data
            }
        }
    }


    /**
     * filteredCryptocurrencies is a StateFlow that always contains the filtered list of cryptocurrencies
     * based on the search query.
     *
     * - If the search query is empty, it returns the original list of cryptocurrencies.
     * - If the search query is not empty, it returns the filtered list of cryptocurrencies.
     *
     * The filtering is performed by checking if the name or symbol of each cryptocurrency contains
     * the search query, ignoring case.
     */
    val filteredCryptocurrencies: StateFlow<List<CoinDataItem>> =
        combine(_upStoxResponseStateFlow, _searchQuery) { cryptocurrencies, query ->
            if (query.isEmpty()) {
                cryptocurrencies
            } else {
                cryptocurrencies.filter {
                    it.name.contains(query, ignoreCase = true) ||
                            it.symbol.contains(query, ignoreCase = true)
                }
            }
        }.stateIn(viewModelScope, SharingStarted.Lazily, emptyList())


    /**
     * Sets the search query used to filter the list of cryptocurrencies.
     *
     * @param query The search query string used to filter the list.
     */

    fun setSearchQuery(query: String) {
        _searchQuery.value = query
    }

}