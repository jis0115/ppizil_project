package com.example.myapplication.data.entity;

public class DogListEntity {

    private int id;
    private String name;
    private String imageUrl;
    private String age;
    private String coments;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getComents() {
        return coments;
    }

    public void setComents(String coments) {
        this.coments = coments;
    }
}
