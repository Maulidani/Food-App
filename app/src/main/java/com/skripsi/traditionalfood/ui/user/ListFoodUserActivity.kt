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
import com.skripsi.traditionalfood.R
import com.skripsi.traditionalfood.adapter.AdapterFood
import com.skripsi.traditionalfood.model.ResponseFoodModel
import com.skripsi.traditionalfood.network.ApiClient
import com.skripsi.traditionalfood.ui.LoginActivity
import com.skripsi.traditionalfood.ui.ProfileActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ListFoodUserActivity : AppCompatActivity(), AdapterFood.IUserRecycler {

    private val tvTitle: TextView by lazy { findViewById(R.id.tvInfo) }
    private val rv: RecyclerView by lazy { findViewById(R.id.rvFood) }
    private val search: EditText by lazy { findViewById(R.id.etSearch) }
    private val notFound: TextView by lazy { findViewById(R.id.tvNotFound) }

    private val pbLoading: ProgressBar by lazy { findViewById(R.id.pbLoading) }

    private var category: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_food_user)
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
        val type = "user"
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
                                data?.let { AdapterFood(it, type!!, this@ListFoodUserActivity) }
                            rv.layoutManager = GridLayoutManager(this@ListFoodUserActivity, 2)
                            rv.adapter = adapter

                            if ("${data?.size}" == "0") {
                                notFound.visibility = View.VISIBLE
                            } else {
                                notFound.visibility = View.INVISIBLE
                            }

                        } else {
                            Toast.makeText(this@ListFoodUserActivity, "gagal", Toast.LENGTH_SHORT)
                                .show()
                        }
                    } else {

                        Toast.makeText(this@ListFoodUserActivity, "gagal", Toast.LENGTH_SHORT)
                            .show()

                    }
                    pbLoading.visibility = View.INVISIBLE;

                }

                override fun onFailure(call: Call<ResponseFoodModel>, t: Throwable) {

                    Toast.makeText(
                        this@ListFoodUserActivity,
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