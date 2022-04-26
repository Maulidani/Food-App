package com.skripsi.traditionalfood.ui.user

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.skripsi.traditionalfood.R
import com.skripsi.traditionalfood.adapter.AdapterFood
import com.skripsi.traditionalfood.model.DataModel
import com.skripsi.traditionalfood.model.ResponseFoodModel
import com.skripsi.traditionalfood.network.ApiClient
import com.skripsi.traditionalfood.ui.LoginActivity
import com.skripsi.traditionalfood.ui.ProfileActivity
import com.skripsi.traditionalfood.ui.admin.InputEditFoodActivity
import com.skripsi.traditionalfood.utils.Constant
import com.skripsi.traditionalfood.utils.PreferencesHelper
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeUserActivity : AppCompatActivity() {

    val cardMakanan: CardView by lazy { findViewById(R.id.cardMakanan) }
    val cardKue: CardView by lazy { findViewById(R.id.cardKue) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_user)
        supportActionBar?.hide()

        cardMakanan.setOnClickListener {
            startActivity(
                Intent(this, ListFoodUserActivity::class.java)
                    .putExtra("category", "makanan")
            )
        }
        cardKue.setOnClickListener {
            startActivity(
                Intent(this, ListFoodUserActivity::class.java)
                    .putExtra("category", "kue")
            )
        }
    }

}