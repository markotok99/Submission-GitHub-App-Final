package com.aryanto.githubfinal.ui.fragment.following

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.aryanto.githubfinal.data.model.Item
import com.aryanto.githubfinal.databinding.ItemBinding
import com.bumptech.glide.Glide

class FollowingAdapter(
    private var users: List<Item>
) : RecyclerView.Adapter<FollowingAdapter.FollowingViewHolder>() {
    class FollowingViewHolder(private val binding: ItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(user: Item) {
            binding.apply {
                tViewPerson.text = user.login

                Glide.with(root)
                    .load(user.avatar_url)
                    .into(iViewPerson)

            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FollowingViewHolder {
        val binding = ItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FollowingViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return users.size
    }

    override fun onBindViewHolder(holder: FollowingViewHolder, position: Int) {
        holder.bind(users[position])
    }

    fun updateListFollowing(newList: List<Item>) {
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