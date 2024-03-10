package com.aryanto.githubfinal.ui.activity.detail

import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.viewpager2.widget.ViewPager2
import com.aryanto.githubfinal.R
import com.aryanto.githubfinal.data.model.ItemDetail
import com.aryanto.githubfinal.databinding.ActivityDetailBinding
import com.aryanto.githubfinal.ui.fragment.followers.FollowersFragment
import com.aryanto.githubfinal.ui.fragment.following.FollowingFragment
import com.aryanto.githubfinal.utils.ClientState
import com.bumptech.glide.Glide
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import org.koin.androidx.viewmodel.ext.android.viewModel

class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding
    private lateinit var detailAdapter: DetailAdapter
    private lateinit var tabLayout: TabLayout
    private lateinit var viewPager: ViewPager2

    private val detailVM: DetailVM by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }


        val username = intent.getStringExtra("username")
        if (username != null) {
            setViewPager(username)
            detailVM.getDetailUser(username)
            setView()
        }

    }

    private fun setView() {
        binding.apply {
            detailVM.detailUser.observe(this@DetailActivity) { result ->
                when (result) {
                    is ClientState.Success -> {
                        detailUserProgressBar.visibility = View.GONE
                        val user = result.data.first()
                        bindDetailUser(user)
                    }

                    is ClientState.Error -> {
                        detailUserProgressBar.visibility = View.GONE
                    }

                    is ClientState.Loading -> {
                        detailUserProgressBar.visibility = View.VISIBLE
                    }
                }
            }
        }
    }

    private fun setViewPager(username: String) {
        binding.apply {
            tabLayout = tabLayoutUserDetail
            viewPager = viewPagerUserDetail

            detailAdapter = DetailAdapter(this@DetailActivity)
            viewPager.adapter = detailAdapter

            detailAdapter.addFragment(FollowersFragment.newInstance(username))
            detailAdapter.addFragment(FollowingFragment.newInstance(username))

            TabLayoutMediator(tabLayout, viewPager) { tab, position ->

                when (position) {
                    0 -> {
                        tab.text = "Followers"
                    }

                    1 -> {
                        tab.text = "Following"
                    }
                }

            }.attach()

        }
    }

    private fun bindDetailUser(user: ItemDetail) {
        binding.apply {
            tvUserDetailName.text = user.name?.toString()
            tvUserDetailUsername.text = user.login
            tvUserDetailFollowers.text = user.followers.toString()
            tvUserDetailFollowing.text = user.following.toString()

            Glide.with(root)
                .load(user.avatar_url)
                .into(ivUserDetail)
        }
    }
}