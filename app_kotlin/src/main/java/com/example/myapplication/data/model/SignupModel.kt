package com.example.myapplication.data.model

import com.example.myapplication.utils.Const
import com.example.myapplication.data.entity.SignupEntity

import java.io.File
import java.util.HashMap

import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody

class SignupModel {
    var filePath: String? = null
    val signupEntity: SignupEntity

    val isLenght4: Boolean
        get() = if (signupEntity.password!!.length == PWD_LENGTH && signupEntity.rePassword!!.length == PWD_LENGTH) {
            true
        } else {
            false
        }


    val imageFile: File
        get() = File(filePath!!)

    val multipartObj: MultipartBody.Part
        get() = Const.prepareFilePart(imageFile)

    val convertToRequestBody: RequestBody
        get() = RequestBody.create(MediaType.parse("text/plain"), Const.toJsonString(signupEntity))

    init {
        signupEntity = SignupEntity()
    }

    fun checkValid(): Boolean {
        return Const.isNotNullAndEmpty(
                        signupEntity.userName?:"",
                        signupEntity.age.toString() + "",
                        signupEntity.addr?:"",
                        signupEntity.password?:"",
                        signupEntity.rePassword?:"",
                        signupEntity.gender,
                        filePath?:"") && isLenght4
    }


    fun checkPwdDuplicate(): Boolean {
        return if (signupEntity.password == signupEntity.rePassword) {
            true
        } else {
            false
        }
    }


    fun makeParams(): Map<String, RequestBody> {
        val params = HashMap<String, RequestBody>()
        params["nickname"] = Const.makeRequestBody(signupEntity.userName?:"")
        params["password"] = Const.makeRequestBody(signupEntity.password?:"")
        val requestBody = RequestBody.create(MediaType.parse("text/plain"), Const.toJsonString(params))
        return params
    }

    companion object {

        val PWD_LENGTH = 4
    }


}
