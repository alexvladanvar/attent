package com.myspring.entities;

public class QRClass {
    private String lesson;
    private String date;
    private String hash;

    public QRClass() {
    }

    public QRClass(String lesson, String date) {
        this.lesson = lesson;
        this.date = date;
        this.hash = hash;
    }

    public String getLesson() {
        return lesson;
    }

    public void setLesson(String lesson) {
        this.lesson = lesson;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }
}
