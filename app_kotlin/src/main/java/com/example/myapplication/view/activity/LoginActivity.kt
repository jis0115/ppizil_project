package com.example.myapplication.view.activity

import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil

import com.example.myapplication.R
import com.example.myapplication.data.model.LoginModel
import com.example.myapplication.databinding.ActivityLoginBinding
import com.example.myapplication.network.RetrofitHelper
import com.example.myapplication.network.dto.LoginDto
import com.example.myapplication.utils.Const
import com.example.myapplication.utils.MakeLog
import com.example.myapplication.utils.SharedPreferenceBase
import com.example.myapplication.view.custom.BaseActivity

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit

import com.example.myapplication.utils.Const.TAG_USER_TOKEN

class LoginActivity : BaseActivity(), View.OnClickListener {


    private var binding: ActivityLoginBinding? = null
    private var loginModel: LoginModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login)
        init()
    }

    override fun init() {
        loginModel = LoginModel()
        setListener()
    }

    override fun setListener() {
        binding!!.confirmBtn.setOnClickListener(this)
    }

    override fun onClick(view: View) {
        val id = view.id
        when (id) {
            R.id.confirm_btn -> {
                sumValue()
                if (loginModel!!.checkValid()) {
                    requestLoginApi()
                } else {
                    Const.showToastMsg(this@LoginActivity, "Check your info")
                }
            }
        }
    }

    fun sumValue() {
        loginModel!!.entity.nickName = binding!!.inputNic.text.toString()
        loginModel!!.entity.pwd = binding!!.inputPwd.text.toString()
    }


    fun requestLoginApi() {


        val call = RetrofitHelper.getinstance().getSignupApi().requestLoginApi(loginModel!!.makeParams())
        val callback = object : Callback<LoginDto> {
            override fun onResponse(call: Call<LoginDto>, response: Response<LoginDto>) {
                if (response.isSuccessful) {
                    val loginDto = response.body()
                    SharedPreferenceBase.instance.setString(TAG_USER_TOKEN, loginDto!!.userToken!!)
                    Const.showToastMsg(this@LoginActivity, "Success login!")
                    this@LoginActivity.startNextActivity(MainActivity::class.java)
                } else {
                    Const.showToastMsg(this@LoginActivity, "Fail login Msg : " + response.message())
                }
            }

            override fun onFailure(call: Call<LoginDto>, t: Throwable) {
                MakeLog.log("requestLoginApi()", t.message!!)
            }
        }
        RetrofitHelper.enqueueWithRetry<LoginDto>(call, callback)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        startNextActivity(StartActivity::class.java)
    }
}
