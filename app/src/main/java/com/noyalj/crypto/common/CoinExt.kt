package com.noyalj.crypto.common

import com.noyalj.crypto.data.remote.dto.CoinDetailDto
import com.noyalj.crypto.data.remote.dto.CoinDto
import com.noyalj.crypto.domain.model.Coin
import com.noyalj.crypto.domain.model.CoinDetail

fun CoinDetailDto.toCoinDetail(): CoinDetail {
    return CoinDetail(
        coinId = id,
        name = name,
        description = description,
        symbol = symbol,
        rank = rank,
        isActive = isActive,
        tags = tags,
        team = team
    )
}

fun CoinDto.toCoin(): Coin {
    return Coin(
        id = id,
        isActive = isActive,
        name = name,
        rank = rank,
        symbol = symbol
    )
}