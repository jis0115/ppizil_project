package com.example.myapplication.data.model

import com.example.myapplication.data.entity.DogListEntity
import com.example.myapplication.network.dto.DogListDto

import java.util.ArrayList

class DogListModel {

    var doglistEntity: DogListDto? = null

    init {
        doglistEntity = DogListDto()
    }
}

