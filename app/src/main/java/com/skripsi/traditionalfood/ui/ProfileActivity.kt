package com.skripsi.traditionalfood.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.material.button.MaterialButton
import com.skripsi.traditionalfood.R
import com.skripsi.traditionalfood.ui.user.HomeUserActivity

class ProfileActivity : AppCompatActivity() {

    private val btnRegisterEdit: MaterialButton by lazy { findViewById(R.id.btnRegisterEditProfile) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
        supportActionBar?.title = "Profil"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val intentType = intent.getStringExtra("type")

        if (intentType == "edit") {
            btnRegisterEdit.setText("Edit")
        } else {
            supportActionBar?.title = "Daftar"
            supportActionBar?.setDisplayHomeAsUpEnabled(false)
            btnRegisterEdit.setText("Daftar")

            btnRegisterEdit.setOnClickListener {
                startActivity(Intent(this, HomeUserActivity::class.java))
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}