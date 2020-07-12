package com.example.assignment_nodejs.models;

public class Schedule {
    private String subjectID, subjectName, shift,date;

    public Schedule(String subjectID, String subjectName, String shift, String date) {
        this.subjectID = subjectID;
        this.subjectName = subjectName;
        this.shift = shift;
        this.date = date;
    }

    @Override
    public String toString() {
        return "Schedule{" +
                "subjectID='" + subjectID + '\'' +
                ", subjectName='" + subjectName + '\'' +
                ", shift='" + shift + '\'' +
                ", date='" + date + '\'' +
                '}';
    }

    public String getSubjectID() {
        return subjectID;
    }

    public void setSubjectID(String subjectID) {
        this.subjectID = subjectID;
    }


    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public String getShift() {
        return shift;
    }

    public void setShift(String shift) {
        this.shift = shift;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
