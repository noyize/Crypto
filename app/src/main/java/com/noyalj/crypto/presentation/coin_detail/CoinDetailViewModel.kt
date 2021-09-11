package com.noyalj.crypto.presentation.coin_detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.noyalj.crypto.common.Constants
import com.noyalj.crypto.common.Resource
import com.noyalj.crypto.common.toCoin
import com.noyalj.crypto.common.toCoinDetail
import com.noyalj.crypto.domain.model.CoinDetail
import com.noyalj.crypto.domain.repository.CoinRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class CoinDetailViewModel @Inject constructor(
    private val coinRepository: CoinRepository,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _coinDetailState = MutableStateFlow<Resource<CoinDetail>>(Resource.Loading)
    val coinDetailState: StateFlow<Resource<CoinDetail>> = _coinDetailState

    init {
        getCoinById()
    }

    private fun getCoinById() = viewModelScope.launch {
        savedStateHandle.get<String>(Constants.PARAM_COIN_ID)?.let {
            try {
                val coin = coinRepository.getCoinById(it).toCoinDetail()
                _coinDetailState.emit(Resource.Success(coin))
            } catch (e: HttpException) {
                _coinDetailState.emit(Resource.Error(e.localizedMessage ?: "An unexpected error occurred"))
            } catch (e: IOException) {
                _coinDetailState.emit(Resource.Error("Couldn't reach server. Check your internet connection."))
            }
        }
    }
}
