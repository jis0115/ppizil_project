package com.example.myapplication.view.activity;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

import com.example.myapplication.R;
import com.example.myapplication.databinding.ActivitySignupBinding;
import com.example.myapplication.view.custom.BaseActivity;

public class SignActivity extends BaseActivity implements View.OnClickListener {


    private ActivitySignupBinding binding;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_signup);
        init();
        setListener();
    }

    @Override
    public void init() {

    }

    @Override
    public void setListener() {
        binding.confirmBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.confirm_btn:
                startNextActivity(MainActivity.class);
                break;
        }
    }
}
