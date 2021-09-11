package com.noyalj.crypto.presentation.coin_detail

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.noyalj.crypto.R
import com.noyalj.crypto.common.Resource
import com.noyalj.crypto.databinding.FragmentCoinDetailBinding
import com.noyalj.crypto.domain.model.CoinDetail
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch


@AndroidEntryPoint
class CoinDetailFragment : Fragment(R.layout.fragment_coin_detail) {

    private lateinit var binding: FragmentCoinDetailBinding
    private val viewModel: CoinDetailViewModel by viewModels()
    private val tagAdapter by lazy { TagAdapter() }
    private val teamAdapter by lazy { TeamAdapter() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentCoinDetailBinding.bind(view)

        setUpTagRecyclerView()
        setUpTeamRecyclerView()
        observeCoinDetail()
    }

    private fun setUpTagRecyclerView(){
        binding.tagRecyclerView.apply {
            adapter = tagAdapter
            layoutManager = LinearLayoutManager(requireContext(),RecyclerView.HORIZONTAL,false)
            setHasFixedSize(true)
        }
    }

    private fun setUpTeamRecyclerView(){
        binding.teamRecyclerView.apply {
            adapter = teamAdapter
            layoutManager = LinearLayoutManager(requireContext())
            setHasFixedSize(true)
        }
    }

    private fun observeCoinDetail(){
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED){
                viewModel.coinDetailState.collect {
                    when(it){
                        is Resource.Loading -> showProgress(true)
                        is Resource.Success -> setCoinDetail(it.value)
                        is Resource.Error -> showError(it.error)
                    }
                }
            }
        }
    }

    private fun showProgress(show : Boolean){
        with(binding){
            detailLayout.isVisible = !show
            progressBar.isVisible = show
        }
    }

    private fun setCoinDetail(coinDetail: CoinDetail){
        showProgress(false)
        tagAdapter.submitList(coinDetail.tags)
        teamAdapter.submitList(coinDetail.team)
        binding.coinDescription.text = coinDetail.description
    }

    private fun showError(error : String){
        showProgress(false)
        Snackbar.make(binding.root,error, Snackbar.LENGTH_INDEFINITE).show()
    }
}