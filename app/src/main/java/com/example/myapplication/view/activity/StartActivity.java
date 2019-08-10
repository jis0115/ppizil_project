package com.example.myapplication.view.activity;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

import com.example.myapplication.R;
import com.example.myapplication.databinding.ActivityStartBinding;
import com.example.myapplication.view.custom.BaseActivity;

public class StartActivity extends BaseActivity {


    private ActivityStartBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_start);
        init();
    }

    @Override
    public void init() {


        setListener();
    }

    @Override
    public void setListener() {
        binding.loginBtn.setOnClickListener(this);
        binding.signupBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.login_btn:
                StartActivity.this.startNextActivity(LoginActivity.class);
                break;
            case R.id.signup_btn:
                StartActivity.this.startNextActivity(SignActivity.class);
                break;
        }
    }
}
