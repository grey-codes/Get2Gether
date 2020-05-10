package com.get2gether;

import java.util.Date;

public class Meeting {
    private int id;
    private java.util.Date date;
    private String owner;
    private String title;
    private String timeString;

    public Meeting(int id, Date date, String owner, String title, String timeString) {
        this.id = id;
        this.date = date;
        this.owner = owner;
        this.title = title;
        this.timeString = timeString;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTimeString() {
        return timeString;
    }

    public void setTimeString(String timeString) {
        this.timeString = timeString;
    }
}
