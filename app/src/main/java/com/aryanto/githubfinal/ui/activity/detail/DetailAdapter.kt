package com.aryanto.githubfinal.ui.activity.detail

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class DetailAdapter(
    activity: AppCompatActivity
) : FragmentStateAdapter(activity) {

    private val fragments = mutableListOf<Fragment>()

    fun addFragment(fragment: Fragment){
        fragments.add(fragment)
    }
    override fun getItemCount(): Int = fragments.size

    override fun createFragment(position: Int): Fragment = fragments[position]
}