package com.example.myapplication.view.activity;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

import com.example.myapplication.R;
import com.example.myapplication.data.model.LoginModel;
import com.example.myapplication.databinding.ActivityLoginBinding;
import com.example.myapplication.network.RetrofitHelper;
import com.example.myapplication.network.dto.LoginDto;
import com.example.myapplication.utils.Const;
import com.example.myapplication.utils.MakeLog;
import com.example.myapplication.utils.SharedPreferenceBase;
import com.example.myapplication.view.custom.BaseActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

import static com.example.myapplication.utils.Const.TAG_USER_TOKEN;

public class LoginActivity extends BaseActivity implements View.OnClickListener {


    private ActivityLoginBinding binding;
    private LoginModel loginModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login);
        init();
    }

    @Override
    public void init() {
        loginModel = new LoginModel();
        setListener();
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
                sumValue();
                if (loginModel.checkValid()) {
                    requestLoginApi();
                } else {
                    Const.showToastMsg(LoginActivity.this, "Check your info");
                }
                break;
        }
    }

    public void sumValue() {
        loginModel.getEntity().setNickName(binding.inputNic.getText().toString());
        loginModel.getEntity().setPwd(binding.inputPwd.getText().toString());
    }


    public void requestLoginApi() {


        Call<LoginDto> call = RetrofitHelper.getinstance().getSignupApi().requestLoginApi(loginModel.makeParams());
        Callback<LoginDto> callback = new Callback<LoginDto>() {
            @Override
            public void onResponse(Call<LoginDto> call, Response<LoginDto> response) {
                if (response.isSuccessful()) {
                    LoginDto loginDto = response.body();
                    SharedPreferenceBase.getInstance().setString(TAG_USER_TOKEN, loginDto.getUserToken());
                    Const.showToastMsg(LoginActivity.this, "Success login!");
                    LoginActivity.this.startNextActivity(MainActivity.class);
                } else {
                    Const.showToastMsg(LoginActivity.this, "Fail login Msg : " + response.message());
                }
            }

            @Override
            public void onFailure(Call<LoginDto> call, Throwable t) {
                MakeLog.log("requestLoginApi()", t.getMessage());
            }
        };
        RetrofitHelper.getinstance().enqueueWithRetry(call, callback);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startNextActivity(StartActivity.class);
    }
}
