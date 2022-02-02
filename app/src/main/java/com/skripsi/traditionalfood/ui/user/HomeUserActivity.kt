package com.skripsi.traditionalfood.ui.user

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.skripsi.traditionalfood.R
import com.skripsi.traditionalfood.adapter.AdapterFood
import com.skripsi.traditionalfood.model.DataModel
import com.skripsi.traditionalfood.model.ResponseFoodModel
import com.skripsi.traditionalfood.network.ApiClient
import com.skripsi.traditionalfood.ui.ProfileActivity
import com.skripsi.traditionalfood.ui.admin.InputEditFoodActivity
import com.skripsi.traditionalfood.utils.Constant
import com.skripsi.traditionalfood.utils.PreferencesHelper
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeUserActivity : AppCompatActivity() {
    private lateinit var sharedPref: PreferencesHelper

    private val rv: RecyclerView by lazy { findViewById(R.id.rvFood) }
    private val search: EditText by lazy { findViewById(R.id.etSearch) }

    private val imgMore: ImageView by lazy { findViewById(R.id.imgMore) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_user)
        supportActionBar?.hide()

        sharedPref = PreferencesHelper(this)

        search.addTextChangedListener {
            food(search.text.toString())
        }

        food("")

        imgMore.setOnClickListener {
            optionAlert()
        }
    }

    private fun optionAlert() {
        val builder: AlertDialog.Builder = AlertDialog.Builder(this)
        builder.setTitle("Aksi")

        val options = arrayOf("Profil", "Logout")
        builder.setItems(
            options
        ) { _, which ->
            when (which) {
                0 -> {
                    startActivity(
                        Intent(this, ProfileActivity::class.java).putExtra(
                            "type",
                            "edit"
                        )
                    )
                }
                1 -> {
                    sharedPref.logout()
                    Toast.makeText(this, "Keluar", Toast.LENGTH_SHORT).show()
                    finish()
                }
            }
        }
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }




    private fun food(searchString: String) {
        val type = sharedPref.getString(Constant.PREF_TYPE)

        ApiClient.instances.showFood(searchString).enqueue(object : Callback<ResponseFoodModel> {
            override fun onResponse(
                call: Call<ResponseFoodModel>,
                response: Response<ResponseFoodModel>
            ) {
                val message = response.body()?.message
                val error = response.body()?.errors
                val data = response.body()?.data

                if (response.isSuccessful) {

                    if (error == false) {

                        val adapter = data?.let { AdapterFood(it, type!!) }
                        rv.layoutManager = GridLayoutManager(this@HomeUserActivity, 2)
                        rv.adapter = adapter

                    } else {
                        Toast.makeText(this@HomeUserActivity, "gagal", Toast.LENGTH_SHORT).show()
                    }
                } else {

                    Toast.makeText(this@HomeUserActivity, "gagal", Toast.LENGTH_SHORT).show()

                }
            }

            override fun onFailure(call: Call<ResponseFoodModel>, t: Throwable) {

                Toast.makeText(this@HomeUserActivity, t.message.toString(), Toast.LENGTH_SHORT)
                    .show()
            }

        })
    }

    override fun onResume() {
        super.onResume()

        if (!sharedPref.getBoolean(Constant.PREF_IS_LOGIN)) {
            finish()
        }
    }
}