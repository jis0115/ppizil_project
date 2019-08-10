package com.example.myapplication.view.activity;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.InputType;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

import com.bumptech.glide.Glide;
import com.example.myapplication.R;
import com.example.myapplication.utils.Const;
import com.example.myapplication.utils.MakeLog;
import com.example.myapplication.data.entity.SignupTitles;
import com.example.myapplication.data.model.SignupModel;
import com.example.myapplication.databinding.ActivitySignupBinding;
import com.example.myapplication.network.RetrofitHelper;
import com.example.myapplication.view.custom.BaseActivity;
import com.example.myapplication.view.dialog.DetailPhotoDialog;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;
import com.ppizil.photopicker.view.PhotoPickerActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.myapplication.view.activity.MainActivity.REQUEST_PHOTOPICK;
import static com.ppizil.photopicker.view.PhotoPickerActivity.RESULT_SUCCESS;

public class SignActivity extends BaseActivity implements View.OnClickListener, RadioGroup.OnCheckedChangeListener {

    private static final String TAG_IMAGE_TYPE = "image/*";

    private ActivitySignupBinding binding;
    private SignupTitles signupTitles;
    private List<TextView> titlesList;

    private SignupModel signupModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_signup);
        init();
        setListener();
    }

    @Override
    public void init() {

        titlesList = new ArrayList<>();
        signupTitles = new SignupTitles(getResources().getStringArray(R.array.signup_title));
        signupModel = new SignupModel();
        addTitleList();
        setTitlesSet();
    }


    //타이블뷰를 리스트로 관리
    public void addTitleList() {
        binding.includeAge.inputValue.setInputType(InputType.TYPE_CLASS_NUMBER);
        titlesList.add(binding.includeNickname.textTitle);
        titlesList.add(binding.includeAge.textTitle);
        titlesList.add(binding.includeAddr.textTitle);
        titlesList.add(binding.includePwd.textTitle);
        titlesList.add(binding.includeRePwd.textTitle);
    }

    //Arrays.xml data ->  set
    public void setTitlesSet() { // 입력필드 타이틀 수정
        for (int i = 0; i < titlesList.size(); i++) {
            titlesList.get(i).setText(signupTitles.getTitles()[i]);
        }
    }

    @Override
    public void setListener() {
        binding.confirmBtn.setOnClickListener(this);
        binding.selectProfileBtn.setOnClickListener(this);
        binding.radioGroup.setOnCheckedChangeListener(this);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.confirm_btn:
                sumValues();
                if (signupModel.checkValid()) {
                    if (signupModel.checkPwdDuplicate()) {
                        MakeLog.log("객체 시리얼라이즈", Const.toJsonString(signupModel.getSignupEntity()));
                        Const.showToastMsg(SignActivity.this, "Success!");
                        // startNextActivity(MainActivity.class);
                        requestRegistApi();
                    } else {
                        Const.showToastMsg(SignActivity.this, "Not dulplicated Pwd!");
                    }
                } else {
                    Const.showToastMsg(SignActivity.this, "Check your info! ");
                }

                break;
            case R.id.select_profile_btn:
                TedPermission.with(SignActivity.this)
                        .setPermissionListener(permissionListener)
                        .setPermissions(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.CAMERA)
                        .check();
                break;
        }
    }

    //권한
    public PermissionListener permissionListener = new PermissionListener() {
        @Override
        public void onPermissionGranted() {
            RendingAlbum();
        }

        @Override
        public void onPermissionDenied(List<String> deniedPermissions) {
            Const.showToastMsg(SignActivity.this, "Check permission!!");
        }
    };


    //


    public void RendingAlbum() {
        Intent intent = new Intent(this, PhotoPickerActivity.class);
        startNextActivityForResult(intent, Const.TAG_ALBUM);

      /*  Intent intent = new Intent(Intent.ACTION_PICK);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType(TAG_IMAGE_TYPE);
        startActivityForResult(intent, Const.TAG_ALBUM);*/
    }


    public void sumValues() {
        signupModel.getSignupEntity().setUserName(binding.includeNickname.inputValue.getText().toString());

        String age = binding.includeAge.inputValue.getText().toString();
        if (Const.isInteger(age)) {
            signupModel.getSignupEntity().setAge(Integer.parseInt(age));
        } else {
            Const.showToastMsg(SignActivity.this, "Check your Age");
        }
        signupModel.getSignupEntity().setAddr(binding.includeAddr.inputValue.getText().toString());
        signupModel.getSignupEntity().setPassword(binding.includePwd.inputValue.getText().toString());
        signupModel.getSignupEntity().setRePassword(binding.includeRePwd.inputValue.getText().toString());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //request => startActivity할때 넣어준 값
        // result => 타겟 Activity에서 finish 전 setResult(변수)값
        if (data != null) {
            switch (resultCode) {
                case PhotoPickerActivity.RESULT_SUCCESS:
                    String path = data.getStringExtra(PhotoPickerActivity.TAG_FILEPATH);
                    signupModel.setFilePath(path);
                    Const.setImageLoadGeneral(binding.profileImage, path);
                    break;
            }
        }
    }

    public void requestRegistApi() {

        Call<ResponseBody> call = RetrofitHelper.
                getinstance().
                getSignupApi().
                requestSignupApi(signupModel.getMultipartObj(), signupModel.makeParams());
        Callback<ResponseBody> callback = new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    Const.showToastMsg(SignActivity.this, "Success Regis!");
                    SignActivity.this.startNextActivity(LoginActivity.class);
                } else {
                    String errorMsg = RetrofitHelper.getErrorMsg(response);
                    Const.showToastMsg(SignActivity.this, errorMsg);
                    MakeLog.log("requestRegistApi() Fail", errorMsg);
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                MakeLog.log("에러", t.getMessage());
            }
        };
        RetrofitHelper.getinstance().enqueueWithRetry(call, callback);
    }

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int i) {
        int id = radioGroup.getCheckedRadioButtonId();
        switch (id) {
            case R.id.male:
                signupModel.getSignupEntity().setGender("M"); //남성
                break;
            case R.id.female:
                signupModel.getSignupEntity().setGender("F");// 여성
                break;
        }
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startNextActivity(StartActivity.class);
    }
}
