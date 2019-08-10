package com.example.myapplication.view.custom;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public abstract class BaseActivity extends AppCompatActivity implements View.OnClickListener {


    public abstract void init();

    public abstract void setListener();


    public void startNextActivity(Class<?> className) {
        Intent intent = new Intent(this, className);
        startActivity(intent);
        finish();
    }

    public void startIncludePayloadActivity(Intent intent) {
        startActivity(intent);
    }

    public void startNextActivityForResult(Intent intent, int requestCode) {
        startActivityForResult(intent, requestCode);
    }
}
