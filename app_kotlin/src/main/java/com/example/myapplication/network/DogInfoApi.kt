package com.example.myapplication.network

import com.example.myapplication.network.dto.DogListDto

import retrofit2.Call
import retrofit2.http.GET

interface DogInfoApi {


    @get:GET("v1/dogs")
    val dogListApi: Call<DogListDto>


}
