package com.skripsi.traditionalfood.ui.admin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.skripsi.traditionalfood.R

class InputEditFoodActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_input_edit_food)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val intentType = intent.getStringExtra("type")

        if (intentType == "edit") {
            supportActionBar?.title = "Edit"
        } else {
            supportActionBar?.title = "Profil"
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}