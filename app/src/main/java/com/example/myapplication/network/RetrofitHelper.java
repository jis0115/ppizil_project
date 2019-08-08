package com.example.myapplication.network;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitHelper {


    public static RetrofitHelper retrofitHelper = new RetrofitHelper();
    public Retrofit retrofit;

    public static RetrofitHelper getinstance() {
        return retrofitHelper;
    }

    public RetrofitHelper() {
        logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        httpClient = new OkHttpClient.Builder();
        httpClient.connectTimeout(5, TimeUnit.SECONDS);
        httpClient.readTimeout(5, TimeUnit.SECONDS);
        httpClient.writeTimeout(5, TimeUnit.SECONDS);
        httpClient.addInterceptor(logging);/*
        httpClient.addInterceptor(new AddCookiesInterceptor());
        httpClient.addInterceptor(new ReceivedCookiesInterceptor());*/

        retrofit = new Retrofit.Builder()
                .baseUrl("https:www.naver.com")
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient.build())
                .build();
    }

    private HttpLoggingInterceptor logging;
    private OkHttpClient.Builder httpClient;
    
    public SignupApi getSignupApi() {
        return retrofit.create(SignupApi.class);
    }

    public static String getErrorMsg(Response<?> response) {
        try {
            return response.errorBody().string();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
