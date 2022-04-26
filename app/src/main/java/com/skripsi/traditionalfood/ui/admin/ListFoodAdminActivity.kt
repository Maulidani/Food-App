package com.skripsi.traditionalfood.ui.admin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.widget.addTextChangedListener
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.navigation.NavigationView
import com.skripsi.traditionalfood.R
import com.skripsi.traditionalfood.adapter.AdapterFood
import com.skripsi.traditionalfood.model.ResponseFoodModel
import com.skripsi.traditionalfood.network.ApiClient
import com.skripsi.traditionalfood.utils.Constant
import com.skripsi.traditionalfood.utils.PreferencesHelper
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ListFoodAdminActivity : AppCompatActivity(), AdapterFood.IUserRecycler {

    private val tvTitle: TextView by lazy { findViewById(R.id.tvTitle) }
    private val rv: RecyclerView by lazy { findViewById(R.id.rvFood) }
    private val search: EditText by lazy { findViewById(R.id.etSearch) }
    private val notFound: TextView by lazy { findViewById(R.id.tvNotFound) }
    private val pbLoading: ProgressBar by lazy { findViewById(R.id.pbLoading) }

    private var category: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_food_admin)
        supportActionBar?.hide()

        category = intent.getStringExtra("category").toString()

        if (category == "kue") {
            tvTitle.text = "Kue Tradisional Wakatobi"
        } else {
            tvTitle.text = "Makanan Tradisional Wakatobi"
        }

        search.addTextChangedListener {
            food(search.text.toString(), category)
        }

        food("", category)
    }

    private fun food(searchString: String, category: String) {
        val type = "admin"
        pbLoading.visibility = View.VISIBLE;

        ApiClient.instances.showFoodCategory(searchString, category)
            .enqueue(object : Callback<ResponseFoodModel> {
                override fun onResponse(
                    call: Call<ResponseFoodModel>,
                    response: Response<ResponseFoodModel>
                ) {
                    val message = response.body()?.message
                    val error = response.body()?.errors
                    val data = response.body()?.data

                    if (response.isSuccessful) {

                        if (error == false) {

                            val adapter =
                                data?.let { AdapterFood(it, type, this@ListFoodAdminActivity) }
                            rv.layoutManager = GridLayoutManager(this@ListFoodAdminActivity, 2)
                            rv.adapter = adapter

                            if ("${data?.size}" == "0") {
                                notFound.visibility = View.VISIBLE
                            } else {
                                notFound.visibility = View.INVISIBLE
                            }

                        } else {
                            Toast.makeText(this@ListFoodAdminActivity, "gagal", Toast.LENGTH_SHORT)
                                .show()
                        }
                    } else {

                        Toast.makeText(this@ListFoodAdminActivity, "gagal", Toast.LENGTH_SHORT)
                            .show()

                    }
                    pbLoading.visibility = View.INVISIBLE;

                }

                override fun onFailure(call: Call<ResponseFoodModel>, t: Throwable) {

                    Toast.makeText(
                        this@ListFoodAdminActivity,
                        t.message.toString(),
                        Toast.LENGTH_SHORT
                    )
                        .show()
                    pbLoading.visibility = View.INVISIBLE;

                }

            })
    }

    override fun refreshView(onUpdate: Boolean) {
        food("", category)
    }

    override fun onResume() {
        super.onResume()
        food("", category)
    }
}