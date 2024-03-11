package com.aryanto.githubfinal.ui.activity.home

import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.aryanto.githubfinal.R
import com.aryanto.githubfinal.databinding.ActivityHomeBinding
import com.aryanto.githubfinal.utils.ClientState
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding
    private lateinit var homeAdapter: HomeAdapter
    private val homeVM: HomeVM by viewModel()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        if (savedInstanceState == null) {
            homeVM.getAllUsers()
        }

        setAdapter()
        setView()
        setSearch()

    }

    private fun setAdapter() {
        binding.apply {
            homeAdapter = HomeAdapter(listOf())
            rvHome.layoutManager = LinearLayoutManager(this@HomeActivity)
            rvHome.adapter = homeAdapter
        }
    }

    private fun setView() {
        binding.apply {
            homeVM.users.observe(this@HomeActivity) { result ->
                when (result) {
                    is ClientState.Success -> {
                        pBarHome.visibility = View.GONE
                        result.data.let { homeAdapter.updateList(it) }
                    }

                    is ClientState.Error -> {
                        pBarHome.visibility = View.GONE
                    }

                    is ClientState.Loading -> {
                        pBarHome.visibility = View.VISIBLE
                    }
                }
            }
        }
    }

    private fun setSearch() {
        binding.apply {
            materialSearchView.setupWithSearchBar(materialSearchBar)
            materialSearchView.editText.setOnEditorActionListener { textView, actionId, _ ->
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    val query = textView.text.toString()
                    query.let {
                        homeVM.searchUser(it)
                    }
                    materialSearchView.hide()
                    true
                } else {
                    false
                }
            }
        }
    }

}