package com.skripsi.traditionalfood.ui.admin

import android.app.Activity
import android.content.Intent
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.*
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import coil.load
import com.github.dhaval2404.imagepicker.ImagePicker
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.skripsi.traditionalfood.R
import com.skripsi.traditionalfood.model.ResponseAuthModel
import com.skripsi.traditionalfood.model.ResponseFoodModel
import com.skripsi.traditionalfood.network.ApiClient
import com.skripsi.traditionalfood.ui.LoginActivity
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

class InputEditFoodActivity : AppCompatActivity() {
    private lateinit var sharedPref: PreferencesHelper

    private val imgFood: ImageView by lazy { findViewById(R.id.imgFood) }
    private val btnChooseFoto: MaterialButton by lazy { findViewById(R.id.btnChooseFoto) }
    private val tvFoodName: TextInputEditText by lazy { findViewById(R.id.inputFoodName) }
    private val inputCategory: AutoCompleteTextView by lazy { findViewById(R.id.inputCategory) }
    private val tvFoodDesc: TextInputEditText by lazy { findViewById(R.id.inputFoodDescription) }
    private val tvRecipe: TextInputEditText by lazy { findViewById(R.id.inputFoodRecipe) }
    private val btnAdd: MaterialButton by lazy { findViewById(R.id.btnAddFood) }

    private var reqBody: RequestBody? = null
    private var partImage: MultipartBody.Part? = null

    private lateinit var intentType: String
    private var id: String = ""

    var categoryFood: ArrayList<String> =
        arrayListOf("makanan", "kue")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_input_edit_food)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        sharedPref = PreferencesHelper(this)

        intentType = intent.getStringExtra("type").toString()

        val adapterListCategory = ArrayAdapter(
            applicationContext,
            R.layout.support_simple_spinner_dropdown_item,
            categoryFood
        )
        inputCategory.setAdapter(adapterListCategory)

        if (intentType == "edit") {
            supportActionBar?.title = "Edit"

            btnAdd.setText("Edit")

            id = intent.getStringExtra("id").toString()
            val name = intent.getStringExtra("name")
            val categoryFood = intent.getStringExtra("category")
            val desc = intent.getStringExtra("description")
            val recipe = intent.getStringExtra("recipe")
            val img = intent.getStringExtra("image")

            val linkImage = "${Constant.URL_IMAGE_FOOD}${img}"
            imgFood.load(linkImage)

            tvFoodName.setText(name)
            tvFoodDesc.setText(desc)
            tvRecipe.setText(recipe)
            btnAdd.setOnClickListener {

                if (tvFoodName.text.toString().isEmpty() || tvFoodDesc.text.toString()
                        .isEmpty() || tvRecipe.text.toString()
                        .isEmpty() || inputCategory.text.toString()
                        .isEmpty()
                ) {
                    Toast.makeText(applicationContext, "tidak boleh kosong", Toast.LENGTH_SHORT)
                        .show()
                } else {
                    edit(
                        tvFoodName.text.toString(),
                        inputCategory.text.toString(),
                        tvFoodDesc.text.toString(),
                        tvRecipe.text.toString(),
                        id!!
                    )
                }
            }

        } else {
            supportActionBar?.title = "Upload Makanan"

            btnAdd.setText("Tambah")

            btnAdd.setOnClickListener {
                if (tvFoodName.text.toString().isEmpty() || tvFoodDesc.text.toString()
                        .isEmpty() || tvRecipe.text.toString()
                        .isEmpty() || inputCategory.text.toString()
                        .isEmpty()
                ) {
                    Toast.makeText(applicationContext, "tidak boleh kosong", Toast.LENGTH_SHORT)
                        .show()

                } else if (partImage == null) {
                    Toast.makeText(applicationContext, "pilih gambar", Toast.LENGTH_SHORT).show()

                } else {
                    upload(
                        tvFoodName.text.toString(),
                        inputCategory.text.toString(),
                        tvFoodDesc.text.toString(),
                        tvRecipe.text.toString(),
                    )
                }
            }
        }

        btnChooseFoto.setOnClickListener {
            ImagePicker.with(this)
                .cropSquare()
                .compress(1024)         //Final image size will be less than 1 MB(Optional)
                .createIntent { intent ->
                    startForProfileImageResult.launch(intent)
                }
        }
    }

    private fun edit(
        name: String,
        category: String,
        desc: String,
        recipe: String,
        id: String
    ) {

        ApiClient.instances.editFood(name, category, desc, recipe, id.toInt())
            .enqueue(object :
                Callback<ResponseFoodModel> {
                override fun onResponse(
                    call: Call<ResponseFoodModel>,
                    response: Response<ResponseFoodModel>
                ) {
                    val message = response.body()?.message
                    if (response.isSuccessful) {
                        if (message == "Success") {
                            Toast.makeText(
                                applicationContext,
                                message.toString(),
                                Toast.LENGTH_SHORT
                            ).show()

                            finish()

                        } else {
                            Toast.makeText(applicationContext, "gagal", Toast.LENGTH_SHORT).show()
                        }
                    } else {
                        Toast.makeText(applicationContext, "gagal", Toast.LENGTH_SHORT).show()

                    }
                }

                override fun onFailure(call: Call<ResponseFoodModel>, t: Throwable) {
                    Toast.makeText(applicationContext, t.message.toString(), Toast.LENGTH_SHORT)
                        .show()
                }
            })

    }

    private fun editImage(
        id: String,
    ) {
        val partId: RequestBody = id.toRequestBody("text/plain".toMediaTypeOrNull())

        ApiClient.instances.editImgFood(partImage!!, partId)
            .enqueue(object :
                Callback<ResponseFoodModel> {
                override fun onResponse(
                    call: Call<ResponseFoodModel>,
                    response: Response<ResponseFoodModel>
                ) {
                    val message = response.body()?.message
                    if (response.isSuccessful) {
                        if (message == "Success") {
                            Toast.makeText(
                                applicationContext,
                                message.toString(),
                                Toast.LENGTH_SHORT
                            ).show()

                        } else {
                            Toast.makeText(applicationContext, "gagal", Toast.LENGTH_SHORT).show()
                        }
                    } else {
                        Toast.makeText(applicationContext, "gagal", Toast.LENGTH_SHORT).show()

                    }
                }

                override fun onFailure(call: Call<ResponseFoodModel>, t: Throwable) {
                    Toast.makeText(applicationContext, t.message.toString(), Toast.LENGTH_SHORT)
                        .show()
                }
            })
    }

    private fun upload(
        name: String,
        category: String,
        desc: String,
        recipe: String,
    ) {
        val idAdmin = sharedPref.getString(Constant.PREF_ID)

        val partId: RequestBody = idAdmin!!.toRequestBody("text/plain".toMediaTypeOrNull())
        val partName: RequestBody = name.toRequestBody("text/plain".toMediaTypeOrNull())
        val partCategory: RequestBody = category.toRequestBody("text/plain".toMediaTypeOrNull())
        val parDesc: RequestBody = desc.toRequestBody("text/plain".toMediaTypeOrNull())
        val partRecipe: RequestBody = recipe.toRequestBody("text/plain".toMediaTypeOrNull())

        ApiClient.instances.uploadFood(
            partId,
            partName,
            partCategory,
            partImage!!,
            parDesc,
            partRecipe
        )
            .enqueue(object :
                Callback<ResponseFoodModel> {
                override fun onResponse(
                    call: Call<ResponseFoodModel>,
                    response: Response<ResponseFoodModel>
                ) {
                    val message = response.body()?.message
                    if (response.isSuccessful) {
                        if (message == "Success") {
                            Toast.makeText(
                                applicationContext,
                                message.toString(),
                                Toast.LENGTH_SHORT
                            ).show()

                            finish()
                        } else {
                            Toast.makeText(applicationContext, "gagal", Toast.LENGTH_SHORT).show()
                        }
                    } else {
                        Toast.makeText(applicationContext, "gagal", Toast.LENGTH_SHORT).show()

                    }
                }

                override fun onFailure(call: Call<ResponseFoodModel>, t: Throwable) {
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
                imgFood.setImageBitmap(BitmapFactory.decodeFile(image.absolutePath))

                Log.e("image format: ", "uri = $fileUri")
                Log.e("image format: ", "file path = $image")
                Log.e("image format: ", "file absolute path = ${image.absolutePath}")

                reqBody = image.asRequestBody("image/*".toMediaTypeOrNull())

                partImage = MultipartBody.Part.createFormData("image", image.name, reqBody!!)

                if (intentType == "edit") {
                    editImage(id)
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
}