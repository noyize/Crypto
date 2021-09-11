package com.noyalj.crypto.domain.model

import com.noyalj.crypto.data.remote.dto.CoinDetailDto


data class CoinDetail(
    val coinId: String?,
    val name: String?,
    val description: String?,
    val symbol: String?,
    val rank: Int?,
    val isActive: Boolean?,
    val tags: List<CoinDetailDto.Tag>?,
    val team: List<CoinDetailDto.TeamMember>?
)
