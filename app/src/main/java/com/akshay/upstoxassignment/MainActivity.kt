package com.akshay.upstoxassignment

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ToggleButton
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.akshay.upstoxassignment.adapter.CryptoAdapter
import com.akshay.upstoxassignment.base.AppConstants
import com.akshay.upstoxassignment.data.CoinDataItem
import com.akshay.upstoxassignment.databinding.ActivityMainBinding
import com.akshay.upstoxassignment.viewmodel.CryptoViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: CryptoAdapter
    private val viewModel: CryptoViewModel by viewModels()
    private var updatedList: List<CoinDataItem> = emptyList()
    private val selectedCriteria = mutableSetOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.rvCryptoList.layoutManager = LinearLayoutManager(this)
        adapter = CryptoAdapter(this)
        binding.rvCryptoList.adapter = adapter
        binding.lifecycleOwner = this

        val dividerItemDecoration =
            DividerItemDecoration(binding.rvCryptoList.context, LinearLayoutManager.VERTICAL)
        binding.rvCryptoList.addItemDecoration(dividerItemDecoration)

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.filteredCryptocurrencies.collect {
                    updatedList = it
                    adapter.submitList(it)
                }
            }
        }

        setSupportActionBar(binding.toolbar)
        setClickListeners()

    }

    /**
     * Sets click listeners for toggle buttons to apply filters on the list of cryptocurrencies.
     *
     * This function defines a helper function `setCheckedChangeListener` to reduce redundancy
     * in setting up the checked change listeners for each toggle button.
     * The helper function:
     * - Adds the specified criterion to the selectedCriteria set when the button is checked.
     * - Removes the specified criterion from the selectedCriteria set when the button is unchecked.
     * - Updates the button's drawable and text based on its checked state.
     * - Calls the adapter's filterList method with the updated list of selected criteria.
     */
    private fun setClickListeners() {
        /**
         * Sets a checked change listener for a toggle button.
         *
         * @param button The toggle button to set the listener for.
         * @param criteria The criterion to add/remove based on the button's state.
         * @param drawable The drawable resource to set when the button is checked.
         * @param text The text to set when the button is checked.
         */
        fun setCheckedChangeListener(
            button: ToggleButton,
            criteria: String,
            drawable: Int,
            text: String
        ) {
            button.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked) {
                    selectedCriteria.add(criteria)
                    button.setButtonDrawable(drawable)
                    button.textOn = text
                } else {
                    selectedCriteria.remove(criteria)
                    button.setButtonDrawable(null)
                    button.textOff = text
                }
                adapter.filterList(selectedCriteria.toList())
            }
        }

        // Set checked change listeners for each toggle button
        setCheckedChangeListener(
            binding.tvActiveCoins,
            AppConstants.ACTIVE_COINS,
            R.drawable.ic_checked_icon,
            getString(R.string.active_coins)
        )
        setCheckedChangeListener(
            binding.tvNewCoins,
            AppConstants.NEW_COINS,
            R.drawable.ic_checked_icon,
            getString(R.string.new_coins)
        )
        setCheckedChangeListener(
            binding.tvInactiveCoins,
            AppConstants.INACTIVE_COINS,
            R.drawable.ic_checked_icon,
            getString(R.string.inactive_coins)
        )
        setCheckedChangeListener(
            binding.tvOnlyCoins,
            AppConstants.ONLY_COINS,
            R.drawable.ic_checked_icon,
            getString(R.string.only_coins)
        )
        setCheckedChangeListener(
            binding.tvOnlyTokens,
            AppConstants.ONLY_TOKENS,
            R.drawable.ic_checked_icon,
            getString(R.string.only_tokens)
        )
    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu, menu)

        val searchItem: MenuItem = menu.findItem(R.id.action_search)
        val searchView: SearchView = searchItem.actionView as SearchView

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                //Search query for viewmodel to filter coins
                //Writing this here helps in searching for coins while typing
                viewModel.setSearchQuery(newText.toString())
                return true
            }
        })

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_search -> {
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

}