package com.skripsi.traditionalfood.ui.admin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.navigation.NavigationView
import com.skripsi.traditionalfood.R
import com.skripsi.traditionalfood.ui.ProfileActivity

class HomeAdminActivity : AppCompatActivity() {

    private lateinit var toggle: ActionBarDrawerToggle
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var navigationView: NavigationView

    private val fabAdd: FloatingActionButton by lazy { findViewById(R.id.fabAdd) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_admin)

        drawerLayout = findViewById(R.id.drawerLayout)
        navigationView = findViewById(R.id.navView)

        toggle = ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        navigationView.setNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.navProfile -> startActivity(
                    Intent(this, ProfileActivity::class.java).putExtra(
                        "type",
                        "edit"
                    )
                )
                R.id.navLogout -> Toast.makeText(this, "Keluar", Toast.LENGTH_SHORT).show()
            }
            true
        }

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
}