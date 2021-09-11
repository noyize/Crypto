package com.noyalj.crypto.presentation.coin_detail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.noyalj.crypto.data.remote.dto.CoinDetailDto
import com.noyalj.crypto.databinding.ItemTagBinding
import com.noyalj.crypto.databinding.ItemTeamBinding

class TeamAdapter(
) : ListAdapter<CoinDetailDto.TeamMember, RecyclerView.ViewHolder>(TeamMemberDiffCallback) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding = ItemTeamBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TeamViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as TeamViewHolder).bind(getItem(position))
    }

    private inner class TeamViewHolder(private val binding: ItemTeamBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(teamMember: CoinDetailDto.TeamMember) {
            binding.apply {
                name.text = teamMember.name
                position.text = teamMember.position
            }
        }
    }
}

object TeamMemberDiffCallback : DiffUtil.ItemCallback<CoinDetailDto.TeamMember>() {
    override fun areItemsTheSame(oldItem: CoinDetailDto.TeamMember, newItem: CoinDetailDto.TeamMember): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(
        oldItem: CoinDetailDto.TeamMember,
        newItem: CoinDetailDto.TeamMember
    ): Boolean {
        return oldItem == newItem
    }
}