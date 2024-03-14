package com.aryanto.githubfinal.ui.activity.favorite

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.aryanto.githubfinal.R
import com.aryanto.githubfinal.databinding.ActivityFavoriteBinding
import com.aryanto.githubfinal.utils.ClientState
import org.koin.androidx.viewmodel.ext.android.viewModel

class FavoriteActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFavoriteBinding
    private lateinit var favoriteAdapter: FavoriteAdapter
    private val favoriteVM: FavoriteVM by viewModel()

    companion object{
        private const val DETAIL_REQUEST_CODE = 1
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteBinding.inflate(layoutInflater)

        enableEdgeToEdge()
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        setAdapter()
        setView()

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == DETAIL_REQUEST_CODE && resultCode == Activity.RESULT_OK){
            favoriteVM.loadFavorites()
        }
    }

    private fun setAdapter() {
        binding.apply {
            favoriteAdapter = FavoriteAdapter(listOf())
            rvFavorite.layoutManager = LinearLayoutManager(this@FavoriteActivity)
            rvFavorite.adapter = favoriteAdapter
        }
    }

    private fun setView() {
        binding.apply {
            favoriteVM.favorites.observe(this@FavoriteActivity) { result ->
                when (result) {
                    is ClientState.Success -> {
                        progressBarFavorite.visibility = View.GONE

                        if (result.data.isEmpty()) {
                            tvEmptyMessage.visibility = View.VISIBLE
                            rvFavorite.visibility = View.GONE
                        } else {
                            tvEmptyMessage.visibility = View.GONE
                            rvFavorite.visibility = View.VISIBLE

                            val favorites = result.data
                            favoriteAdapter.updateFavoriteList(favorites)
                        }
                    }

                    is ClientState.Error -> {
                        progressBarFavorite.visibility = View.GONE
                    }

                    is ClientState.Loading -> {
                        progressBarFavorite.visibility = View.VISIBLE
                    }
                }

            }

            favoriteVM.loadFavorites()
        }
    }

}