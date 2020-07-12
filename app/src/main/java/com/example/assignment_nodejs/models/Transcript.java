package com.example.assignment_nodejs.models;

import java.util.List;

public class Transcript {
    private String status;
    private float scores;
    private Subject subject;

    public Transcript(String status, float scores, Subject subject) {
        this.status = status;
        this.scores = scores;
        this.subject = subject;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public float getScores() {
        return scores;
    }

    public void setScores(float scores) {
        this.scores = scores;
    }

    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }

    @Override
    public String toString() {
        return "Transcript{" +
                "status='" + status + '\'' +
                ", scores=" + scores +
                ", subject=" + subject +
                '}';
    }
}
