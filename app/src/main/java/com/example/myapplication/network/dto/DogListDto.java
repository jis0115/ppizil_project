package com.example.myapplication.network.dto;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class DogListDto {

    @SerializedName("dogs")
    private List<DetailInfo> dogs = new ArrayList<>();


    public List<DetailInfo> getDogs() {
        return dogs;
    }

    public void setDogs(List<DetailInfo> dogs) {
        this.dogs = dogs;
    }

    public static class DetailInfo implements Parcelable {
        @SerializedName("uid")
        private String userId;
        @SerializedName("isAdopted")
        private boolean isAdopted;
        @SerializedName("name")
        private String name;
        @SerializedName("type")
        private String type;
        @SerializedName("age")
        private int age;
        @SerializedName("gender")
        private String gender;
        @SerializedName("thumbnailImg")
        private String thumbnailImg;
        @SerializedName("imgs")
        private List<Img> imgs = new ArrayList<>();

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public boolean isAdopted() {
            return isAdopted;
        }

        public void setAdopted(boolean adopted) {
            isAdopted = adopted;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }

        public String getGender() {
            return gender;
        }

        public void setGender(String gender) {
            this.gender = gender;
        }

        public String getThumbnailImg() {
            return thumbnailImg;
        }

        public void setThumbnailImg(String thumbnailImg) {
            this.thumbnailImg = thumbnailImg;
        }

        public List<Img> getImgs() {
            return imgs;
        }

        public void setImgs(List<Img> imgs) {
            this.imgs = imgs;
        }


        public static class Img  implements Parcelable {
            @SerializedName("img")
            private String img;

            public String getImg() {
                return img;
            }

            public void setImg(String img) {
                this.img = img;
            }


            @Override
            public int describeContents() {
                return 0;
            }

            @Override
            public void writeToParcel(Parcel dest, int flags) {
                dest.writeString(this.img);
            }

            public Img() {
            }

            protected Img(Parcel in) {
                this.img = in.readString();
            }

            public static final Creator<Img> CREATOR = new Creator<Img>() {
                @Override
                public Img createFromParcel(Parcel source) {
                    return new Img(source);
                }

                @Override
                public Img[] newArray(int size) {
                    return new Img[size];
                }
            };
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.userId);
            dest.writeByte(this.isAdopted ? (byte) 1 : (byte) 0);
            dest.writeString(this.name);
            dest.writeString(this.type);
            dest.writeInt(this.age);
            dest.writeString(this.gender);
            dest.writeString(this.thumbnailImg);
            dest.writeList(this.imgs);
        }

        public DetailInfo() {
        }

        protected DetailInfo(Parcel in) {
            this.userId = in.readString();
            this.isAdopted = in.readByte() != 0;
            this.name = in.readString();
            this.type = in.readString();
            this.age = in.readInt();
            this.gender = in.readString();
            this.thumbnailImg = in.readString();
            this.imgs = new ArrayList<Img>();
            in.readList(this.imgs, Img.class.getClassLoader());
        }

        public static final Parcelable.Creator<DetailInfo> CREATOR = new Parcelable.Creator<DetailInfo>() {
            @Override
            public DetailInfo createFromParcel(Parcel source) {
                return new DetailInfo(source);
            }

            @Override
            public DetailInfo[] newArray(int size) {
                return new DetailInfo[size];
            }
        };
    }
}
