package com.example.blogapp;

public class Model {

    private String title , desc, image;

    public Model(String title, String desc, String image) {
        this.title = title;
        this.desc = desc;
        this.image = image;
    }

    public Model() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
