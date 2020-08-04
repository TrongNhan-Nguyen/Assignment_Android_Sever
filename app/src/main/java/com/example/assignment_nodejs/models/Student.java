package com.example.assignment_nodejs.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Student {
    private String _id, email, password, type;
    private String name,phoneNumber,sex, specialized;
    private String birthday,schedule,transcript,img;
    @SerializedName("class")
    private String studentClass;
    private List<Inbox> inbox;


    public List<Inbox> getInbox() {
        return inbox;
    }

    public void setInbox(List<Inbox> inbox) {
        this.inbox = inbox;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getSpecialized() {
        return specialized;
    }

    public void setSpecialized(String specialized) {
        this.specialized = specialized;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getSchedule() {
        return schedule;
    }

    public void setSchedule(String schedule) {
        this.schedule = schedule;
    }

    public String getTranscript() {
        return transcript;
    }

    public void setTranscript(String transcript) {
        this.transcript = transcript;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getStudentClass() {
        return studentClass;
    }

    public void setStudentClass(String studentClass) {
        this.studentClass = studentClass;
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
                ", img='" + img + '\'' +
                ", studentClass='" + studentClass + '\'' +
                ", inboxList=" + inbox +
                '}';
    }
}
