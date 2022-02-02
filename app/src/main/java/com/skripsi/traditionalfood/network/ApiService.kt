package com.skripsi.traditionalfood.network

import com.skripsi.traditionalfood.model.ResponseAuthModel
import com.skripsi.traditionalfood.model.ResponseFoodModel
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*


interface ApiService {

    //user
    @FormUrlEncoded
    @POST("login")
    fun login(
        @Field("email") email: String,
        @Field("password") password: String,
        @Field("type") type: String,
    ): Call<ResponseAuthModel>

    @Multipart
    @POST("register")
    fun registration(
        @Part("name") name: RequestBody,
        @Part parts: MultipartBody.Part,
        @Part("phone") phone: RequestBody,
        @Part("email") email: RequestBody,
        @Part("password") password: RequestBody,
    ): Call<ResponseAuthModel>

    @FormUrlEncoded
    @POST("edit")
    fun editUser(
        @Field("name") name: String,
        @Field("phone") phone: String,
        @Field("email") email: String,
        @Field("password") password: String,
        @Field("id") id: Int,
    ): Call<ResponseAuthModel>

    @Multipart
    @POST("edit-image")
    fun editImgUser(
        @Part parts: MultipartBody.Part,
        @Part("id") id: RequestBody,
    ): Call<ResponseAuthModel>

    //food
    @FormUrlEncoded
    @POST("show-food")
    fun showFood(
        @Field("search") search: String,
    ): Call<ResponseFoodModel>

    @Multipart
    @POST("upload-food")
    fun uploadFood(
        @Part("id_admin_user") idAdmin: RequestBody,
        @Part("name") name: RequestBody,
        @Part parts: MultipartBody.Part,
        @Part("description") description: RequestBody,
        @Part("recipe") recipe: RequestBody,
    ): Call<ResponseFoodModel>

    @FormUrlEncoded
    @POST("edit-food")
    fun editFood(
        @Field("name") name: String,
        @Field("description") description: String,
        @Field("recipe") recipe: String,
        @Field("id") id: Int,
    ): Call<ResponseFoodModel>

    @Multipart
    @POST("edit-image-food")
    fun editImgFood(
        @Part parts: MultipartBody.Part,
        @Part("id") id: RequestBody,
    ): Call<ResponseFoodModel>

    @FormUrlEncoded
    @POST("delete-food")
    fun deletefood(
        @Field("id") id: Int,
    ): Call<ResponseFoodModel>
}