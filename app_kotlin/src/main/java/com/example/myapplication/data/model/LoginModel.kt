package com.example.myapplication.data.model

import com.example.myapplication.data.entity.LoginEntity
import com.example.myapplication.utils.Const

import java.util.HashMap

class LoginModel {
    val entity: LoginEntity = LoginEntity()

    fun checkValid(): Boolean = Const.isNotNullAndEmpty(entity.nickName?:"", entity.pwd?:"")
    fun makeParams(): Map<String, String> {
        val params = HashMap<String, String>()
        params["nickname"] = entity.nickName?:""
        params["password"] = entity.pwd?:""
        return params
    }
}
