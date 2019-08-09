package com.ppizil.photopicker.viewmodel;

import com.ppizil.photopicker.adapter.PhotoPickerAdapter;
import com.ppizil.photopicker.model.PhotoListModel;
import com.ppizil.photopicker.utils.SingleLiveEvent;

public class PhotoPickerViewModel extends BaseViewModel {


    private PhotoListModel photoListModel;
    private PhotoPickerAdapter adapter;
    public SingleLiveEvent<String> lItemEntity = new SingleLiveEvent<>();

    @Override
    public void onCreate() {
        photoListModel = new PhotoListModel();
        adapter = new PhotoPickerAdapter(this);
    }

    @Override
    public void onStart() {

    }

    @Override
    public void onResume() {
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onStop() {

    }

    @Override
    public void onDestroy() {

    }

    public void addImageFilePath(String path) {
        photoListModel.addEntity(path);
    }

    public PhotoListModel getPhotoListModel() {
        return photoListModel;
    }

    public PhotoPickerAdapter getAdapter() {
        return adapter;
    }

    public String getImageRes(int pos) {
        return photoListModel.getPhotoList().get(pos);
    }

    public void onClickDelteBtn(int pos) {
        photoListModel.getPhotoList().remove(pos);
        adapter.notifyDataSetChanged();
    }

    public void onClickItemRow(int pos){
        lItemEntity.setValue(photoListModel.getPhotoList().get(pos));
    }
}
