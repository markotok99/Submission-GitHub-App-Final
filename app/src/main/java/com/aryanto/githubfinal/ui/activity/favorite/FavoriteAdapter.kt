package com.aryanto.githubfinal.ui.activity.favorite

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.aryanto.githubfinal.data.local.Favorite
import com.aryanto.githubfinal.databinding.ItemBinding
import com.aryanto.githubfinal.ui.activity.detail.DetailActivity
import com.bumptech.glide.Glide

class FavoriteAdapter(
    private var favorites: List<Favorite>
) : RecyclerView.Adapter<FavoriteAdapter.FavoriteViewHolder>() {
    class FavoriteViewHolder(private val binding: ItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(favorite: Favorite) {
            binding.apply {
                tViewPerson.text = favorite.login

                Glide.with(root)
                    .load(favorite.avatar_url)
                    .into(iViewPerson)

                root.setOnClickListener {
                    val intent = Intent(root.context, DetailActivity::class.java)
                    intent.putExtra("username", favorite.login)
                    root.context.startActivity(intent)
                }

            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteViewHolder {
        val binding = ItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FavoriteViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return favorites.size
    }

    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
        holder.bind(favorites[position])
    }

    fun updateFavoriteList(newList: List<Favorite>) {
        val diffResult = DiffUtil.calculateDiff(myDiffCallBack(favorites, newList))
        favorites = newList
        diffResult.dispatchUpdatesTo(this)
    }

    companion object {
        fun myDiffCallBack(oldList: List<Favorite>, newList: List<Favorite>) =
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