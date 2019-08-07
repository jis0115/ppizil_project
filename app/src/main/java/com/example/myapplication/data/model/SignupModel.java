package com.example.myapplication.data.model;

import android.graphics.Bitmap;

import com.example.myapplication.Utils.Const;
import com.example.myapplication.data.entity.SignupEntity;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class SignupModel {

    public static final int PWD_LENGTH = 4;
    private String filePath;
    private SignupEntity signupEntity;

    public SignupModel() {
        signupEntity = new SignupEntity();
    }

    public SignupEntity getSignupEntity() {
        return signupEntity;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public boolean checkValid() {
        if (Const.isNotNullAndEmpty(
                signupEntity.getUserName(),
                signupEntity.getAddr(),
                signupEntity.getPassword(),
                signupEntity.getRePassword(),
                filePath) &&
                isLenght4()) {
            return true;
        } else {
            return false;
        }
    }

    public boolean isLenght4() {
        if (getSignupEntity().getPassword().length() == PWD_LENGTH &&
                getSignupEntity().getRePassword().length() == PWD_LENGTH) {
            return true;
        } else {
            return false;
        }
    }


    public boolean checkPwdDuplicate() {
        if (signupEntity.getPassword().equals(signupEntity.getRePassword())) {
            return true;
        } else {
            return false;
        }
    }




    public File getImageFile() {
        File file = new File(filePath);
        return file;
    }

    public MultipartBody.Part getMultipartObj() {
        return Const.prepareFilePart(getImageFile());
    }

    public RequestBody getConvertToRequestBody() {
        return RequestBody.create(MediaType.parse("text/plain"), Const.toJsonString(getSignupEntity()));
    }

}
