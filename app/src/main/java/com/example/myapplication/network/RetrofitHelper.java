package com.example.myapplication.network;

import com.example.myapplication.BuildConfig;
import com.example.myapplication.ProjectApplication;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitHelper {

    public static final int DEFAULT_RETRIES = 3;

    public static RetrofitHelper retrofitHelper = new RetrofitHelper();
    public Retrofit retrofit;
    private SignupApi signupApi;
    private DogInfoApi dogInfoApi;

    public ErrorCallback errorCallback;

    public void setErrorCallback(ErrorCallback errorCallback) {
        this.errorCallback = errorCallback;
    }


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
        httpClient.addInterceptor(logging);
        httpClient.addInterceptor(new AuthorizationInterceptor(ProjectApplication.getSession()));
        httpClient.addInterceptor(new AddCookiesInterceptor());
        /*

        httpClient.addInterceptor(new AddCookiesInterceptor());
        httpClient.addInterceptor(new ReceivedCookiesInterceptor());*/


    }

    private HttpLoggingInterceptor logging;
    private OkHttpClient.Builder httpClient;


    public SignupApi getSignupApi() {
        if (signupApi == null) {
            signupApi = provideRetrofit().create(SignupApi.class);
        }
        return signupApi;
    }

    public DogInfoApi getDoginfoApi() {
        if (dogInfoApi == null) {
            dogInfoApi = provideRetrofit().create(DogInfoApi.class);
        }
        return dogInfoApi;
    }


    public static String getErrorMsg(Response<?> response) {
        try {
            return response.errorBody().string();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    private Retrofit provideRetrofit() {
        return retrofit = new Retrofit.Builder()
                .baseUrl(BuildConfig.BASEURL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient.build())
                .build();
    }

    public static boolean isCallSuccess(int code) {
        return (code >= 200 && code < 400);

    }

    public static <T> void enqueueWithRetry(Call<T> call, final int retryCount, final Callback<T> callback) {
        RetryableCallback retryableCallback = new RetryableCallback<T>(call, retryCount) {

            @Override
            public void onFinalResponse(Call<T> call, Response<T> response) {
                callback.onResponse(call, response);
            }

            @Override
            public void onFinalFailure(Call<T> call, Throwable t) {
                callback.onFailure(call, t);
            }
        };
        retryableCallback.setCallback(RetrofitHelper.getinstance().errorCallback);
        call.enqueue(retryableCallback);
    }

    public static <T> void enqueueWithRetry(Call<T> call, final Callback<T> callback) {
        enqueueWithRetry(call, DEFAULT_RETRIES, callback);
    }
}
