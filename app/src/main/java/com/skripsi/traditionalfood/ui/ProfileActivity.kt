package com.skripsi.traditionalfood.ui

import android.app.Activity
import android.content.Intent
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import coil.load
import com.github.dhaval2404.imagepicker.ImagePicker
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.skripsi.traditionalfood.R
import com.skripsi.traditionalfood.model.DataModel
import com.skripsi.traditionalfood.model.ResponseAuthModel
import com.skripsi.traditionalfood.model.ResponseFoodModel
import com.skripsi.traditionalfood.network.ApiClient
import com.skripsi.traditionalfood.ui.user.HomeUserActivity
import com.skripsi.traditionalfood.utils.Constant
import com.skripsi.traditionalfood.utils.PreferencesHelper
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File

class ProfileActivity : AppCompatActivity() {
    private lateinit var sharedPref: PreferencesHelper

    private val inputName: TextInputEditText by lazy { findViewById(R.id.inputName) }
    private val inputPhone: TextInputEditText by lazy { findViewById(R.id.inputPhone) }
    private val inputEmail: TextInputEditText by lazy { findViewById(R.id.inputEmail) }
    private val inputPassword: TextInputEditText by lazy { findViewById(R.id.inputPassword) }
    private val btnRegisterEdit: MaterialButton by lazy { findViewById(R.id.btnRegisterEditProfile) }
    private val btnChooseFoto: MaterialButton by lazy { findViewById(R.id.btnChooseFoto) }
    private val imgViewRegister: ImageView by lazy { findViewById(R.id.imgProfile) }

    private var reqBody: RequestBody? = null
    private var partImage: MultipartBody.Part? = null

    private lateinit var intentType: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
        supportActionBar?.title = "Profil"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        sharedPref = PreferencesHelper(this)

        intentType = intent.getStringExtra("type").toString()
        val idUser = sharedPref.getString(Constant.PREF_ID)

        if (intentType == "edit") {
            btnRegisterEdit.setText("Edit")

            val name = sharedPref.getString(Constant.PREF_NAME)
            val img = sharedPref.getString(Constant.PREF_IMAGE)
            val phone = sharedPref.getString(Constant.PREF_PHONE)
            val email = sharedPref.getString(Constant.PREF_EMAIL)
            val password = sharedPref.getString(Constant.PREF_PASSWORD)

            var linkImage = "${Constant.URL_IMAGE_USER}${img}"
            imgViewRegister.load(linkImage)

            inputName.setText(name)
            inputPhone.setText(phone)
            inputEmail.setText(email)
            inputPassword.setText(password)

        } else {
            supportActionBar?.title = "Daftar"
            supportActionBar?.setDisplayHomeAsUpEnabled(false)
            btnRegisterEdit.setText("Daftar")

        }

        btnChooseFoto.setOnClickListener {
            ImagePicker.with(this)
                .cropSquare()
                .compress(1024)         //Final image size will be less than 1 MB(Optional)
                .createIntent { intent ->
                    startForProfileImageResult.launch(intent)
                }
        }

        btnRegisterEdit.setOnClickListener {

            if (intentType == "edit") {

                if (inputName.text.toString().isEmpty() || inputPhone.text.toString()
                        .isEmpty() || inputEmail.text.toString()
                        .isEmpty() || inputPassword.text.toString().isEmpty()
                ) {
                    Toast.makeText(applicationContext, "tidak boleh kosong", Toast.LENGTH_SHORT)
                        .show()

                } else {
                    edit(
                        inputName.text.toString(),
                        inputPhone.text.toString(),
                        inputEmail.text.toString(),
                        inputPassword.text.toString(),
                        idUser!!
                    )
                }
            } else {

                if (inputName.text.toString().isEmpty() || inputPhone.text.toString()
                        .isEmpty() || inputEmail.text.toString()
                        .isEmpty() || inputPassword.text.toString().isEmpty()
                ) {
                    Toast.makeText(applicationContext, "tidak boleh kosong", Toast.LENGTH_SHORT)
                        .show()

                } else if (partImage == null) {
                    Toast.makeText(applicationContext, "pilih gambar", Toast.LENGTH_SHORT).show()

                } else {
                    registration(
                        inputName.text.toString(),
                        inputPhone.text.toString(),
                        inputEmail.text.toString(),
                        inputPassword.text.toString()
                    )
                }
            }
        }
    }

    private fun edit(
        name: String,
        phone: String,
        email: String,
        password: String,
        id: String
    ) {
        ApiClient.instances.editUser(name, phone, email, password, id.toInt())
            .enqueue(object :
                Callback<ResponseAuthModel> {
                override fun onResponse(
                    call: Call<ResponseAuthModel>,
                    response: Response<ResponseAuthModel>
                ) {
                    val message = response.body()?.message
                    if (response.isSuccessful) {
                        if (message == "Success") {
                            Toast.makeText(
                                applicationContext,
                                message.toString(),
                                Toast.LENGTH_SHORT
                            ).show()

                            sharedPref.put(Constant.PREF_NAME, name)
                            sharedPref.put(Constant.PREF_PHONE, phone)
                            sharedPref.put(Constant.PREF_EMAIL, email)
                            sharedPref.put(Constant.PREF_PASSWORD, password)

                            finish()
                        } else {
                            Toast.makeText(applicationContext, "gagal", Toast.LENGTH_SHORT).show()
                        }
                    } else {
                        Toast.makeText(applicationContext, "gagal", Toast.LENGTH_SHORT).show()

                    }
                }

                override fun onFailure(call: Call<ResponseAuthModel>, t: Throwable) {
                    Toast.makeText(applicationContext, t.message.toString(), Toast.LENGTH_SHORT)
                        .show()
                }
            })

    }

    private fun editImage(
        id: String,
    ) {
        val partId: RequestBody = id.toRequestBody("text/plain".toMediaTypeOrNull())

        ApiClient.instances.editImgUser(partImage!!, partId)
            .enqueue(object :
                Callback<ResponseAuthModel> {
                override fun onResponse(
                    call: Call<ResponseAuthModel>,
                    response: Response<ResponseAuthModel>
                ) {
                    val message = response.body()?.message
                    if (response.isSuccessful) {
                        if (message == "Success") {

                            login(
                                sharedPref.getString(Constant.PREF_EMAIL).toString(),
                                sharedPref.getString(Constant.PREF_PASSWORD).toString(),
                                sharedPref.getString(Constant.PREF_TYPE).toString()
                            )

                        } else {
                            Toast.makeText(applicationContext, "gagal", Toast.LENGTH_SHORT).show()
                        }
                    } else {
                        Toast.makeText(applicationContext, "gagal", Toast.LENGTH_SHORT).show()

                    }
                }

                override fun onFailure(call: Call<ResponseAuthModel>, t: Throwable) {
                    Toast.makeText(applicationContext, t.message.toString(), Toast.LENGTH_SHORT)
                        .show()
                }
            })

    }

    private fun registration(
        name: String,
        phone: String,
        email: String,
        password: String
    ) {
        val partName: RequestBody = name.toRequestBody("text/plain".toMediaTypeOrNull())
        val partPhone: RequestBody = phone.toRequestBody("text/plain".toMediaTypeOrNull())
        val partEmail: RequestBody = email.toRequestBody("text/plain".toMediaTypeOrNull())
        val partPassword: RequestBody = password.toRequestBody("text/plain".toMediaTypeOrNull())

        ApiClient.instances.registration(partName, partImage!!, partPhone, partEmail, partPassword)
            .enqueue(object :
                Callback<ResponseAuthModel> {
                override fun onResponse(
                    call: Call<ResponseAuthModel>,
                    response: Response<ResponseAuthModel>
                ) {
                    val message = response.body()?.message
                    if (response.isSuccessful) {
                        if (message == "Success") {
                            Toast.makeText(
                                applicationContext,
                                message.toString() + " silahkan masuk",
                                Toast.LENGTH_SHORT
                            ).show()

                            startActivity(
                                Intent(applicationContext, LoginActivity::class.java)
                            )
                            finish()
                        } else {
                            Toast.makeText(applicationContext, "gagal", Toast.LENGTH_SHORT).show()
                        }
                    } else {
                        Toast.makeText(applicationContext, "gagal", Toast.LENGTH_SHORT).show()

                    }
                }

                override fun onFailure(call: Call<ResponseAuthModel>, t: Throwable) {
                    Toast.makeText(applicationContext, t.message.toString(), Toast.LENGTH_SHORT)
                        .show()
                }
            })

    }

    private val startForProfileImageResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
            val resultCode = result.resultCode
            val data = result.data

            if (resultCode == Activity.RESULT_OK) {
                //Image Uri will not be null for RESULT_OK
                val fileUri = data?.data!!
//                    imageView.setImageURI(fileUri)

                val image: File = File(fileUri.path!!)
                imgViewRegister.setImageBitmap(BitmapFactory.decodeFile(image.absolutePath))

                Log.e("image format: ", "uri = $fileUri")
                Log.e("image format: ", "file path = $image")
                Log.e("image format: ", "file absolute path = ${image.absolutePath}")

                reqBody = image.asRequestBody("image/*".toMediaTypeOrNull())

                partImage = MultipartBody.Part.createFormData("image", image.name, reqBody!!)
                Log.e("image format: ", "file absolute path = ${partImage}")

                if (intentType == "edit") {
                    val idUser = sharedPref.getString(Constant.PREF_ID)
                    editImage(idUser!!)
                }

            } else if (resultCode == ImagePicker.RESULT_ERROR) {
                Toast.makeText(this, ImagePicker.getError(data), Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Task Cancelled", Toast.LENGTH_SHORT).show()
            }
        }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
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

                            Toast.makeText(this@ProfileActivity, "gagal", Toast.LENGTH_SHORT).show()

                        }
                    } else {
                        Toast.makeText(this@ProfileActivity, "gagal", Toast.LENGTH_SHORT).show()

                    }
                }

                override fun onFailure(call: Call<ResponseAuthModel>, t: Throwable) {

                    Toast.makeText(this@ProfileActivity, t.message.toString(), Toast.LENGTH_SHORT)
                        .show()
                }
            })
    }

    private fun saveSession(data: DataModel) {

        sharedPref.put(Constant.PREF_IMAGE, data.image)
        Log.e(this.toString(), "data: $data")

    }
}