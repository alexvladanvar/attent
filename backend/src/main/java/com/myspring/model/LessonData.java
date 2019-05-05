package com.myspring.model;


import java.util.Date;

public class LessonData {
    private int lessonId;
    private String subjectName;
    private String groupName;
    private Date date;


    public LessonData() {
    }

    public LessonData(int lessonId, String subjectName, String groupName, Date date) {
        this.lessonId = lessonId;
        this.subjectName = subjectName;
        this.groupName = groupName;
        this.date = date;
    }

    public int getLessonId() {
        return lessonId;
    }

    public void setLessonId(int lessonId) {
        this.lessonId = lessonId;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
