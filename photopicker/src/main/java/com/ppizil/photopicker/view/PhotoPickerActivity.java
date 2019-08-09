package com.ppizil.photopicker.view;

import android.content.Intent;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.Observable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.ppizil.photopicker.R;
import com.ppizil.photopicker.databinding.ActivityPhotopickerBinding;
import com.ppizil.photopicker.model.PhotoListEntity;
import com.ppizil.photopicker.viewmodel.PhotoPickerViewModel;

public class PhotoPickerActivity extends AppCompatActivity {

    public static final int RESULT_SUCCESS = 1010;
    public static final int RESULT_FAIL = -1;
    public static final String TAG_FILEPATH = "filePath";

    private ActivityPhotopickerBinding binding;
    private PhotoPickerViewModel viewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_photopicker);
        init();
    }

    public void init() {

        viewModel = ViewModelProviders.of(this).get(PhotoPickerViewModel.class);
        viewModel.onCreate();

        binding.setViewmodel(viewModel);
        binding.setLifecycleOwner(this);
        pickRandomImage();
        setObserver();
    }

    @Override
    protected void onResume() {
        super.onResume();
        viewModel.onResume();
    }

    private Observer<String> lClickEntityOb;

    public void setObserver() {

        lClickEntityOb = new Observer<String>() {
            @Override
            public void onChanged(String path) {
                if (path != null && !path.isEmpty()) {
                    Intent intent = new Intent();
                    intent.putExtra(TAG_FILEPATH, path);
                    setResult(RESULT_SUCCESS, intent);
                    finish();
                }
            }
        };
        viewModel.lItemEntity.observe(this, lClickEntityOb);
    }

    private void pickRandomImage() {
        String[] mediaArray = new String[2];
        mediaArray[0] = MediaStore.Images.ImageColumns._ID;
        mediaArray[1] = MediaStore.Images.ImageColumns.DATA;
        String[] tempArray = new String[1];
        tempArray[0] = "0";

        Cursor c = getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, mediaArray,
                MediaStore.Images.ImageColumns.SIZE + ">?", tempArray, null);
        if (c != null) {
            int total = c.getCount();
            int count = 0;
            while (count < total) {
                PhotoListEntity photoListEntity = new PhotoListEntity();
                c.moveToNext();
                Uri insieUri = Uri.parse(c.getString(c.getColumnIndex(MediaStore.Images.ImageColumns.DATA)));
             /*   String[] splitStr = insieUri.toString().split("/");
                String folderName = splitStr[splitStr.length - 2];
*/
                viewModel.addImageFilePath(insieUri.toString());
                //  Log.e("경로", folderName + "/" + splitStr);

              /*  addPost.setAlbumName(folderName);
                addPost.setUri(insieUri);
                viewModel.allPhotoList.add(addPost);
                MakeLog.e("사진 전체 사이즈", "" + viewModel.allPhotoList.size());*/
                count++;
            }
            c.close();
        }
    }
}
