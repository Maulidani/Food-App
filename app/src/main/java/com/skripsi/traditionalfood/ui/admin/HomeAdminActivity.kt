package com.skripsi.traditionalfood.ui.admin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.widget.addTextChangedListener
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.navigation.NavigationView
import com.skripsi.traditionalfood.R
import com.skripsi.traditionalfood.adapter.AdapterFood
import com.skripsi.traditionalfood.model.ResponseFoodModel
import com.skripsi.traditionalfood.network.ApiClient
import com.skripsi.traditionalfood.ui.LoginActivity
import com.skripsi.traditionalfood.ui.ProfileActivity
import com.skripsi.traditionalfood.utils.Constant
import com.skripsi.traditionalfood.utils.PreferencesHelper
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeAdminActivity : AppCompatActivity(), AdapterFood.IUserRecycler {
    private lateinit var sharedPref: PreferencesHelper

    private lateinit var toggle: ActionBarDrawerToggle
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var navigationView: NavigationView

    private val rv: RecyclerView by lazy { findViewById(R.id.rvFood) }
    private val search: EditText by lazy { findViewById(R.id.etSearch) }
    private val notFound: TextView by lazy { findViewById(R.id.tvNotFound) }

    private val fabAdd: FloatingActionButton by lazy { findViewById(R.id.fabAdd) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_admin)
        sharedPref = PreferencesHelper(this)

        drawerLayout = findViewById(R.id.drawerLayout)
        navigationView = findViewById(R.id.navView)

        toggle = ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        navigationView.setNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.navProfile -> {
                    startActivity(
                        Intent(this, ProfileActivity::class.java).putExtra(
                            "type",
                            "edit"
                        )
                    )
                }
                R.id.navLogout -> {
                    sharedPref.logout()
                    Toast.makeText(this, "Keluar", Toast.LENGTH_SHORT).show()
                    startActivity(
                        Intent(this, LoginActivity::class.java)
                    )
                    finish()
                }
            }
            true
        }

        search.addTextChangedListener {
            food(search.text.toString())
        }

        food("")

        fabAdd.setOnClickListener {
            startActivity(Intent(this, InputEditFoodActivity::class.java).putExtra("type", "add"))
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if (toggle.onOptionsItemSelected(item)) {
            return true
        }

        return super.onOptionsItemSelected(item)
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

                        val adapter =
                            data?.let { AdapterFood(it, type!!, this@HomeAdminActivity) }
                        rv.layoutManager = GridLayoutManager(this@HomeAdminActivity, 2)
                        rv.adapter = adapter

                        if ("${data?.size}" == "0") {
                            notFound.visibility = View.VISIBLE
                        } else {
                            notFound.visibility = View.INVISIBLE
                        }

                    } else {
                        Toast.makeText(this@HomeAdminActivity, "gagal", Toast.LENGTH_SHORT).show()
                    }
                } else {

                    Toast.makeText(this@HomeAdminActivity, "gagal", Toast.LENGTH_SHORT).show()

                }
            }

            override fun onFailure(call: Call<ResponseFoodModel>, t: Throwable) {

                Toast.makeText(this@HomeAdminActivity, t.message.toString(), Toast.LENGTH_SHORT)
                    .show()
            }

        })
    }

    override fun onResume() {
        super.onResume()
        food("")

        if (!sharedPref.getBoolean(Constant.PREF_IS_LOGIN)) {
            finish()
        }
    }

    override fun refreshView(onUpdate: Boolean) {
        food("")
    }
}