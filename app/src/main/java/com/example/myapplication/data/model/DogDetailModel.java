package com.example.myapplication.data.model;

import com.example.myapplication.network.dto.DogListDto;

public class DogDetailModel {

    private DogListDto.DetailInfo info;

    public DogDetailModel() {
        info = new DogListDto.DetailInfo();
    }

    public DogListDto.DetailInfo getInfo() {
        return info;
    }

    public void setInfo(DogListDto.DetailInfo info) {
        this.info = info;

    }
}
