package com.example.myapplication.network;

import com.example.myapplication.network.dto.LoginDto;

import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;

public interface SignupApi {


    @Multipart
    @POST("v1/auth/register")
    Call<ResponseBody> requestSignupApi(@Part MultipartBody.Part file, @PartMap Map<String, RequestBody> params);


    @FormUrlEncoded
    @POST("v1/auth/login")
    Call<LoginDto> requestLoginApi(@FieldMap Map<String, String> params);
}
