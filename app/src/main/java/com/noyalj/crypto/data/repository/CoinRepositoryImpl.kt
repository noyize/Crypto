package com.noyalj.crypto.data.repository

import com.noyalj.crypto.data.remote.CoinPaprikaApi
import com.noyalj.crypto.data.remote.dto.CoinDetailDto
import com.noyalj.crypto.data.remote.dto.CoinDto
import com.noyalj.crypto.domain.repository.CoinRepository
import javax.inject.Inject

class CoinRepositoryImpl @Inject constructor(private val coinPaprikaApi: CoinPaprikaApi) :
    CoinRepository {
    override suspend fun getCoins(): List<CoinDto> {
        return coinPaprikaApi.getCoins()
    }

    override suspend fun getCoinById(coinId: String): CoinDetailDto {
        return coinPaprikaApi.getCoinById(coinId)
    }
}