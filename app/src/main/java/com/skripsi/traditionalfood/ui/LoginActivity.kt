package com.skripsi.traditionalfood.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.google.android.material.button.MaterialButton
import com.skripsi.traditionalfood.R
import com.skripsi.traditionalfood.ui.admin.HomeAdminActivity
import com.skripsi.traditionalfood.ui.user.HomeUserActivity

class LoginActivity : AppCompatActivity() {

    private val btnLogin: MaterialButton by lazy { findViewById(R.id.btnLogin) }
    private val btnRegister: MaterialButton by lazy { findViewById(R.id.btnRegister) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        supportActionBar?.hide()

        btnLogin.setOnClickListener {
            startActivity(Intent(this, HomeAdminActivity::class.java))
//            startActivity(Intent(this, HomeUserActivity::class.java))
        }
        btnRegister.setOnClickListener {
            startActivity(Intent(this, ProfileActivity::class.java))
        }
    }
}