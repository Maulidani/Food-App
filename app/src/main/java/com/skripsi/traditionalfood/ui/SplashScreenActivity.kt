package com.skripsi.traditionalfood.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.skripsi.traditionalfood.R
import com.skripsi.traditionalfood.ui.admin.HomeAdminActivity
import com.skripsi.traditionalfood.ui.user.HomeUserActivity
import com.skripsi.traditionalfood.utils.Constant
import com.skripsi.traditionalfood.utils.PreferencesHelper
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashScreenActivity : AppCompatActivity() {
    private lateinit var sharedPref: PreferencesHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        supportActionBar?.hide()

        sharedPref = PreferencesHelper(this)

        CoroutineScope(Dispatchers.Main).launch {
            delay(2500)

            if (sharedPref.getBoolean(Constant.PREF_IS_LOGIN)) {

                val type = sharedPref.getString(Constant.PREF_TYPE)
                if (type == "admin") {
                    startActivity(Intent(this@SplashScreenActivity, HomeAdminActivity::class.java))
                } else {
                    startActivity(Intent(this@SplashScreenActivity, HomeUserActivity::class.java))
                }

            } else {
                startActivity(Intent(this@SplashScreenActivity, LoginActivity::class.java))
            }

            finish()
        }
    }

}