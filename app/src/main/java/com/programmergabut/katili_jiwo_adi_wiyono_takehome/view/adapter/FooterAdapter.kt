package com.programmergabut.katili_jiwo_adi_wiyono_takehome.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.programmergabut.katili_jiwo_adi_wiyono_takehome.databinding.ListFooterBinding

class FooterAdapter(
    private val retry: () -> Unit,
    private val showError: (loadState: LoadState) -> Unit
): LoadStateAdapter<FooterAdapter.LoadStateViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): LoadStateViewHolder {
        val binding = ListFooterBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return LoadStateViewHolder(binding)
    }

    override fun onBindViewHolder(holder: LoadStateViewHolder, loadState: LoadState) {
        holder.bind(loadState)
    }

    inner class LoadStateViewHolder(private val binding: ListFooterBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.ivRetry.setOnClickListener {
                retry.invoke()
            }
        }

        fun bind(loadState: LoadState) {
            when(loadState){
                is LoadState.Loading -> {
                    binding.ivRetry.isVisible = false
                    binding.pbFooter.isVisible = true
                }
                is LoadState.Error -> {
                    showError.invoke(loadState)
                    binding.ivRetry.isVisible = true
                    binding.pbFooter.isVisible = false
                }
                is LoadState.NotLoading -> {
                    binding.pbFooter.isVisible = false
                    binding.ivRetry.isVisible = false
                }
            }
        }
    }
}