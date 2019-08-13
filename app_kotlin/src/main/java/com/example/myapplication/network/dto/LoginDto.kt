package com.example.myapplication.network.dto

import com.google.gson.annotations.SerializedName

class LoginDto {

    @SerializedName("accessToken") // 이렇게 써주는게 편함
    //필수 게터 세터
    var userToken: String? = null // 마이 베리어블 네임
}
