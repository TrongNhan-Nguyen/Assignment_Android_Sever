package com.example.assignment_nodejs.models;

import java.io.Serializable;

public class Inbox implements Serializable {
    private String  _id,from,title,content,pubDate;
    private boolean active;

    @Override
    public String toString() {
        return "Inbox{" +
                "_id='" + _id + '\'' +
                ", from='" + from + '\'' +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", pubDate='" + pubDate + '\'' +
                ", active=" + active +
                '}';
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getPubDate() {
        return pubDate;
    }

    public void setPubDate(String pubDate) {
        this.pubDate = pubDate;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
