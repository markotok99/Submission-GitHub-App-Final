package com.aryanto.githubfinal.ui.activity.home

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.aryanto.githubfinal.data.model.Item
import com.aryanto.githubfinal.databinding.ItemBinding
import com.bumptech.glide.Glide

class HomeAdapter(
    private var users: List<Item>
) : RecyclerView.Adapter<HomeAdapter.HomeViewHolder>() {
    class HomeViewHolder(private val binding: ItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(user: Item) {
            binding.apply {
                tViewPerson.text = user.login

                Glide.with(root)
                    .load(user.avatar_url)
                    .into(iViewPerson)

                root.setOnClickListener {
                    Log.d("GAF-HA User clicked: ", "$user")
                }
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeViewHolder {
        val binding = ItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return HomeViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return users.size
    }

    override fun onBindViewHolder(holder: HomeViewHolder, position: Int) {
        holder.bind(users[position])
    }

    fun updateList(newList: List<Item>) {
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