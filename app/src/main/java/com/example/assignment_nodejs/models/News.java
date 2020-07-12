package com.example.assignment_nodejs.models;

import java.io.Serializable;

public class News implements Serializable {
    private String _id, time,title;
    private String type,desc,img;

    @Override
    public String toString() {
        return "News{" +
                "_id='" + _id + '\'' +
                ", time='" + time + '\'' +
                ", title='" + title + '\'' +
                ", type='" + type + '\'' +
                ", desc='" + desc + '\'' +
                ", img='" + img + '\'' +
                '}';
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }
}
