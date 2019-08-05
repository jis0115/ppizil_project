package com.example.myapplication.data.model;

import android.graphics.Bitmap;

public interface SignupInterface {

    boolean checkValid();

    Bitmap getBitmap();

    void setBitmap(Bitmap bitmap);

    boolean checkPwdDuplicate();

    void setUserName(String name);

    void setPwd(String pwd);

    void setRePwd(String rePwd);

}
