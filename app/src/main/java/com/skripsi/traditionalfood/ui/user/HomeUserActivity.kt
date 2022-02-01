package com.skripsi.traditionalfood.ui.user

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.skripsi.traditionalfood.R

class HomeUserActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_user)
        supportActionBar?.hide()



    }
}