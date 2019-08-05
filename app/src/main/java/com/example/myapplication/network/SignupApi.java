package com.example.myapplication.network;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;

public interface SignupApi {


    @GET("")
    Call<ResponseBody> requestSignupApi();
}
