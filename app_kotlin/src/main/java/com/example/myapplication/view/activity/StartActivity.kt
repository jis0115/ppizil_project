package com.example.myapplication.view.activity

import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil

import com.example.myapplication.R
import com.example.myapplication.databinding.ActivityStartBinding
import com.example.myapplication.view.custom.BaseActivity

class StartActivity : BaseActivity() {


    private var binding: ActivityStartBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_start)
        init()
    }

    override fun init() {


        setListener()
    }

    override fun setListener() {
        binding!!.loginBtn.setOnClickListener(this)
        binding!!.signupBtn.setOnClickListener(this)
    }

    override fun onClick(view: View) {
        val id = view.id
        when (id) {
            R.id.login_btn -> this@StartActivity.startNextActivity(LoginActivity::class.java)
            R.id.signup_btn -> this@StartActivity.startNextActivity(SignActivity::class.java)
        }
    }
}
