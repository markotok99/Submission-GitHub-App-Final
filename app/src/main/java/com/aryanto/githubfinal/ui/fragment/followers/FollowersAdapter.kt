package com.aryanto.githubfinal.ui.fragment.followers

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.aryanto.githubfinal.data.model.Item
import com.aryanto.githubfinal.databinding.ItemBinding
import com.aryanto.githubfinal.ui.activity.detail.DetailActivity
import com.bumptech.glide.Glide

class FollowersAdapter(
    private var users: List<Item>
) : RecyclerView.Adapter<FollowersAdapter.FollowersViewHolder>() {
    class FollowersViewHolder(private val binding: ItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(user: Item) {
            binding.apply {
                tViewPerson.text = user.login

                Glide.with(root)
                    .load(user.avatar_url)
                    .into(iViewPerson)

            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FollowersViewHolder {
        val binding = ItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FollowersViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return users.size
    }

    override fun onBindViewHolder(holder: FollowersViewHolder, position: Int) {
        holder.bind(users[position])
    }

    fun updateListFollowers(newList: List<Item>) {
        val diffResult = DiffUtil.calculateDiff(myDiffCallBack(users, newList))
        users = newList
        diffResult.dispatchUpdatesTo(this)
    }

    companion object {
        fun myDiffCallBack(oldList: List<Item>, newList: List<Item>) =
            object : DiffUtil.Callback() {
                override fun getOldListSize(): Int {
                    return oldList.size
                }

                override fun getNewListSize(): Int {
                    return newList.size
                }

                override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                    return oldList[oldItemPosition].id == newList[newItemPosition].id
                }

                override fun areContentsTheSame(
                    oldItemPosition: Int,
                    newItemPosition: Int
                ): Boolean {
                    return oldList[oldItemPosition] == newList[newItemPosition]
                }


            }
    }

}