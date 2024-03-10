package com.aryanto.githubfinal.ui.fragment.followers

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.aryanto.githubfinal.databinding.FragmentFollowersBinding
import com.aryanto.githubfinal.utils.ClientState
import org.koin.androidx.viewmodel.ext.android.viewModel


class FollowersFragment : Fragment() {
    private lateinit var binding: FragmentFollowersBinding
    private lateinit var followersAdapter: FollowersAdapter

    private val followersVM: FollowersVM by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFollowersBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.getString("username")?.let {
            followersVM.fetchFollowers(it)
        }

        setAdapter()
        setView()

    }

    private fun setAdapter() {
        binding.apply {
            followersAdapter = FollowersAdapter(listOf())
            rvFollowers.adapter = followersAdapter
            rvFollowers.layoutManager = LinearLayoutManager(requireContext())
        }
    }

    private fun setView() {
        binding.apply {
            followersVM.followers.observe(viewLifecycleOwner) { result ->
                when (result) {
                    is ClientState.Success -> {
                        progressBar.visibility = View.GONE
                        followersAdapter.updateListFollowers(result.data)
                    }

                    is ClientState.Error -> {
                        progressBar.visibility = View.GONE
                    }

                    is ClientState.Loading -> {
                        progressBar.visibility = View.VISIBLE
                    }
                }

            }

        }
    }

    companion object {

        @JvmStatic
        fun newInstance(username: String) =
            FollowersFragment().apply {
                arguments = Bundle().apply {
                    putString("username", username)
                }
            }

    }

}