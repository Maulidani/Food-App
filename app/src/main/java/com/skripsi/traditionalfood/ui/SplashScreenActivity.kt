package com.skripsi.traditionalfood.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.skripsi.traditionalfood.R
import com.skripsi.traditionalfood.ui.admin.HomeAdminActivity
import com.skripsi.traditionalfood.ui.user.HomeUserActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashScreenActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        supportActionBar?.hide()

        CoroutineScope(Dispatchers.Main).launch {
            delay(2500)

            startActivity(Intent(this@SplashScreenActivity, LoginActivity::class.java))

            finish()
        }
    }
}