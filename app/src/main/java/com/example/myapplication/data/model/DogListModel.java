package com.example.myapplication.data.model;

import com.example.myapplication.data.entity.DogListEntity;
import com.example.myapplication.network.dto.DogListDto;

import java.util.ArrayList;
import java.util.List;

public class DogListModel {

    private DogListDto doglistEntity;


    public DogListModel() {
        doglistEntity = new DogListDto();
    }

    public void setDoglistEntity(DogListDto doglistEntity) {
        this.doglistEntity = doglistEntity;
    }

    public DogListDto getDoglistEntity() {
        return doglistEntity;
    }
}

