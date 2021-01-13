package com.programmergabut.katili_jiwo_adi_wiyono_takehome.view.adapter

import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.programmergabut.katili_jiwo_adi_wiyono_takehome.R
import com.programmergabut.katili_jiwo_adi_wiyono_takehome.data.remote.remoteentity.users.Item
import com.programmergabut.katili_jiwo_adi_wiyono_takehome.databinding.LayoutBottomLoaderBinding
import com.programmergabut.katili_jiwo_adi_wiyono_takehome.databinding.ListUsersBinding
import javax.inject.Inject


/* class UserAdapter @Inject constructor(
    private val glide: RequestManager
): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val VIEW_TYPE_LOADING = 0
    private val VIEW_TYPE_ITEM = 1

    var listItem : MutableList<Item?> = mutableListOf()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerView.ViewHolder {
        return when (viewType) {
            VIEW_TYPE_ITEM -> {
                UserViewHolder(DataBindingUtil.inflate(
                    LayoutInflater.from(parent.context), R.layout.list_users, parent, false
                ))
            }
            else -> {
                BottomLoaderViewHolder(DataBindingUtil.inflate(
                    LayoutInflater.from(parent.context), R.layout.layout_bottom_loader, parent, false
                ))
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder.itemViewType){
            VIEW_TYPE_ITEM -> {
                (holder as UserViewHolder).bind(listItem[position]!!)
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if(listItem[position] == null) VIEW_TYPE_LOADING else VIEW_TYPE_ITEM
    }

    override fun getItemCount(): Int = listItem.size

    inner class UserViewHolder(private val binding: ListUsersBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(data: Item){
            glide.load(data.avatarUrl).into(binding.ivUser)
            binding.tvUserName.text = data.login
        }
    }

    inner class BottomLoaderViewHolder(private val binding: LayoutBottomLoaderBinding): RecyclerView.ViewHolder(binding.root)
} */
