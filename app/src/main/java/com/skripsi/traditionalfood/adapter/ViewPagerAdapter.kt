package com.skripsi.traditionalfood.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.skripsi.traditionalfood.ui.ListFoodFragment

class ViewPagerAdapter(
    fragmentManager: FragmentManager,
    lifecycle: Lifecycle,
    _category: String,
    totalItem: Int,
) :
    FragmentStateAdapter(fragmentManager, lifecycle) {

    private val category = _category
    private val total = totalItem

    override fun getItemCount(): Int = total

    override fun createFragment(position: Int): Fragment {

        return when (position) {
            0 -> ListFoodFragment(category, "makanan")
            1 -> ListFoodFragment(category, "kue")
            else -> Fragment()

        }
    }
}