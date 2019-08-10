package com.example.myapplication.network;

import com.example.myapplication.network.dto.DogListDto;

import retrofit2.Call;
import retrofit2.http.GET;

public interface DogInfoApi {


    @GET("v1/dogs")
    Call<DogListDto> getDogListApi();


}
