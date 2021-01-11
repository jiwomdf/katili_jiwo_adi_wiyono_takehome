package com.programmergabut.katili_jiwo_adi_wiyono_takehome.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.programmergabut.katili_jiwo_adi_wiyono_takehome.R
import com.programmergabut.katili_jiwo_adi_wiyono_takehome.data.remote.remoteentity.users.Item
import com.programmergabut.katili_jiwo_adi_wiyono_takehome.databinding.ListUsersBinding
import javax.inject.Inject


class UserAdapter @Inject constructor(
    private val glide: RequestManager
): RecyclerView.Adapter<UserAdapter.UserViewHolder>() {

    private val diffCallback = object: DiffUtil.ItemCallback<Item>(){
        override fun areItemsTheSame(oldItem: Item, newItem: Item) = oldItem == newItem

        override fun areContentsTheSame(oldItem: Item, newItem: Item) = oldItem == newItem
    }

    private val differ = AsyncListDiffer(this, diffCallback)

    var listItem : List<Item>
        get() = differ.currentList
        set(value) = differ.submitList(value)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): UserAdapter.UserViewHolder {
        val binding = DataBindingUtil.inflate<ListUsersBinding>(
            LayoutInflater.from(parent.context), R.layout.list_users, parent, false
        )
        return UserViewHolder(binding)
    }

    override fun onBindViewHolder(holder: UserAdapter.UserViewHolder, position: Int) {
        holder.bind(listItem[position])
    }

    override fun getItemCount(): Int{
        return listItem.size
    }

    inner class UserViewHolder(private val binding: ListUsersBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(data: Item){
            glide.load(data.avatarUrl).into(binding.ivUser)
            binding.tvUserName.text = data.login
        }
    }
}
