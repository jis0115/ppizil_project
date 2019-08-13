package com.example.myapplication.network

import android.util.Base64

import java.io.IOException

import okhttp3.Interceptor
import okhttp3.Response

class AuthorizationInterceptor(private val session: Session) : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val mainResponse = chain.proceed(chain.request())

        if (!RetrofitHelper.isCallSuccess(mainResponse.code())) {
            session.invalidate()
        }

        /*얘는 로그아웃 없이 자동 리프레시*/
        /* Request mainRequest = chain.request();

        if (session.isLoggedIn()) {
            // if response code is 401 or 403, 'mainRequest' has encountered authentication error
            if (mainResponse.code() == 401 || mainResponse.code() == 403) {
                String authKey = getAuthorizationHeader(session.getEmail(), session.getPassword());
                // request to login API to get fresh token
                // synchronously calling login API

                retrofit2.Response<Authorization> loginResponse = apiService.loginAccount(authKey).execute();

                if (loginResponse.isSuccessful()) {
                    // login request succeed, new token generated
                    Authorization authorization = loginResponse.body();
                    // save the new token
                    session.saveToken(authorization.getToken());
                    // retry the 'mainRequest' which encountered an authentication error
                    // add new token into 'mainRequest' header and request again
                    Request.Builder builder = mainRequest.newBuilder().header("x-auth-token", session.getToken()).
                            method(mainRequest.method(), mainRequest.body());
                    mainResponse = chain.proceed(builder.build());
                }
            }
        }*/

        return mainResponse
    }

    companion object {

        /**
         * this method is API implemetation specific
         * might not work with other APIs
         */
        fun getAuthorizationHeader(email: String, password: String): String {
            val credential = "$email:$password"
            return "Basic " + Base64.encodeToString(credential.toByteArray(), Base64.DEFAULT)
        }
    }
}