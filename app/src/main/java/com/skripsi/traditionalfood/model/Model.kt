package com.skripsi.traditionalfood.model

data class ResponseAuthModel(
    val message: String,
    val errors: Boolean,
    val data: DataModel
)

data class ResponseFoodModel(
    val message: String,
    val errors: Boolean,
    val data: List<DataModel>
)

data class DataModel(
    val id: Int,
    val id_image_user: Int?,
    val name: String, //also user
    val category: String, //also user
    val image: String, //also user
    val description: String?,
    val recipe: String?,

    val phone: String?,
    val email: String?,
    val password: String?,
    val type: String?,
)