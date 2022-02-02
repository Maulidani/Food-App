package com.skripsi.traditionalfood.ui.user

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import coil.load
import com.skripsi.traditionalfood.R
import com.skripsi.traditionalfood.utils.Constant

class FoodDetailActivity : AppCompatActivity() {

    private val imgfood: ImageView by lazy {findViewById(R.id.imgFood)}
    private val tvName: TextView by lazy {findViewById(R.id.tvFoodName)}
    private val tvDesc: TextView by lazy {findViewById(R.id.tvFoodDesc)}
    private val tvRecipe: TextView by lazy {findViewById(R.id.tvFoodRecipe)}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_food_detail)
        supportActionBar?.title = "Info Makanan"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val img = intent.getStringExtra("image")
        val name = intent.getStringExtra("name")
        val desc = intent.getStringExtra("description")
        val recipe = intent.getStringExtra("recipe")

        var linkImage = "${Constant.URL_IMAGE_FOOD}${img}"
        imgfood.load(linkImage)

        tvName.text = name
        tvDesc.text = desc
        tvRecipe.text = recipe
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}