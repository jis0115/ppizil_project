package com.example.myapplication.data.model;

import com.example.myapplication.data.entity.LoginEntity;
import com.example.myapplication.utils.Const;

import java.util.HashMap;
import java.util.Map;

public class LoginModel {
    private LoginEntity entity;

    public LoginModel() {

        entity = new LoginEntity();
    }

    public boolean checkValid() {
        if (Const.isNotNullAndEmpty(entity.getNickName(), entity.getPwd())) {
            return true;
        }
        return false;
    }

    public LoginEntity getEntity() {
        return entity;
    }


    public Map<String, String> makeParams() {
        Map<String, String> params = new HashMap<>();
        params.put("nickname", entity.getNickName());
        params.put("password", entity.getPwd());
        return params;
    }
}
