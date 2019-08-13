package com.example.myapplication.network

import com.example.myapplication.network.dto.LoginDto

import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.FieldMap
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.PartMap

interface SignupApi {


    @Multipart
    @POST("v1/auth/register")
    fun requestSignupApi(@Part file: MultipartBody.Part, @PartMap params: Map<String, RequestBody>): Call<ResponseBody>


    @FormUrlEncoded
    @POST("v1/auth/login")
    fun requestLoginApi(@FieldMap params: Map<String, String>): Call<LoginDto>
}
