package com.skripsi.traditionalfood.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.skripsi.traditionalfood.R
import com.skripsi.traditionalfood.model.DataModel
import com.skripsi.traditionalfood.model.ResponseAuthModel
import com.skripsi.traditionalfood.network.ApiClient
import com.skripsi.traditionalfood.ui.admin.HomeAdminActivity
import com.skripsi.traditionalfood.ui.user.HomeUserActivity
import com.skripsi.traditionalfood.utils.Constant
import com.skripsi.traditionalfood.utils.PreferencesHelper
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginAdminActivity : AppCompatActivity() {

    private lateinit var sharedPref: PreferencesHelper

    private val inputEmail: TextInputEditText by lazy { findViewById(R.id.inputEmail) }
    private val inputPassword: TextInputEditText by lazy { findViewById(R.id.inputPassword) }
    private val btnLogin: MaterialButton by lazy { findViewById(R.id.btnLogin) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_admin)
        supportActionBar?.title = "Masuk Admin"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        sharedPref = PreferencesHelper(this)

        btnLogin.setOnClickListener {
            val email = inputEmail.text.toString()
            val password = inputPassword.text.toString()
            val type = "admin"

            login(email, password, type)
        }
    }

    private fun login(email: String, password: String, type: String) {

        ApiClient.instances.login(email, password, type)
            .enqueue(object : Callback<ResponseAuthModel> {
                override fun onResponse(
                    call: Call<ResponseAuthModel>,
                    response: Response<ResponseAuthModel>
                ) {

                    val message = response.body()?.message
                    val error = response.body()?.errors
                    val data = response.body()?.data

                    if (response.isSuccessful) {
                        if (error == false) {

                            saveSession(data!!)
                        } else {

                            Toast.makeText(this@LoginAdminActivity, "gagal", Toast.LENGTH_SHORT)
                                .show()

                        }
                    } else {
                        Toast.makeText(this@LoginAdminActivity, "gagal", Toast.LENGTH_SHORT).show()

                    }
                }

                override fun onFailure(call: Call<ResponseAuthModel>, t: Throwable) {

                    Toast.makeText(
                        this@LoginAdminActivity,
                        t.message.toString(),
                        Toast.LENGTH_SHORT
                    )
                        .show()
                }
            })
    }

    private fun saveSession(data: DataModel) {

        sharedPref.put(Constant.PREF_ID, data.id.toString())
        sharedPref.put(Constant.PREF_NAME, data.name)
        sharedPref.put(Constant.PREF_IMAGE, data.image)
        sharedPref.put(Constant.PREF_PHONE, data.phone!!)
        sharedPref.put(Constant.PREF_EMAIL, data.email!!)
        sharedPref.put(Constant.PREF_PASSWORD, data.password!!)
        sharedPref.put(Constant.PREF_TYPE, data.type!!)
        sharedPref.put(Constant.PREF_IS_LOGIN, true)
        Log.e(this.toString(), "data: $data")

        startActivity(Intent(this, HomeAdminActivity::class.java))

        finish()
    }

    override fun onSupportNavigateUp(): Boolean {
        startActivity(Intent(this, SplashScreenActivity::class.java))
        finish()
        return true
    }
}