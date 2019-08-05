package com.example.myapplication.data.model;

import android.graphics.Bitmap;

import com.example.myapplication.Utils.Const;
import com.example.myapplication.data.entity.SignupEntity;

public class SignupModel implements SignupInterface {

    private SignupEntity signupEntity;

    public SignupModel() {
        signupEntity = new SignupEntity();
    }

    public SignupEntity getSignupEntity() {
        return signupEntity;
    }

    @Override
    public boolean checkValid() {
        if (Const.isNotNullAndEmpty(
                signupEntity.getUserName(),
                signupEntity.getPassword(),
                signupEntity.getRePassword() )) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public Bitmap getBitmap() {
        return signupEntity.getBitmap();
    }

    @Override
    public void setBitmap(Bitmap bitmap) {
        signupEntity.setBitmap(bitmap);
    }


    @Override
    public boolean checkPwdDuplicate() {
        if (signupEntity.getPassword().equals(signupEntity.getRePassword())) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void setUserName(String name) {
        signupEntity.setUserName(name);

    }

    @Override
    public void setPwd(String pwd) {
        signupEntity.setPassword(pwd);
    }

    @Override
    public void setRePwd(String rePwd) {
        signupEntity.setRePassword(rePwd);
    }


}
