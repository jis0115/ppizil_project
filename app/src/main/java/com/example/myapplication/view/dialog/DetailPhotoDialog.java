package com.example.myapplication.view.dialog;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.DialogFragment;

import com.example.myapplication.R;
import com.example.myapplication.data.model.DetailPhotoModel;
import com.example.myapplication.databinding.DialogDetailPhotoBinding;
import com.example.myapplication.utils.Const;
import com.ppizil.photopicker.view.PhotoPickerActivity;

public class DetailPhotoDialog extends DialogFragment implements View.OnClickListener {


    public static DetailPhotoDialog newInstance(Bundle bundle) {
        DetailPhotoDialog fragment = new DetailPhotoDialog();
        fragment.setArguments(bundle);
        return fragment;
    }

    private DialogDetailPhotoBinding binding;
    private Bundle bundle;
    private DetailPhotoModel detailPhotoModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.dialog_detail_photo, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init();
    }

    @Override
    public void onResume() {
        super.onResume();
        setImage();
        sendMultipart();
    }

    public void init() {
        bundle = getArguments();
        detailPhotoModel = new DetailPhotoModel();
        detailPhotoModel.getDetailPhotoEntity().setFilePath(bundle.getString(PhotoPickerActivity.TAG_FILEPATH));

        setListener();
    }

    public void setListener() {
        binding.cancelBtn.setOnClickListener(this);
        binding.confirmBtn.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {

        int id = view.getId();
        switch (id) {
            case R.id.cancel_btn:
                dismiss();
                break;
            case R.id.confirm_btn:
                break;
        }
    }

    public void setImage() {
        Const.setImageLoadGeneral(binding.image, detailPhotoModel.getDetailPhotoEntity().getFilePath());
    }


    public void sendMultipart() {
        Const.setWrapContentDialogSize(this);
    }
}
