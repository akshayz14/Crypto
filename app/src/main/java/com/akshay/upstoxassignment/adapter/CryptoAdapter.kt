package com.akshay.upstoxassignment.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.akshay.upstoxassignment.R
import com.akshay.upstoxassignment.base.AppConstants
import com.akshay.upstoxassignment.base.toStringOrEmpty
import com.akshay.upstoxassignment.data.CoinDataItem
import com.akshay.upstoxassignment.databinding.ListItemBinding
import com.bumptech.glide.Glide
import dagger.hilt.android.qualifiers.ActivityContext
import javax.inject.Inject

class CryptoAdapter @Inject constructor(@ActivityContext private val context: Context) :
    RecyclerView.Adapter<CryptoAdapter.CryptocurrencyViewHolder>() {

    private val asyncListDiffer = AsyncListDiffer(this, CryptocurrencyDiffCallback())

    private var originalList: List<CoinDataItem> = listOf()
    private var filteredList: List<CoinDataItem> = listOf()

    fun submitList(list: List<CoinDataItem>) {
        originalList = list
        filteredList = list
        asyncListDiffer.submitList(list)
    }


    fun filterList(criteria: List<String>) {
        filteredList = if (criteria.isEmpty()) {
            originalList
        } else {
            originalList.filter { item ->
                criteria.any { criterion ->
                    item.matchesCriterion(criterion)
                }
            }
        }
        asyncListDiffer.submitList(filteredList)
    }

    class CryptocurrencyViewHolder(var binding: ListItemBinding) :
        ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CryptocurrencyViewHolder {
        val binding = ListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CryptocurrencyViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return asyncListDiffer.currentList.size
    }

    override fun onBindViewHolder(holder: CryptocurrencyViewHolder, position: Int) {
        val data = asyncListDiffer.currentList[position]
        holder.binding.apply {
            tvCoinFullName.text = data.name
            tvCoinShortName.text = data.symbol
            setCrypto(this, data)
        }
    }


    private fun setCrypto(binding: ListItemBinding, data: CoinDataItem) {
        val iconRes = when (data.type.toStringOrEmpty()) {
            "token" -> if (data.is_active) R.mipmap.ic_token_symbol else R.mipmap.ic_inactive_coin
            "coin" -> if (data.is_active) R.mipmap.ic_coin_symbol else R.mipmap.ic_inactive_coin
            else -> null
        }

        iconRes?.let {
            Glide.with(context)
                .load(ResourcesCompat.getDrawable(context.resources, it, null))
                .into(binding.ivCoinSymbol)
        }

        binding.ivNew.visibility = if (data.is_new) View.VISIBLE else View.GONE
    }

    class CryptocurrencyDiffCallback : DiffUtil.ItemCallback<CoinDataItem>() {
        override fun areItemsTheSame(oldItem: CoinDataItem, newItem: CoinDataItem): Boolean {
            return oldItem.symbol == newItem.symbol
        }

        override fun areContentsTheSame(oldItem: CoinDataItem, newItem: CoinDataItem): Boolean {
            return oldItem == newItem
        }
    }

    private fun CoinDataItem.matchesCriterion(criterion: String): Boolean {
        return when (criterion) {
            AppConstants.ACTIVE_COINS -> this.is_active
            AppConstants.NEW_COINS -> this.is_new
            AppConstants.INACTIVE_COINS -> !this.is_active
            AppConstants.ONLY_COINS -> this.type == "coin"
            AppConstants.ONLY_TOKENS -> this.type == "token"
            else -> false
        }
    }
}



