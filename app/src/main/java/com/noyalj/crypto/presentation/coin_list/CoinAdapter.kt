package com.noyalj.crypto.presentation.coin_list

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.noyalj.crypto.R
import com.noyalj.crypto.databinding.ItemCoinBinding
import com.noyalj.crypto.domain.model.Coin

class CoinAdapter(
    private val listener: OnClick
) : ListAdapter<Coin, RecyclerView.ViewHolder>(DiffCallback) {

    interface OnClick {
        fun onCoinClick(coin: Coin)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding = ItemCoinBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CoinViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as CoinViewHolder).bind(getItem(position))
    }

   private inner class CoinViewHolder(private val binding: ItemCoinBinding) :
        RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bind(coin: Coin) {
            binding.apply {
                coinName.text = "${coin.rank}. ${coin.name} (${coin.symbol})"
                isActive.apply {
                    if (coin.isActive) {
                        text = root.context.getString(R.string.active)
                        setTextColor(ContextCompat.getColor(root.context, R.color.green_800))
                    } else {
                        text = root.context.getString(R.string.not_active)
                        setTextColor(ContextCompat.getColor(root.context, R.color.red_800))
                    }
                }
                root.setOnClickListener {
                    listener.onCoinClick(coin)
                }
            }
        }
    }
}

object DiffCallback : DiffUtil.ItemCallback<Coin>() {
    override fun areItemsTheSame(oldItem: Coin, newItem: Coin): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Coin, newItem: Coin): Boolean {
        return oldItem == newItem
    }
}