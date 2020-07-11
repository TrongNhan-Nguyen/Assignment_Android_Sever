package com.example.assignment_nodejs.models;

import com.google.gson.annotations.SerializedName;

public class Student {
    private String _id, email, password, type;
    private String name,phoneNumber,sex, specialized;
    private String birthday,schedule,transcript;
    @SerializedName("class")
    private String studentClass;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "Student{" +
                "_id='" + _id + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", type='" + type + '\'' +
                ", name='" + name + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", sex='" + sex + '\'' +
                ", specialized='" + specialized + '\'' +
                ", birthday='" + birthday + '\'' +
                ", schedule='" + schedule + '\'' +
                ", transcript='" + transcript + '\'' +
                ", studentClass='" + studentClass + '\'' +
                '}';
    }
}
