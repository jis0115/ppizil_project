package com.example.myapplication.view.activity;

import android.Manifest;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.example.myapplication.R;
import com.example.myapplication.Utils.Const;
import com.example.myapplication.Utils.MakeLog;
import com.example.myapplication.data.entity.SignupTitles;
import com.example.myapplication.data.model.SignupModel;
import com.example.myapplication.databinding.ActivitySignupBinding;
import com.example.myapplication.view.custom.BaseActivity;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;

import java.util.ArrayList;
import java.util.List;

public class SignActivity extends BaseActivity implements View.OnClickListener {


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

    public void addTitleList() {
        titlesList.add(binding.includeNickname.textTitle);
        titlesList.add(binding.includePwd.textTitle);
        titlesList.add(binding.includeRePwd.textTitle);
    }

    public void setTitlesSet() { // 입력필드 타이틀 수정
        for (int i = 0; i < titlesList.size(); i++) {
            titlesList.get(i).setText(signupTitles.getTitles()[i]);
        }
    }

    @Override
    public void setListener() {
        binding.confirmBtn.setOnClickListener(this);
        binding.selectProfileBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.confirm_btn:
                sumValues();
                if (checkValid() && checkImageFile() != null) {
                    if (checkPwdDuplicate()) {
                        Const.showToastMsg(SignActivity.this, "Success!");
                        MakeLog.log("SignupEntity", Const.toJsonString(signupModel.getSignupEntity()));
                        //startNextActivity(MainActivity.class);

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

    public boolean checkPwdDuplicate() {
        return signupModel.checkPwdDuplicate();
    }


    public void RendingAlbum() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType(MediaStore.Images.Media.CONTENT_TYPE);
        startActivityForResult(intent, Const.TAG_ALBUM);
    }

    public void sumValues() {
        signupModel.setUserName(binding.includeNickname.inputValue.getText().toString());
        signupModel.setPwd(binding.includePwd.inputValue.getText().toString());
        signupModel.setRePwd(binding.includeRePwd.inputValue.getText().toString());
    }

    public boolean checkValid() {
        return signupModel.checkValid();
    }

    public Bitmap checkImageFile() {
        return signupModel.getBitmap();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //request => startActivity할때 넣어준 값
        // result => 타겟 Activity에서 finish 전 setResult(변수)값
        if (requestCode == Const.TAG_ALBUM) {
            if (data != null && data.getData() != null) {
                Uri uri = data.getData();
                saveBitmap(uri);
            } else {

            }
        }
    }

    public void saveBitmap(Uri uri) {
        Glide.with(this)
                .asBitmap()
                .load(uri.toString())
                .into(new CustomTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                        binding.profileImage.setScaleType(ImageView.ScaleType.FIT_XY);
                        binding.profileImage.setImageBitmap(resource);
                        if (resource != null) {
                            signupModel.setBitmap(resource);
                        } else {
                            Const.showToastMsg(SignActivity.this, "Photo is null!");
                        }
                    }

                    @Override
                    public void onLoadCleared(@Nullable Drawable placeholder) {
                    }
                });


    }
}
