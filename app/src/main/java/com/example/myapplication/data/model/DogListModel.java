package com.example.myapplication.data.model;

import com.example.myapplication.data.entity.DogListEntity;

import java.util.ArrayList;
import java.util.List;

public class DogListModel {

    private List<DogListEntity> items;

    public DogListModel() {
        items = new ArrayList<>();
    }

    public DogListEntity getIndexDogEntity(int pos){
        return  items.get(pos);
    }

}
