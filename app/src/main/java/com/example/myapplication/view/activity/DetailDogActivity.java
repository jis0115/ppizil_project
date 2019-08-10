package com.example.myapplication.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.adapter.DetailDogAdapter;
import com.example.myapplication.data.model.DogDetailModel;
import com.example.myapplication.databinding.ActivityDetaildogBinding;
import com.example.myapplication.view.custom.BaseActivity;

public class DetailDogActivity extends BaseActivity {

    public static final String TAG_DOG_ENTITY = "dogEntity";


    private ActivityDetaildogBinding binding;
    private DetailDogAdapter adapter;
    private DogDetailModel model;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_detaildog);
        init();
    }

    public void getIntentData() {
        Intent intent = getIntent();
        model.setInfo(intent.getParcelableExtra(TAG_DOG_ENTITY));
    }

    @Override
    public void init() {
        model = new DogDetailModel();
        getIntentData();

        adapter = new DetailDogAdapter(this, model);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        binding.recyclerview.setLayoutManager(linearLayoutManager);
        binding.recyclerview.setAdapter(adapter);
        setListener();
    }

    @Override
    public void setListener() {

    }

    @Override
    public void onClick(View view) {

    }
}
