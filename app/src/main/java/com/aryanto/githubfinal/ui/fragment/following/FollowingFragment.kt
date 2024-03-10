package com.aryanto.githubfinal.ui.fragment.following

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.aryanto.githubfinal.databinding.FragmentFollowingBinding
import com.aryanto.githubfinal.utils.ClientState
import org.koin.androidx.viewmodel.ext.android.viewModel

class FollowingFragment : Fragment() {
    private lateinit var binding: FragmentFollowingBinding
    private lateinit var followingAdapter: FollowingAdapter

    private val followingVM: FollowingVM by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFollowingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.getString("username")?.let {
            followingVM.fetchFollowing(it)
        }

        setAdapter()
        setView()

    }

    private fun setAdapter() {
        binding.apply {
            followingAdapter = FollowingAdapter(listOf())
            rvFollowing.adapter = followingAdapter
            rvFollowing.layoutManager = LinearLayoutManager(requireContext())
        }
    }

    private fun setView() {
        binding.apply {
            followingVM.following.observe(viewLifecycleOwner) { result ->
                when (result) {
                    is ClientState.Success -> {
                        progressBar.visibility = View.GONE
                        followingAdapter.updateListFollowing(result.data)
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
            FollowingFragment().apply {
                arguments = Bundle().apply {
                    putString("username", username)
                }
            }
    }
}