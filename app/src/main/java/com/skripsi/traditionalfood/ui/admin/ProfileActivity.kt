package com.skripsi.traditionalfood.ui.admin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.skripsi.traditionalfood.R

class ProfileActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
        supportActionBar?.title = "Profil"
    }
}