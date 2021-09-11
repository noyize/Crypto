package com.noyalj.crypto.presentation.coin_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.noyalj.crypto.common.Resource
import com.noyalj.crypto.common.toCoin
import com.noyalj.crypto.domain.model.Coin
import com.noyalj.crypto.domain.repository.CoinRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class CoinListViewModel @Inject constructor(private val coinRepository: CoinRepository) :
    ViewModel() {

    private val _state = MutableStateFlow<Resource<List<Coin>>>(Resource.Loading)
    val state : StateFlow<Resource<List<Coin>>> = _state

    init {
        getCoins()
    }

    private fun getCoins() = viewModelScope.launch {
        try {
            val coins = coinRepository.getCoins().map { it.toCoin() }
            _state.emit(Resource.Success(coins))
        } catch (e: HttpException) {
            _state.emit(Resource.Error(e.localizedMessage ?: "An unexpected error occurred"))
        } catch (e: IOException) {
            _state.emit(Resource.Error("Couldn't reach server. Check your internet connection."))
        }
    }
}