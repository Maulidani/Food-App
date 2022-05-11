package com.skripsi.traditionalfood.ui.user

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.skripsi.traditionalfood.R
import com.skripsi.traditionalfood.adapter.AdapterFood
import com.skripsi.traditionalfood.adapter.ViewPagerAdapter
import com.skripsi.traditionalfood.model.ResponseFoodModel
import com.skripsi.traditionalfood.network.ApiClient
import com.skripsi.traditionalfood.ui.LoginActivity
import com.skripsi.traditionalfood.ui.ProfileActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ListFoodUserActivity : AppCompatActivity() {

    private val viewPager: ViewPager2 by lazy { findViewById(R.id.viewPager) }
    private val tabLayout: TabLayout by lazy { findViewById(R.id.tabLayout) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_food_user)
        supportActionBar?.title = "Makanan Tradisional"

        val type = "user"

        val totalItem = 2
        val viewPagerAdapter = ViewPagerAdapter(
            supportFragmentManager,
            lifecycle, type, totalItem
        )
        viewPager.adapter = viewPagerAdapter

        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            when (position) {
                0 -> tab.text = "Makanan"
                1 -> tab.text = "Jajanan / Kue"
            }
        }.attach()

    }
}