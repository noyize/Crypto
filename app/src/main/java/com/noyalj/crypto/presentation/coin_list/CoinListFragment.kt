package com.noyalj.crypto.presentation.coin_list

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.noyalj.crypto.R
import com.noyalj.crypto.common.BounceEdgeEffectFactory
import com.noyalj.crypto.common.Resource
import com.noyalj.crypto.databinding.FragmentCoinListBinding
import com.noyalj.crypto.domain.model.Coin
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CoinListFragment : Fragment(R.layout.fragment_coin_list), CoinAdapter.OnClick {

    private val viewModel: CoinListViewModel by viewModels()
    private val coinAdapter by lazy { CoinAdapter(this) }
    private lateinit var binding: FragmentCoinListBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentCoinListBinding.bind(view)
        setUpRecyclerView()
        observeCoinList()
    }

    private fun setUpRecyclerView() {
        binding.recyclerView.apply {
            adapter = coinAdapter
            layoutManager = LinearLayoutManager(requireContext())
            edgeEffectFactory = BounceEdgeEffectFactory()
            setHasFixedSize(true)
        }
    }

    private fun observeCoinList() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.state.collect {
                    when (it) {
                        is Resource.Loading -> showProgress(true)
                        is Resource.Success -> setCoins(it.value)
                        is Resource.Error -> showError(it.error)
                    }
                }
            }
        }
    }

    private fun showProgress(show: Boolean) {
        with(binding) {
            recyclerView.isVisible = !show
            progressBar.isVisible = show
        }
    }

    private fun setCoins(coins: List<Coin>) {
        showProgress(false)
        coinAdapter.submitList(coins)
    }

    private fun showError(error: String) {
        showProgress(false)
        Snackbar.make(binding.root, error, Snackbar.LENGTH_INDEFINITE).show()
    }

    override fun onCoinClick(coin: Coin) {
        findNavController().navigate(
            CoinListFragmentDirections.actionCoinListFragmentToCoinDetailFragment(
                coinId = coin.id,
                coinName = coin.name
            )
        )
    }
}