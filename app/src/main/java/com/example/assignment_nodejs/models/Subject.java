package com.example.assignment_nodejs.models;

public class Subject {
    private String _id, name, subjectID;

    public Subject(String name, String subjectID) {
        this.name = name;
        this.subjectID = subjectID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSubjectID() {
        return subjectID;
    }

    public void setSubjectID(String subjectID) {
        this.subjectID = subjectID;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }


    @Override
    public String toString() {
        return "Subject{" +
                "_id='" + _id + '\'' +
                ", name='" + name + '\'' +
                ", subjectID='" + subjectID + '\'' +
                '}';
    }
}
