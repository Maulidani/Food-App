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
import androidx.cardview.widget.CardView
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
import com.skripsi.traditionalfood.ui.LoginAdminActivity
import com.skripsi.traditionalfood.ui.ProfileActivity
import com.skripsi.traditionalfood.ui.user.ListFoodUserActivity
import com.skripsi.traditionalfood.utils.Constant
import com.skripsi.traditionalfood.utils.PreferencesHelper
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeAdminActivity : AppCompatActivity() {
    private lateinit var sharedPref: PreferencesHelper

    private lateinit var toggle: ActionBarDrawerToggle
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var navigationView: NavigationView
    private val fabAdd: FloatingActionButton by lazy { findViewById(R.id.fabAdd) }

    val cardMakanan: CardView by lazy { findViewById(R.id.cardMakanan) }
    val cardKue: CardView by lazy { findViewById(R.id.cardKue) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_admin)
        sharedPref = PreferencesHelper(this)

        cardMakanan.setOnClickListener {
            startActivity(
                Intent(this, ListFoodAdminActivity::class.java)
                    .putExtra("category", "makanan")
            )
        }
        cardKue.setOnClickListener {
            startActivity(
                Intent(this, ListFoodAdminActivity::class.java)
                    .putExtra("category", "kue")
            )
        }

        drawerLayout = findViewById(R.id.drawerLayout)
        navigationView = findViewById(R.id.navView)

        toggle = ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        fabAdd.setOnClickListener {
            startActivity(Intent(this, InputEditFoodActivity::class.java).putExtra("type", "add"))
        }

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
                        Intent(this, LoginAdminActivity::class.java)
                    )
                    finish()
                }
            }
            true
        }

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if (toggle.onOptionsItemSelected(item)) {
            return true
        }

        return super.onOptionsItemSelected(item)
    }


    override fun onResume() {
        super.onResume()

        if (!sharedPref.getBoolean(Constant.PREF_IS_LOGIN)) {
            finish()
        }
    }

}