package com.programmergabut.katili_jiwo_adi_wiyono_takehome.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.programmergabut.katili_jiwo_adi_wiyono_takehome.databinding.ListFooterBinding

class FooterAdapter(private val retry: () -> Unit): LoadStateAdapter<FooterAdapter.LoadStateViewHolder>() {

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
            binding.llRetry.setOnClickListener {
                retry.invoke()
            }
        }

        fun bind(loadState: LoadState) {
            binding.apply {
                pbFooter.isVisible = loadState is LoadState.Loading
                llRetry.isVisible = loadState !is LoadState.Loading
            }
        }
    }
}