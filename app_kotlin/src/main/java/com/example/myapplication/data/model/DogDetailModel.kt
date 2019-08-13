package com.example.myapplication.data.model

import com.example.myapplication.network.dto.DogListDto

class DogDetailModel {

    var info: DogListDto.DetailInfo? = null

    init {
        info = DogListDto.DetailInfo()
    }
}
