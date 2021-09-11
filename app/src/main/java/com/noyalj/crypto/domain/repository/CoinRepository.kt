package com.noyalj.crypto.domain.repository

import com.noyalj.crypto.data.remote.dto.CoinDetailDto
import com.noyalj.crypto.data.remote.dto.CoinDto

interface CoinRepository {

    suspend fun getCoins(): List<CoinDto>

    suspend fun getCoinById(coinId: String): CoinDetailDto
}