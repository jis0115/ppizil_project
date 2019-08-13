package com.example.myapplication.network.dto

import android.annotation.SuppressLint
import android.os.Parcel
import android.os.Parcelable

import com.google.gson.annotations.SerializedName

import java.util.ArrayList

class DogListDto {

    @SerializedName("dogs")
    var dogs: List<DetailInfo> = ArrayList()

    class DetailInfo : Parcelable {
        @SerializedName("uid")
        var userId: String? = null
        @SerializedName("isAdopted")
        var isAdopted: Boolean = false
        @SerializedName("name")
        var name: String? = null
        @SerializedName("type")
        var type: String? = null
        @SerializedName("age")
        var age: Int = 0
        @SerializedName("gender")
        var gender: String? = null
        @SerializedName("thumbnailImg")
        var thumbnailImg: String? = null
        @SerializedName("imgs")
        var imgs: List<Img> = ArrayList()


        class Img : Parcelable {
            @SerializedName("img")
            var img: String? = null


            override fun describeContents(): Int {
                return 0
            }

            override fun writeToParcel(dest: Parcel, flags: Int) {
                dest.writeString(this.img)
            }

            constructor() {}

            protected constructor(`in`: Parcel) {
                this.img = `in`.readString()
            }

            companion object {

                @SuppressLint("ParcelCreator")
                val CREATOR: Parcelable.Creator<Img> = object : Parcelable.Creator<Img> {
                    override fun createFromParcel(source: Parcel): Img {
                        return Img(source)
                    }

                    override fun newArray(size: Int): Array<Img?> {
                        return arrayOfNulls(size)
                    }
                }
            }
        }

        override fun describeContents(): Int {
            return 0
        }

        override fun writeToParcel(dest: Parcel, flags: Int) {
            dest.writeString(this.userId)
            dest.writeByte(if (this.isAdopted) 1.toByte() else 0.toByte())
            dest.writeString(this.name)
            dest.writeString(this.type)
            dest.writeInt(this.age)
            dest.writeString(this.gender)
            dest.writeString(this.thumbnailImg)
            dest.writeList(this.imgs)
        }

        constructor() {}

        protected constructor(`in`: Parcel) {
            this.userId = `in`.readString()
            this.isAdopted = `in`.readByte().toInt() != 0
            this.name = `in`.readString()
            this.type = `in`.readString()
            this.age = `in`.readInt()
            this.gender = `in`.readString()
            this.thumbnailImg = `in`.readString()
            this.imgs = ArrayList()
            `in`.readList(this.imgs, Img::class.java.classLoader)
        }

        companion object {

            @SuppressLint("ParcelCreator")
            val CREATOR: Parcelable.Creator<DetailInfo> = object : Parcelable.Creator<DetailInfo> {
                override fun createFromParcel(source: Parcel): DetailInfo {
                    return DetailInfo(source)
                }

                override fun newArray(size: Int): Array<DetailInfo?> {
                    return arrayOfNulls(size)
                }
            }
        }
    }
}
