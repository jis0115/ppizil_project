package com.ppizil.photopicker.model;

import java.util.ArrayList;
import java.util.List;

public class PhotoListModel {

    private List<String> photoList;

    public PhotoListModel() {
        photoList = new ArrayList<>();
    }

    public List<String> getPhotoList() {
        return photoList;
    }

    public void addEntity(String path) {
        photoList.add(path);
    }
}
