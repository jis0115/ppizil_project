package com.example.myapplication.network.dto;

import com.google.gson.annotations.SerializedName;

public class LoginDto {

    @SerializedName("accessToken") // 이렇게 써주는게 편함
    private String userToken; // 마이 베리어블 네임

    public String getUserToken() { //필수 게터 세터
        return userToken;
    }

    public void setUserToken(String userToken) {
        this.userToken = userToken;
    }
}
