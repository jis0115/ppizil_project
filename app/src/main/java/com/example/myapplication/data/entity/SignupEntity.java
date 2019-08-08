package com.example.myapplication.data.entity;

import android.graphics.Bitmap;

public class SignupEntity {
    private String userName;
    private int age;
    private String addr;
    private String password;
    private String rePassword;
    private String gender="M"; // 성별은 , 레이디오 버튼이  디폴드 값 지정이 애매하기 때문에



    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRePassword() {
        return rePassword;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setRePassword(String rePassword) {
        this.rePassword = rePassword;
    }
}
