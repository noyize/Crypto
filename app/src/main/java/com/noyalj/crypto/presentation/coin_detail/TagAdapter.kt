package com.noyalj.crypto.presentation.coin_detail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.noyalj.crypto.data.remote.dto.CoinDetailDto
import com.noyalj.crypto.databinding.ItemTagBinding

class TagAdapter(
) : ListAdapter<CoinDetailDto.Tag, RecyclerView.ViewHolder>(TagDiffCallback) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding = ItemTagBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TagViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as TagViewHolder).bind(getItem(position))
    }

    private inner class TagViewHolder(private val binding: ItemTagBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(tag: CoinDetailDto.Tag) {
            binding.apply {
                tagChip.text = tag.name
            }
        }
    }
}

object TagDiffCallback : DiffUtil.ItemCallback<CoinDetailDto.Tag>() {
    override fun areItemsTheSame(oldItem: CoinDetailDto.Tag, newItem: CoinDetailDto.Tag): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(
        oldItem: CoinDetailDto.Tag,
        newItem: CoinDetailDto.Tag
    ): Boolean {
        return oldItem == newItem
    }
}