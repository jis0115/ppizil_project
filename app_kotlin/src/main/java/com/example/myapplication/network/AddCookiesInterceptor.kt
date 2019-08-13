package com.example.myapplication.network

import com.example.myapplication.utils.Const
import com.example.myapplication.utils.MakeLog
import com.example.myapplication.utils.SharedPreferenceBase

import java.io.IOException
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response


class AddCookiesInterceptor : Interceptor {
    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val builder = chain.request().newBuilder()     // Preference에서 cookies를 가져오는 작업을 수행
        val preferences = SharedPreferenceBase.instance.getString(Const.TAG_USER_TOKEN, "")?:""
        if (Const.isNotNullAndEmpty(preferences)) {
            MakeLog.log("받은 토큰", "" + preferences)
            builder.addHeader("Authorization", "Bearer$preferences")
        }
        // Web,Android,iOS 구분을 위해 User-Agent세팅
        //   builder.removeHeader("User-Agent").addHeader("User-Agent", "Android");
        return chain.proceed(builder.build())
    }

}
