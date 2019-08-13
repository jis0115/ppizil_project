package com.example.myapplication.network

import com.example.myapplication.BuildConfig
import com.example.myapplication.ProjectApplication
import com.google.gson.Gson

import java.io.IOException
import java.util.concurrent.TimeUnit

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitHelper {
//    lateinit var retrofit: Retrofit
    private var signupApi: SignupApi? = null
    private var dogInfoApi: DogInfoApi? = null

    lateinit var errorCallback: ErrorCallback

    private val logging: HttpLoggingInterceptor
    private val httpClient: OkHttpClient.Builder

    val doginfoApi: DogInfoApi
        get() {
            if (dogInfoApi == null) {
                dogInfoApi = provideRetrofit().create(DogInfoApi::class.java)
            }
            return dogInfoApi!!
        }


    init {
        logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY
        httpClient = OkHttpClient.Builder()
        httpClient.connectTimeout(5, TimeUnit.SECONDS)
        httpClient.readTimeout(5, TimeUnit.SECONDS)
        httpClient.writeTimeout(5, TimeUnit.SECONDS)
        httpClient.addInterceptor(logging)
        httpClient.addInterceptor(AuthorizationInterceptor(ProjectApplication.getSession()))
        httpClient.addInterceptor(AddCookiesInterceptor())
        /*

        httpClient.addInterceptor(new AddCookiesInterceptor());
        httpClient.addInterceptor(new ReceivedCookiesInterceptor());*/


    }


    fun getSignupApi(): SignupApi {
        if (signupApi == null) {
            signupApi = provideRetrofit().create(SignupApi::class.java)
        }
        return signupApi!!
    }


    private fun provideRetrofit(): Retrofit {
//        return retrofit = Retrofit.Builder
        return Retrofit.Builder()
                .baseUrl(BuildConfig.BASEURL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient.build())
                .build()
        /*
        return retrofit = Retrofit.Builder()
                .baseUrl(BuildConfig.BASEURL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient.build())
                .build()
                */
    }

    companion object {

        val DEFAULT_RETRIES = 3

        var retrofitHelper = RetrofitHelper()


        fun getinstance(): RetrofitHelper {
            return retrofitHelper
        }


        fun getErrorMsg(response: Response<*>): String? {
            try {
                return response.errorBody()!!.string()

            } catch (e: IOException) {
                e.printStackTrace()
            }

            return null
        }

        fun isCallSuccess(code: Int): Boolean {
            return code >= 200 && code < 400

        }

        fun <T> enqueueWithRetry(call: Call<T>, retryCount: Int, callback: Callback<T>) {
            val retryableCallback = object : RetryableCallback<T>(call, retryCount) {

                override fun onFinalResponse(call: Call<T>, response: Response<T>) {
                    callback.onResponse(call, response)
                }

                override fun onFinalFailure(call: Call<T>, t: Throwable) {
                    callback.onFailure(call, t)
                }
            }
            retryableCallback.setCallback(RetrofitHelper.getinstance().errorCallback)
            call.enqueue(retryableCallback)
        }

        fun <T> enqueueWithRetry(call: Call<T>, callback: Callback<T>) {
            enqueueWithRetry(call, DEFAULT_RETRIES, callback)
        }
    }
}
